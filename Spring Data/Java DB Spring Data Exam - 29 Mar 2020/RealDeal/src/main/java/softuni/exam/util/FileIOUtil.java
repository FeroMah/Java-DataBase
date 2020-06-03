package softuni.exam.util;

import java.io.IOException;

public interface FileIOUtil {

    String readFileContent(String path) throws IOException;

    void write(String content, String filePath) throws IOException;
}
