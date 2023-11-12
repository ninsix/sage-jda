package ninsix.sage.core;

public class Converter {

    public class binary {

        public static String toText(String text) {
            try {
                return toText_unsafe(text);
            } catch (NumberFormatException nfe) {
                return "Not a valid binary string!";
            }
        }

        public static String from(String text) {
            int[] intchars = text.chars().toArray();
            String binary = "";
            for (int chr : intchars) {
                binary = binary.concat(Integer.toBinaryString(chr) + ' ');
            }
            return binary.trim();
        }

        private static String toBb(String binary) {
            return binary.replace('0', 'b').replace('1', 'B');
        }

        private static String toText_unsafe(String binary) {
            String[] split = binary.split(" ");
            int[] intchars = new int[split.length];
            String text = "";
            for (int i = 0; i < split.length; ++i) {
                intchars[i] = Integer.parseInt(split[i], 2);
                text = text.concat(Character.toString(intchars[i]));
            }
            return text;
        }
    }

    public class bBsecret {

        public static String from(String text) {
            return binary.toBb(binary.from(secret.from(text)));
        }

        public static String toText(String text) {
            try {
                return secret.undo(binary.toText((bB.toBinary(text))));
            } catch (NumberFormatException nfe) {
                return "Not a valid bB string!";
            }
        }

    }

    public class bB {
        private static String toBinary(String bBstr) {
            return bBstr.replace('b', '0').replace('B', '1');
        }
    }

    public class secret {

        private static String from(String text) {
            int[] intchars = text.chars().toArray();
            int length = text.strip().length();
            String secret = "";
            for (int chr : intchars) {
                secret = secret.concat(Character.toString(chr + length / 2));
            }
            return secret.trim();
        }

        private static String undo(String text) {
            int[] intchars = text.chars().toArray();
            int length = text.strip().length();
            String secret = "";
            for (int chr : intchars) {
                secret = secret.concat(Character.toString(chr - length / 2));
            }
            return secret.trim();
        }
    }

}
