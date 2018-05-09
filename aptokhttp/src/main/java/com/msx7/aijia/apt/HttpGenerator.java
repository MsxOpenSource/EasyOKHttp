package com.msx7.aijia.apt;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.msx7.android.annotions.ActionData;
import com.msx7.android.annotions.Convert;
import com.msx7.android.annotions.HActionSubscriber;
import com.msx7.android.annotions.HRetrofit;
import com.msx7.android.annotions.IActionDataParse;
import com.msx7.android.commons.HYSubscriberImpl;
import com.msx7.android.commons.MRetorfit;
import com.msx7.android.commons.OkHttpSubcribe;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.util.Pair;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import rx.Subscription;

/**
 * 所 属 包：com.msx7.aijia.apt
 * 文 件 名：HttpGenerator
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2017/6/27
 */
public class HttpGenerator implements BasicAnnotationProcessor.ProcessingStep {


    Filer    filer;
    Messager messager;
    Elements elements;
    Types    types;

    public HttpGenerator(Filer filer, Messager messager, Elements elements, Types types) {
        this.filer = filer;
        this.messager = messager;
        this.elements = elements;
        this.types = types;
    }

    private Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

    {
        annotations.add(HTTP.class);
        annotations.add(GET.class);
        annotations.add(POST.class);
        annotations.add(HActionSubscriber.class);
        annotations.add(Convert.class);
        annotations.add(HRetrofit.class);
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return annotations;
    }

    String subscribleType;
    String convertClass;
    String retrofitClass;

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        if (elementsByAnnotation.get(HActionSubscriber.class).isEmpty()) {
            subscribleType = HYSubscriberImpl.class.getName();
        } else
            subscribleType = elementsByAnnotation.get(HActionSubscriber.class).iterator().next().toString();

        if (elementsByAnnotation.get(Convert.class).isEmpty()) {
            convertClass = OkHttpSubcribe.class.getName();
        } else
            convertClass = elementsByAnnotation.get(Convert.class).iterator().next().asType().toString();

        if (elementsByAnnotation.get(HRetrofit.class).isEmpty()) {
            retrofitClass = MRetorfit.class.getName();
        } else
            retrofitClass = elementsByAnnotation.get(HRetrofit.class).iterator().next().asType().toString();

        ImmutableMap.Builder<String, Pair<TypeSpec.Builder, ImmutableList.Builder<MethodSpec>>> map =
                ImmutableMap.builder();

