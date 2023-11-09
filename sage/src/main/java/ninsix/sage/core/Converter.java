package ninsix.sage.core;

public class Converter {
    
    public static String format(String message) {
        return "```" + message + "```";
    }
    
    public static String Text2bB(String text) {
        return binary2bB(Text2Binary(ensecret(text)));
    }
    
    public static String bB2Text(String text) {
        try {
            return unsecret(Binary2Text((bB2binary(text))));
        } catch (NumberFormatException nfe) {
            return "Not a valid bB string!";
        }
    }
    
    public static String Binary2Text_valid(String text) {
        try {
            return Binary2Text(text);
        } catch (NumberFormatException nfe) {
            return "Not a valid binary string!";
        }
    }
    
    public static String Text2Binary(String text) {
        int[] intchars = text.chars().toArray();
        String binary = "";
        for (int chr : intchars) {
            binary = binary.concat(Integer.toBinaryString(chr) + ' ');
        }
        return binary.trim();
    }

    private static String Binary2Text(String binary) {
        String[] split = binary.split(" ");
        int[] intchars = new int[split.length];
        String text = "";
        for (int i = 0; i < split.length; ++i) {
            intchars[i] = Integer.parseInt(split[i], 2);
            text = text.concat(Character.toString(intchars[i]));
        }
        return text;
    }
    
    private static String binary2bB(String binary) {
        return binary.replace('0', 'b').replace('1', 'B');
    }

    private static String bB2binary(String bBstr) {
        return bBstr.replace('b', '0').replace('B', '1');
    }

    private static String ensecret(String text) {
        int[] intchars = text.chars().toArray();
        int length = text.strip().length();
        String secret = "";
        for (int chr : intchars) {
            secret = secret.concat(Character.toString(chr + length / 2));
        }
        return secret.trim();
    }

    private static String unsecret(String text) {
        int[] intchars = text.chars().toArray();
        int length = text.strip().length();
        String secret = "";
        for (int chr : intchars) {
            secret = secret.concat(Character.toString(chr - length / 2));
        }
        return secret.trim();
    }
    
}
