package UTwitter.resources;

import UTwitter.RestConfig;
import UTwitter.service.TwitterImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class PostTweet {
    RestConfig restConfig;

    public PostTweet(){
        this.restConfig=restConfig;
    }
    public static Status sendTweet(String args) throws TwitterException {
        Logger logger = LoggerFactory.getLogger(PostTweet.class);
        RestConfig restConfig = new RestConfig();
        ConfigurationBuilder configurationBuilder = restConfig.configurationBuilder();
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        Status status = null;
        try {
            if (args.length()!=0 )
                status = twitter.updateStatus(args);
            else
                status=null;
        }catch (Exception e){
            if(status==null){
                logger.error("duplicate tweets");
                throw new TwitterException("Tweet is duplicate");
            }
        }
        return status;
    }
}

