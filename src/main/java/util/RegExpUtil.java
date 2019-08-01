package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {

    public static String findByRegex(String content, String regexp,int group) {
        return findsByRegex(content, regexp,group).get(0);
    }

    public static List<String> findsByRegex(String content, String regexp,int group) {
        List<String> res = new ArrayList<>();
        Matcher m = Pattern.compile(regexp).matcher(content);
        while (m.find()) {
            res.add(m.group(group));
        }
        return res;
    }
}
