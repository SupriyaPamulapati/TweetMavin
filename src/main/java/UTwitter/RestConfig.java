package UTwitter;

import io.dropwizard.Configuration;
import javafx.beans.value.ObservableBooleanValue;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class RestConfig extends Configuration {
    public ConfigurationBuilder configurationBuilder(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("aI6oBe09H1qRqIijeD0T7FFf2")
                .setOAuthConsumerSecret("zDXT0LZVVGBDPOBMNzd6BrDtj2YGzmiNq5Wx4LXaLpKZeQbSU0")
                .setOAuthAccessToken("1450686116353376256-O4BbdN9sKVXedSj3iLjBAVuwnMQW9P")
                .setOAuthAccessTokenSecret("50L3TlfWTdVTtucJfdHbZKSdyi7IacvardUD0YyhL0zCA");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return configurationBuilder();
    }
}
