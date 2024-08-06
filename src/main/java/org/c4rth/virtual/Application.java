package org.c4rth.virtual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner1(ApplicationContext ctx) {
        return args -> {
            var environment = ctx.getEnvironment();
            if (environment instanceof ConfigurableEnvironment ce) {
                ce.getPropertySources().forEach(ps -> {
                    log.info("{} = {}", ps.getName(), ps.getClass().getName());
                    if (ps instanceof EnumerablePropertySource<?> eps) {
                        var str1 = Arrays.stream(eps.getPropertyNames()).map(key -> key + " = " + eps.getProperty(key))
                                .collect(Collectors.joining(",\n", "{", "}"));
                        log.info("    properties = {}", str1);
                    } else {
                        log.info("    properties = NOT EnumerablePropertySource");
                    }
                });
                List.of("spring.application.name", "k8s.key1", "k8s.key2", "spring.cloud.kubernetes.config.paths", "spring.cloud.kubernetes.config.enabled").forEach(key -> {
                    var x = environment.getProperty(key);
                    log.info("{} = {}", key, x);
                });
            } else {
                log.warn("environment NOT ConfigurableEnvironment");
            }
        };
    }

}
