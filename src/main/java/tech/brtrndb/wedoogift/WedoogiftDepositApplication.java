package tech.brtrndb.wedoogift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import tech.brtrndb.wedoogift.config.ApplicationProperties;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "classpath:/config/application.yml", ignoreResourceNotFound = false),
        @PropertySource(value = "classpath:META-INF/build-info.properties", ignoreResourceNotFound = false)
})
@EnableConfigurationProperties({ApplicationProperties.class})
public class WedoogiftDepositApplication {

    public static void main(String[] args) {
        SpringApplication.run(WedoogiftDepositApplication.class, args);
    }

}
