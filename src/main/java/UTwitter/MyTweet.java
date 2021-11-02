package UTwitter;

import UTwitter.resources.Resources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;


public class MyTweet extends Application<RestConfigurations> {
    public static void main(String[] args) throws Exception {
        new MyTweet().run(args);
    }

    @Override
    public void run(RestConfigurations restConfigurations, Environment environment) throws Exception {
        environment.jersey().register(new Resources());

    }
}
