package ninsix.sage.core;

import java.util.Scanner;

public class IO {

    public class output {

        public static void success(String message) {
            System.out.println("[ \u001b[32mSUCCESS\u001b[0m ] " + message);
        }

        public static void error(String message) {
            System.err.println("[  \u001b[31mERROR\u001b[0m  ] " + message);
        }

        public static void log(String message) {
            System.out.println("[   \u001b[36mLOG\u001b[0m   ] " + message);
        }
    }

    public class input {

        public static String str(String message) {
            Scanner input = new Scanner(System.in);
            System.err.println("[  \u001b[33mINPUT\u001b[0m  ] " + message);
            if (input.hasNext()) {
                System.out.print("> ");
                return input.nextLine().strip();
            }
            output.error("Couldn't get console input");
            return "null";
        }

    }

}
