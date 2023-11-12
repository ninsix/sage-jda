package ninsix.sage.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ninsix.sage.core.IO.output;

public class DataLoader {

    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_ARCH = System.getProperty("os.arch");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String JAVA_VENDOR = System.getProperty("java.vendor");
    public static final String SAVEDIR = new File(System.getProperty("user.dir")).getParentFile().toString() + File.separator + "etc" + File.separator;
    public static final String MANUAL = SAVEDIR + "manual.md";

    public static HashMap<String, String> manualPages = new HashMap<>();

    public static void loadPages(String pagefile) {
        String fileContent = getFileContent(pagefile);
        if (fileContent == null) {
            return;
        }
        manualPages.clear();
        manualPages = getMapFromString(fileContent);
    }

    private static HashMap<String, String> getMapFromString(String input) {
        Pattern pattern = Pattern.compile("\\[:(.*?):\\] \\{:(.*?):\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);
        HashMap<String, String> hashmap = new HashMap<>();
        while (matcher.find()) {
            String key = matcher.group(1).strip();
            String page = matcher.group(2).replaceAll("\t", "");
            hashmap.put(key, page);
        }
        return hashmap;
    }

    public static String getFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        output.log("Loading " + file.getAbsolutePath());
        
        if (!file.exists()) {
            output.error("Couldn't load, file " + file.getAbsolutePath() + " doesn't exist");
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            output.success("Loaded " + file.getAbsolutePath());
            return content.toString();
            
        } catch (IOException ex) {
            output.error("Couldn't load, file " + file.getAbsolutePath() + " is corrupted");
            return null;
        }
    }

}
