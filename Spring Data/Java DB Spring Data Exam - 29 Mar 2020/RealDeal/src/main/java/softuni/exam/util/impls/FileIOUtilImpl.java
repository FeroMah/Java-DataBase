package softuni.exam.util.impls;


import softuni.exam.util.FileIOUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;


public class FileIOUtilImpl implements FileIOUtil {
    @Override
    public String readFileContent(String path) throws IOException {
//        File file = new File(path);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line=reader.readLine())!=null){
//            sb.append(line).append(System.lineSeparator());
//        }
//        return sb.toString().trim();
        return Files.readAllLines(Paths.get(path)).stream().
                filter(x -> !x.isEmpty()).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void write(String content, String filePath) throws IOException {
        Files.write(Paths.get(filePath), Collections.singleton(content), StandardCharsets.UTF_8);
    }
}
