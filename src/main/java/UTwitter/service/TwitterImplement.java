package UTwitter.service;

import UTwitter.RestConfig;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterImplement {
    RestConfig restConfig;
    ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;

    public TwitterImplement(RestConfig restConfig, ConfigurationBuilder configurationBuilder, TwitterFactory twitterFactory) {
        this.restConfig = restConfig;
        this.configurationBuilder = configurationBuilder;
        this.twitterFactory = twitterFactory;
    }

    public TwitterImplement() {

    }

    public Twitter getTwitterObject() {
        RestConfig restConfig = new RestConfig();
        ConfigurationBuilder configurationBuilder = restConfig.configurationBuilder();
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        return twitter;
    }
}