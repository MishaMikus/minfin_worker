package ui_automation;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class ChromeHelper {
    private static final Logger LOGGER = Logger.getLogger(ChromeHelper.class);

    public static void main(String[] args) {
        System.out.println("ChromeVersion: " + getChromeVersion());
    }

    public static String getChromeVersion() {
        ProcessBuilder pb;
        String os = System.getProperty("os.name");
        LOGGER.info("OS: " + os);
        os = os.startsWith("Mac") ? "mac" : os;
        os = os.startsWith("Windows") ? "win" : os;
        os = os.contains("nux") ? "nux" : os;
        LOGGER.info("OS.type: " + os);
        String version = null;
        switch (os) {
            case "mac": {
                //Mac
                pb = new ProcessBuilder("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "--version");
                String output = null;
                try {
                    output = readOutput(pb);
                version = Objects.requireNonNull(output).split(" ")[2].split("\\.")[0];
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
                break;
            }

            case "win": {
                //Windows
                //wmic datafile where name="C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe" get Version /value
                pb = new ProcessBuilder("wmic", "datafile", "where",
                        "name=\"C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe\"",
                        "get", "Version", "/value");
                String output = null;
                try {
                    output = readOutput(pb);
                    version = Objects.requireNonNull(output).split("Version=")[1].split("\\.")[0];
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
                break;
            }

            case "nux": {
                //Linux
                //google-chrome --version
                pb = new ProcessBuilder("google-chrome", "--version");
                String output = null;
                try {
                    output = readOutput(pb);
                    version = Objects.requireNonNull(output).split(" ")[2].split("\\.")[0];
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.warn("can't parse Chrome version from system output: " + output);
                }
                break;
            }
        }
        LOGGER.info("current chrome version: " + version);
        return version;
    }

    private static String readOutput(ProcessBuilder pb) {
        try {
            LOGGER.info(">>> " + String.join(" ", pb.command()));
            Process p = pb.start();
            byte[] output = new byte[1024];
            p.getInputStream().read(output);
            String outputString = new String(output);
            LOGGER.info("<<< " + outputString.split("\n")[0]);
            return outputString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
