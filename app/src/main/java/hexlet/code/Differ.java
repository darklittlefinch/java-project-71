package hexlet.code;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;

public class Differ {

    // Returns differences of two JSON files
    public static String generate(String fileName1, String fileName2, String format) throws IOException {

        String content1 = readFile(fileName1);
        String content2 = readFile(fileName2);

        Map<String, Object> mapContent1 = Parser.parse(content1, getFileExtension(fileName1));
        Map<String, Object> mapContent2 = Parser.parse(content2, getFileExtension(fileName2));

        List<Map<String, Object>> diff = DiffTree.genDiffTree(mapContent1, mapContent2);
        return Formatter.format(diff, format);
    }

    public static String generate(String fileName1, String fileName2) throws IOException {
        return generate(fileName1, fileName2, Formatter.FORMAT_STYLISH);
    }

    // Returns supported extension of specified filename or IOException
    public static String getFileExtension(String fileName) {
        String[] partsOfFileName = fileName.split("\\.");
        return partsOfFileName[partsOfFileName.length - 1];
    }

    // Returns content of specified file
    public static String readFile(String fileName) throws IOException {
        Path filePath = getPath(fileName);
        return Files.readString(filePath);
    }

    // Returns path of specified file
    public static Path getPath(String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize();
    }
}
