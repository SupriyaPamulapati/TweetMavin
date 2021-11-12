package UTwitter.tweet;

import UTwitter.RestConfig;
import UTwitter.resources.Controller;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyTweet extends Application<RestConfig> {
    RestConfig restConfig;
    Environment environment;
    private static Logger logger= LoggerFactory.getLogger(MyTweet.class);

    public MyTweet(RestConfig restConfig, Environment environment) {
        this.restConfig = restConfig;
        this.environment = environment;
    }

    public MyTweet() {
    }

    public static void main(String[] args) throws Exception {
        logger.info("main method activated");
        new MyTweet().run(args);
    }

    @Override
    public void run(RestConfig restConfig, Environment environment) throws Exception {
        environment.jersey().register(new Controller());
    }
}
