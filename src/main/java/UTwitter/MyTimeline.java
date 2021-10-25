package UTwitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class MyTimeline {
        public void timeline() throws TwitterException {


                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("*********************")
                        .setOAuthConsumerSecret("******************************************")
                        .setOAuthAccessToken("**************************************************")
                        .setOAuthAccessTokenSecret("******************************************");
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();

                List<Status> status = twitter.getHomeTimeline();
                for (Status st : status) {
                        System.out.println(st.getUser().getName() + "-------" + st.getText());
                }
        }
    }



