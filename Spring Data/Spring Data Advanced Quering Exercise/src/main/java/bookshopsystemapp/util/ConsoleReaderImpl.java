package bookshopsystemapp.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleReaderImpl implements ConsoleReader {
    private BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
    @Override
    public String read()  {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
           return e.getMessage();
        }
    }
}
