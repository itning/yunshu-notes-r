package top.itning.yunshunotesr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * Spring Boot 启动类
 *
 * @author itning
 */
@SpringBootApplication
@ServletComponentScan
public class YunshuNotesRApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunshuNotesRApplication.class, args);
    }
}
