package UTwitter;

import UTwitter.resources.Resources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MyTweet extends Application<RestConfigurations> {
    public static void main(String[] args) throws Exception {

        new MyTweet().run(args);


    }

    @Override
    public void run(RestConfigurations restConfigurations, Environment environment) throws Exception {
        System.out.println("haii");
        environment.jersey().register(new Resources());

    }
}
