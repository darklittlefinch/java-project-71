package hexlet.code;

import java.io.IOException;
import java.util.Map;

import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;

public class Formatter {

    private static final String FORMAT_STYLISH = "stylish";
    private static final String FORMAT_PLAIN = "plain";

    // Returns String of differences
    public static String format(Map<String, Object> map1, Map<String, Object> map2,
                                Map<String, String> diffMap, String format) throws IOException {

        if (format.equals(FORMAT_STYLISH)) {
            return Stylish.stylish(map1, map2, diffMap);
        } else if (format.equals(FORMAT_PLAIN)) {
            return Plain.plain(map1, map2, diffMap);
        } else {
            throw new IOException("Unsupported format");
        }
    }
}
