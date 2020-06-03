package softuni.homework.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.homework.adaptors.LocalDateTimeAdapter;
import softuni.homework.utils.FileIOUtil;
import softuni.homework.utils.ValidationUtil;
import softuni.homework.utils.XmlParser;
import softuni.homework.utils.impls.FileIOUtilImpl;
import softuni.homework.utils.impls.ValidationUtilImpl;
import softuni.homework.utils.impls.XmlParserImpl;

import java.util.Random;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting().create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public LocalDateTimeAdapter localDateTimeAdapter() {
        return new LocalDateTimeAdapter();
    }
}
