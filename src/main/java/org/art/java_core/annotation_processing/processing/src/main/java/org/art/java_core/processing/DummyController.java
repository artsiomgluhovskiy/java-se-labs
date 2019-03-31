package org.art.java_core.processing;

/**
 * A simple example of a controller implementation.
 * There is a requirement, that every public method of the controller
 * must be invoked with some preliminary validation (secured). So, every
 * public method must be annotated with {@link SecuredInvocation}. In case
 * of missing this annotation for some reasons, the error during COMPILE
 * time should be thrown.
 */
@Controller
public class DummyController {

    /* Public annotated method - OK! */
    @SecuredInvocation
    public String invokeSecured() {
        return "Secured method invocation!";
    }

    /* Public method without annotation - Compile time error!!! */
    public String invokeUnsecured() {
        return "Unsecured method invocation!";
    }

    /* Private method without annotation - OK! */
    private String invokePrivate() {
        return "Private method invocation!";
    }
}
