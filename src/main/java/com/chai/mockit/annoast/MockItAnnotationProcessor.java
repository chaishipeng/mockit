package com.chai.mockit.annoast;

import com.chai.mockit.client.annotation.MockIt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by chaishipeng on 2017/4/20.
 */
@SupportedAnnotationTypes({"*"})
public class MockItAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator it = roundEnv.getElementsAnnotatedWith(MockIt.class).iterator();
        while(it.hasNext()){
            Element element = (Element) it.next();
        }
        return false;
    }
}
