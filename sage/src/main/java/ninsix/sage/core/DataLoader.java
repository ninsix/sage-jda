package ninsix.sage.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {

    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_ARCH = System.getProperty("os.arch");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String JAVA_VENDOR = System.getProperty("java.vendor");
    public static final String SAVEDIR = new File(System.getProperty("user.dir")).getParentFile().toString() + File.separator + "etc" + File.separator;
    public static final String MANUAL = SAVEDIR + "manual.md";

    public static HashMap<String, String> pages = new HashMap<>();
    public static String[] page_key;

    public static void load_pages(String pagefile) {
        String pages_str = read_file(pagefile);
        if (pages_str == null) {
            return;
        }
        pages.clear();
        hmps_from_str(pages_str);
        page_key = pages.keySet().toArray(new String[pages.size()]);
    }

    private static void hmps_from_str(String input) {
        Pattern pattern = Pattern.compile("\\[:(.*?):\\] \\{:(.*?):\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1).strip();
            String page = matcher.group(2).replaceAll("\t", "");
            pages.put(key, page);
        }
    }

    public static String read_file(String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        System.out.println("[  loader  ] Loading " + file.getAbsolutePath());
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                System.out.println("[  loader  ] Loaded " + file.getAbsolutePath());
                return content.toString();
            } catch (IOException ex) {
                System.err.println("[  error  ] File " + file.getAbsolutePath() + " is corrupted");
            }
        } else {
            System.err.println("[  error  ] File " + file.getAbsolutePath() + " doesn't exist");
        }
        System.err.println("[  error  ] Could'nt load file " + file.getAbsolutePath());
        return null;
    }

    /*
    public static void loadKeysAndValuesFromFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    String key = getKeyFromFile(file);
                    String value = getValueFromFile(file);

                    if (key != null && value != null) {
                        keyValueMap.put(key, value);
                    }
                }
            }
        }
    }

    public static String getKeyFromFile(File file) {
        String fileName = file.getName();
        // Assuming that the file name itself is the key
        return fileName;
    }

    public static String getValueFromFile(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return content.toString().trim();
    }*/
}
