package util;

import java.util.HashMap;

public class StringUtil {
    private static final HashMap<String, String> CYRILLIC_ENGLISH_CHAR_MAPPING = new HashMap<>();

    static {
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0410", "A");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0412", "B");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0421", "C");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0415", "E");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u041D", "H");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u041A", "K");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u041C", "M");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u041E", "O");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0420", "P");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0422", "T");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0423", "Y");
        CYRILLIC_ENGLISH_CHAR_MAPPING.put("\u0425", "X");
    }

    private static final HashMap<String, String> URL_MAPPING = new HashMap<>();

    static {
        URL_MAPPING.put("=", "%3D");
        URL_MAPPING.put(":", "%3A");
        URL_MAPPING.put("\\[", "%5B");
        URL_MAPPING.put("\\]", "%5D");
    }

    public static void main(String[] args) {
        CYRILLIC_ENGLISH_CHAR_MAPPING.entrySet().forEach(System.out::println);
        //"А=A,Р=P,С=C,В=B,Т=T,У=Y,Е=E,Х=X,К=K,М=M,Н=H,О=O";
        System.out.println(turnCyrillicLettersToEnglish("А=A,Р=P,С=C,В=B,Т=T,У=Y,Е=E,Х=X,К=K,М=M,Н=H,О=O"));
    }

    public static String turnCyrillicLettersToEnglish(String target) {
        return encodeUsingMapping(CYRILLIC_ENGLISH_CHAR_MAPPING, target);
    }

    private static String encodeUsingMapping(HashMap<String, String> encodeMap, String target) {
        final String[] result = {target};
        encodeMap.forEach((key, value) -> result[0] = result[0].replaceAll(key, value));
        return result[0];
    }


    public static String urlEncode(String input) {
        return encodeUsingMapping(URL_MAPPING, input);
    }
}
