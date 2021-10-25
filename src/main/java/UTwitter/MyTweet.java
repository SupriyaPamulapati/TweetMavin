package UTwitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MyTweet {
    public static void main(String[] args) throws TwitterException {

        MyTimeline MyTimelineobject = new MyTimeline();
        MyTimelineobject.timeline();


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("*********************")
                .setOAuthConsumerSecret("******************************************")
                .setOAuthAccessToken("**************************************************")
                .setOAuthAccessTokenSecret("******************************************");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        twitter.updateStatus("my new tweet ");



    }
}
