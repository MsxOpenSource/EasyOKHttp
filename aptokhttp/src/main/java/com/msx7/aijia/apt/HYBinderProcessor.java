package com.msx7.aijia.apt;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.googlejavaformat.java.filer.FormattingFiler;
import com.msx7.android.annotions.Convert;
import com.msx7.android.annotions.HActionSubscriber;
import com.msx7.android.annotions.HRetrofit;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

/**
 * 所 属 包：com.msx7.aijia.apt
 * 文 件 名：HYBinderProcessor
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2017/6/27
 */
@AutoService(Processor.class)
public class HYBinderProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(HTTP.class.getCanonicalName());
        annotations.add(GET.class.getCanonicalName());
        annotations.add(POST.class.getCanonicalName());
        annotations.add(HActionSubscriber.class.getCanonicalName());
        annotations.add(Convert.class.getCanonicalName());
        annotations.add(HRetrofit.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        Filer filer = new FormattingFiler(processingEnv.getFiler());
        Messager messager = processingEnv.getMessager();
        Elements elements = processingEnv.getElementUtils();
        Types types = processingEnv.getTypeUtils();

        HttpGenerator generator = new HttpGenerator(filer, messager, elements, types);



        buildProcessingStep(generator, roundEnvironment);
        return true;
    }


    void buildProcessingStep(
            BasicAnnotationProcessor.ProcessingStep
                    step, RoundEnvironment roundEnvironment) {
        ImmutableSetMultimap.Builder<Class<? extends Annotation>, Element>
                elementsByAnnotation = ImmutableSetMultimap.builder();

        Set<? extends Class> cls = step.annotations();
        Iterator<? extends Class> clsIt = cls.iterator();
        while (clsIt.hasNext()) {
            Class _class = clsIt.next();

            Iterator<? extends Element> it1 = roundEnvironment.getElementsAnnotatedWith(_class).iterator();
            while (it1.hasNext()) {
                elementsByAnnotation.put(_class, it1.next());
            }

        }
        SetMultimap<Class<? extends Annotation>, Element> build = elementsByAnnotation.build();
        if (build != null && !build.isEmpty()) {
            step.process(build);
        }
    }
}
