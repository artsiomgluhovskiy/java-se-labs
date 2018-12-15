public class SimpleClass {

    static {
        System.out.println(SimpleClass.class.getSimpleName() + " is loaded...");
        System.out.println("Class loader: " + SimpleClass.class.getClassLoader());
    }

    public SimpleClass() {
        System.out.println(SimpleClass.class.getSimpleName() + " is instantiated...");
    }

    private static final String CONST_1 = "Constant 1";
    private static final String CONST_2 = "Constant 2";
    private static final String CONST_3 = "Constant 3";
    private static final String CONST_4 = "Constant 4";
    private static final String CONST_5 = "Constant 5";
    private static final String CONST_6 = "Constant 6";
}