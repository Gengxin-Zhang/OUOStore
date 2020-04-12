package org.csu.ouostore.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.csu.ouostore")
public class OuostoreAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OuostoreAdminApplication.class, args);
    }

}
