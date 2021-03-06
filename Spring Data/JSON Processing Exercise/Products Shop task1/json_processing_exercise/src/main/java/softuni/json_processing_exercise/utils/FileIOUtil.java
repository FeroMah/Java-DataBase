package softuni.json_processing_exercise.utils;

import java.io.IOException;

public interface FileIOUtil  {

    String readFileContent(String path) throws IOException;

    void write(String content, String filePath) throws IOException;
}
