package org.csu.ouostore.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.csu.ouostore")
public class OuostorePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(OuostorePortalApplication.class, args);
    }

}
