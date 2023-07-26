package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.DiffTree;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Json {

    private static final String SYMBOL_ADDED = "+";
    private static final String SYMBOL_DELETED = "-";

    // Returns differences in JSON format
    public static String json(List<Map<String, Object>> diff) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(diff);
    }
}
