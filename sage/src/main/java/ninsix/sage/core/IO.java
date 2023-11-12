package ninsix.sage.core;

import java.util.Scanner;

public class IO {

    public class output {

        public static void success(String message) {
            System.out.printf("[ success ] %s%n", message);
        }

        public static void error(String message) {
            System.err.printf("[  error  ] %s%n", message);
        }
        
        public static void log(String message) {
            System.out.printf("[   log   ] %s%n", message);
        }
    }

    public class input {

        public static String str(String message) {
            Scanner input = new Scanner(System.in);
            System.out.printf("[  input  ] %s%n", message);
            if (input.hasNext()) {
                System.out.print("> ");
                return input.nextLine().strip();
            }
            output.error("Couldn't get console input");
            return "null";
        }

    }
    
}
