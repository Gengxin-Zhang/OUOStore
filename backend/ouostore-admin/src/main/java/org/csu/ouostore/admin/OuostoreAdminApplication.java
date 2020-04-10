package org.csu.ouostore.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.csu.ouostore")
@MapperScan("org.csu.ouostore.mapper")
public class OuostoreAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OuostoreAdminApplication.class, args);
    }

}
