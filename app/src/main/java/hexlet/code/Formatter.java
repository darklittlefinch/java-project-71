package hexlet.code;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Json;

public class Formatter {

    public static final String FORMAT_STYLISH = "stylish";
    public static final String FORMAT_PLAIN = "plain";
    public static final String FORMAT_JSON = "json";

    // Returns String of differences
    public static String format(List<Map<String, Object>> diff, String format) throws IOException {

        if (format.equals(FORMAT_STYLISH)) {
            return Stylish.stylish(diff);
        } else if (format.equals(FORMAT_PLAIN)) {
            return Plain.plain(diff);
        } else if (format.equals(FORMAT_JSON)) {
            return Json.json(diff);
        } else {
            throw new IOException("Unsupported format");
        }
    }
}
