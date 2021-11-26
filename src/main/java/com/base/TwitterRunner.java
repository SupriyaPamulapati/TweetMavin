package com.base;

import com.config.RestConfig;
import com.resources.Controller;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.resources","com.service","com.tweet","com.config,com.base"})
public class TwitterRunner extends Application<RestConfig> {
    private static final Logger logger = LoggerFactory.getLogger(TwitterRunner.class);
    RestConfig restConfig;
    Environment environment;

    public TwitterRunner(RestConfig restConfig, Environment environment) {
        this.restConfig = restConfig;
        this.environment = environment;
    }

    public TwitterRunner() {
    }

    public static void main(String[] args) throws Exception {
        logger.info("main method activated");
      //new TwitterRunner().run(args);
        SpringApplication.run(TwitterRunner.class,args);
    }

    @Override
    public void run(RestConfig restConfig, Environment environment) throws Exception {
        environment.jersey().register(new Controller());
    }
}