        for (ExecutableElement element : ElementFilter.methodsIn(elementsByAnnotation.values())) {
            String              packageName = elements.getPackageOf(element).getQualifiedName().toString();
            String              methodName  = element.getSimpleName().toString();
            Symbol.MethodSymbol methodElemt = (Symbol.MethodSymbol) element;

            String className = methodElemt.owner.asType().toString().substring(packageName.length() + 1).replace(".", "$");

            String key = packageName + "|" + className;
            ImmutableList.builder();
            Pair<TypeSpec.Builder, ImmutableList.Builder<MethodSpec>> pair = map.build().get(key);

            if (pair == null) {

                pair = new Pair<>(
                        generateAndroidCreatorClass(methodElemt, packageName, className),
                        ImmutableList.<MethodSpec>builder().add(
                                generateAndroidHttpMethod(methodElemt, packageName, className, methodName)
                        )
                );
                map.put(key, pair);
            } else {
                pair.snd.add(generateAndroidHttpMethod(methodElemt, packageName, className, methodName));
            }
        }
        map.build().forEach((s, v) -> {
            TypeSpec.Builder builder = v.fst;
            v.snd.build().forEach(s1 -> {
                builder.addMethod(s1);
            });

            String packageName = s.substring(0, s.lastIndexOf("|"));
            try {
                JavaFile.builder(packageName,
                        builder
                                .build()
                )
                        .build().writeTo(filer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return ImmutableSet.of();
    }


    TypeSpec.Builder generateAndroidCreatorClass(Symbol.MethodSymbol method, String packageName, String className) {
        TypeSpec.Builder tbuilder = TypeSpec.classBuilder(className + "_Creator");
        tbuilder.addModifiers(Modifier.PUBLIC);
        StringBuffer initBlock = new StringBuffer();
        initBlock.append(" api = " + retrofitClass + ".retrofit().create(")
                .append(packageName + "." + className + ".class);");
        tbuilder.addField(ClassName.get(method.owner.asType()), "api", Modifier.STATIC);
        tbuilder.addStaticBlock(CodeBlock.of(initBlock.toString()));
        return tbuilder;
    }

    /**
     * 根据对应的注解，生成API中的方法
     *
     * @param method
     * @return
     */
    MethodSpec generateAndroidHttpMethod(Symbol.MethodSymbol method
            , String packageName, String className, String methodName) {


        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        //构造method所需参数
        Collection<ParameterSpec> params = Collections2.transform(method.getParameters(),

                s -> ParameterSpec.builder(TypeName.get(s.asType()), s.getQualifiedName().toString()).build()
        );


        /**
         * 获取返回类型的泛型
         */
        String renturnGenericType = method.getReturnType().getTypeArguments().get(0).toString();

        /**
         * 增加 参数 IActionDataParse
         */
        TypeName parse = getIActionDataParseTypeName(renturnGenericType);

        params = ImmutableList
                .<ParameterSpec>builder()
                .addAll(params)
                .add(ParameterSpec.builder(parse, "parse")
                        .build())
                .build();


        builder.addParameters(params);
        /**
         * 实际接收response的类型
         */
        String dataClassname = String.format("%s<%s>", ActionData.class.getName(), renturnGenericType);

        builder.addCode(String.format(" %s data = new %s();\n", dataClassname, dataClassname));

        builder.addCode(String.format(" data.postData =new Object[]{ %s};\n",
                Arrays.toString(
                        Collections2.transform(method.getParameters(), s -> s.name.toString())
                                .toArray(new String[0])
                )
                        .replace("[", "")
                        .replace("]", ""))
        );
        builder.addCode(String.format(" return %s.subscribeOn(api.%s(%s)).subscribe(\nnew %s <%s>(data,parse));\n",
                convertClass, method.getSimpleName().toString(),
                Arrays.toString(
                        Collections2.transform(method.getParameters(), s -> s.name.toString())
                                .toArray(new String[0])
                )
                        .replace("[", "")
                        .replace("]", ""), subscribleType, renturnGenericType
        ));
        builder.returns(ClassName.bestGuess(Subscription.class.getName()));


        return builder.build();
    }


    TypeName getIActionDataParseTypeName(String type) {
        return getTypeName((IActionDataParse.class.getName() + "<" +
                type).replace(">", "").split("<"));
    }

    TypeName getActionDataTypeName(String type) {
        return getTypeName((ActionData.class.getName() + "<" +
                type).replace(">", "").split("<"));
    }

    /**
     * 生成Data类，通过eventBus接收的response类型
     *
     * @param packageName 包名
     * @param className   类名
     * @param superClass  父类
     */
    void generateAndroidDataClass(String packageName, String className, TypeName superClass) {
        try {
            JavaFile.builder(packageName,
                    TypeSpec.classBuilder(className)
                            .addModifiers(Modifier.PUBLIC)
                            .addField(ClassName.bestGuess("rx.Subscription"), "subscription", Modifier.PUBLIC)
                            .superclass(superClass).build())
                    .build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 将多级泛型  拆分成arr后，构建实际所需typename
     *
     * @param names
     * @return
     */
    TypeName className = null;

    private TypeName getTypeName(String... names) {
        className = null;

        ImmutableList.<String>builder().addAll(Arrays.asList(names))
                .build().reverse().forEach(s -> {

            if (s != null && s.length() > 0) {
                if (className == null) {
                    if (s.contains("[]"))
                        className = ArrayTypeName.of(ClassName.bestGuess(s.replace("[]", "")));
                    else
                        className = ClassName.bestGuess(s.toString());
                } else
                    className = ParameterizedTypeName.get(ClassName.bestGuess(s), className);

            }

        });
        return className;
    }

}
