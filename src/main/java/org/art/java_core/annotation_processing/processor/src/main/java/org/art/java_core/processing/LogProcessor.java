package org.art.java_core.processing;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

import static org.art.java_core.processing.ProcessorConstants.CONTROLLER_ANNOTATION_NAME;
import static org.art.java_core.processing.ProcessorConstants.SECURED_ANNOTATION_NAME;

/**
 * Simple annotation processor implementation.
 * Finds controller definitions (classes, annotated with {@link Controller})
 * and check if every public method is annotated with {@link SecuredInvocation}.
 * In case of missing {@link SecuredInvocation}, generates compile time error
 * with the location of the target public method.
 */
@SupportedAnnotationTypes({CONTROLLER_ANNOTATION_NAME, SECURED_ANNOTATION_NAME})
public class LogProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            if ((CONTROLLER_ANNOTATION_NAME.equals(annotation.getQualifiedName().toString()))) {
                for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    List<? extends Element> classElements = element.getEnclosedElements();
                    for (Element el : classElements) {
                        if (isPublicMethod(el)) {
                            if (el.getAnnotation(SecuredInvocation.class) == null) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Public controller method invocation should be secured!", el);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isPublicMethod(Element element) {
        return ElementKind.METHOD.equals(element.getKind())
                && element.getModifiers().contains(Modifier.PUBLIC);
    }
}
