package server;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IOUtils {
    public static final String FS = File.separator;
    private final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(IOUtils.class);

    public static String readTextFromResources(String fileName) {
        ClassLoader classLoader = IOUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return readTextFromFile(file);
    }

    public static String readTextFromFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.warn("can't read file '" + file.getAbsolutePath() + "'");
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readBytesFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            LOGGER.warn("can't read file '" + file.getAbsolutePath() + "'");
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> readPropertiesToMap(File file) throws IOException {
        Map<String, String> propertiesMap = new HashMap<>();
        if (file.exists()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            for (String propertyName : properties.stringPropertyNames()) {
                propertiesMap.put(propertyName, properties.get(propertyName).toString());
            }
        } else {
            LOGGER.warn("property file '" + file.getAbsolutePath() + "' not found");
        }
        return propertiesMap;
    }

    public static void copyDir(File srcFolder, File destFolder) throws IOException {
        if (!srcFolder.exists()) {
            LOGGER.warn("Directory '" + srcFolder.getAbsolutePath() + "' does not exist.");
            return;
        } else {
            copyFolder(srcFolder, destFolder);
        }
        LOGGER.info("COPY '" + srcFolder.getAbsolutePath() + "' to '" + destFolder.getAbsolutePath() + "' DONE");
    }

    private static void copyFolder(File src, File dest) throws IOException {

        if (src.getName().startsWith("~$")) {
            LOGGER.warn("SKIP COPY for '" + src.getAbsolutePath() + "'");
            return;
        }

        if (src.isDirectory()) {
            if (!dest.exists()) {
                mkDir(dest);
            }
            String files[] = src.list();
            if (files != null) {
                for (String file : files) {
                    copyFolder(new File(src, file), new File(dest, file));
                }
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            LOGGER.info("File copied from '" + src.getAbsolutePath() + "' to '" + dest.getAbsolutePath());
        }
    }

    public static void mkDir(File dir) {
        LOGGER.info("try to create " + dir.getAbsoluteFile().getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) {
            LOGGER.info("dir '" + dir.getAbsolutePath() + "' already exists");
        } else {
            Long start = new Date().getTime();
            while (!dir.getAbsoluteFile().mkdir() && new Date().getTime() < (start + 5 * 1000)) {
                if (dir.exists()) {
                    LOGGER.info("dir '" + dir.getAbsolutePath() + "' created");
                } else {
                    if (new Date().getTime() >= (start + 5 * 1000))
                        LOGGER.warn("can't create '" + dir.getAbsolutePath() + "' dir");
                }
            }
        }
        LOGGER.info("dir.exists() '" + dir.getAbsolutePath() + "': " + dir.exists());
    }

    public static void mkDir(String relativePath) {
        File dir = new File(System.getProperty("user.dir"), relativePath);
        mkDir(dir);
    }

    public static void saveTextToFile(File file, String content) {
        mkDir(file.getAbsoluteFile().getParentFile().getAbsoluteFile());
        mkFile(file);
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            LOGGER.warn("can't write to file '" + file.getAbsolutePath() + "'");
        }
        if (file.exists()) {
            LOGGER.info("saveTextToFile('" + file.getAbsolutePath() + "')[length:" + content.length() + "] DONE ");
        }
    }

    private static void mkFile(File file) {
        try {
            if (file.getAbsoluteFile().createNewFile()) {
                LOGGER.info("create '" + file.getAbsoluteFile().getAbsolutePath() + "' SUCCESS");
            }
        } catch (IOException e) {
            LOGGER.warn("create '" + file.getAbsolutePath() + "' FAIL");
        }
    }

    public static File zipFile(File fileNeedToBeZipped) {
        if (fileNeedToBeZipped.exists()) {
            byte[] buffer = new byte[1024];
            File zipFile = new File(FilenameUtils.getBaseName(fileNeedToBeZipped.getAbsolutePath()) + ".zip");
            try {
                FileOutputStream fos = new FileOutputStream(new File(FilenameUtils.getBaseName(fileNeedToBeZipped.getAbsolutePath()) + ".zip"));
                ZipOutputStream zos = new ZipOutputStream(fos);
                ZipEntry ze = new ZipEntry(fileNeedToBeZipped.getName());
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(fileNeedToBeZipped);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
                zos.closeEntry();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Long size = fileNeedToBeZipped.length();
            Long zipSize = zipFile.length();
            LOGGER.info("zippedFile has " + ((100 * zipSize) / size) + "% of input file");
            return zipFile;
        } else {
            LOGGER.warn("file '" + fileNeedToBeZipped.getAbsolutePath() + "' does't exist");
            return fileNeedToBeZipped;
        }
    }

    public static String projectRelativePath(String relativePath) {
        File appDir = new File(System.getProperty("user.dir"), relativePath);
        return appDir.getAbsolutePath();
    }

    public static String projectRelativePath(String... path) {
        String relativePath = String.join(FS, path);
        return projectRelativePath(relativePath);
    }

    public static File createFolder(String relativePath) throws IOException {
        File appDir = new File(System.getProperty("user.dir"), relativePath);
        if (!appDir.isDirectory()) {
            if (!appDir.mkdir()) {
                throw new IOException("create folder");
            }
        }
        return appDir;
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        file.deleteOnExit();
    }

    public static boolean fileExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        // verify on readable
        try {
            file.createNewFile();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
