package com.fx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication

// !!! NEVER annotate enable<repo>, if u do then every test every start that shit dog will create
// a lot of repo beans, for annotation enable<repo> use separate configuration
public class SpringInActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringInActionApplication.class, args);
    }

}
