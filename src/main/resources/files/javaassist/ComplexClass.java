public class ComplexClass {

    static {
        System.out.println(ComplexClass.class.getSimpleName() + " is loaded...");
    }

    public ComplexClass() {
        System.out.println(ComplexClass.class.getSimpleName() + " is instantiated...");
    }

    interface Inner0 {

        interface Inner1_1 {
            String CONST_1 = "Constant 1";
            String CONST_2 = "Constant 2";
        }

        interface Inner1_2 {
            String CONST_3 = "Constant 3";
            String CONST_4 = "Constant 4";
        }

        interface Inner1_3 {
            String CONST_5 = "Constant 5";
            String CONST_6 = "Constant 6";
        }
    }
}
