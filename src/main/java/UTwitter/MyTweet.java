package UTwitter;

import UTwitter.resources.Controller;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;


public class MyTweet extends Application<RestConfig> {
    public static void main(String[] args) throws Exception {
        new MyTweet().run(args);
    }

    @Override
    public void run(RestConfig restConfigurations, Environment environment) throws Exception {
        environment.jersey().register(new Controller());
    }
}
