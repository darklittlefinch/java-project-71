package hexlet.code;

import java.io.IOException;
import java.util.Map;

import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Json;

public class Formatter {

    private static final String FORMAT_STYLISH = "stylish";
    private static final String FORMAT_PLAIN = "plain";
    private static final String FORMAT_JSON = "json";

    // Returns String of differences
    public static String format(Map<String, Object> map1, Map<String, Object> map2,
                                Map<String, String> diffMap, String format) throws IOException {

        if (format.equals(FORMAT_STYLISH)) {
            return Stylish.stylish(map1, map2, diffMap);
        } else if (format.equals(FORMAT_PLAIN)) {
            return Plain.plain(map1, map2, diffMap);
        } else if (format.equals(FORMAT_JSON)) {
            return Json.json(map1, map2, diffMap);
        } else {
            throw new IOException("Unsupported format");
        }
    }
}
