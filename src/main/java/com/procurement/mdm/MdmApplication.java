package com.procurement.mdm;

import com.procurement.mdm.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(
        scanBasePackageClasses = {ApplicationConfig.class},
        exclude = {LiquibaseAutoConfiguration.class}
)
public class MdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdmApplication.class, args);
    }
}
