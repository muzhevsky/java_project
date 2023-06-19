package muzhevsky.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "muzhevsky.org")
public class Main {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(Main.class);
    }
}