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
                        .setOAuthConsumerKey("QU7m7SGTagpG4IlxEUWrph2uI")
                        .setOAuthConsumerSecret("Lhr80fbsHYrHNPUa992OAAoeQEvNKToSZ5DqqTkgC4hKRnMycq")
                        .setOAuthAccessToken("1450686116353376256-cY0gGRKOp3NyLOeD6i8ke6fzm0ttRk")
                        .setOAuthAccessTokenSecret("KcuhOXd1MBnPw4qSgnWZ4oy25zRnJT2dha1XNzgje8AWv");
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();

                List<Status> status = twitter.getHomeTimeline();
                for (Status st : status) {
                        System.out.println(st.getUser().getName() + "-------" + st.getText());
                }
        }
    }



