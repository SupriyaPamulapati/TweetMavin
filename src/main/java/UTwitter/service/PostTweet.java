package UTwitter.service;

import UTwitter.RestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class PostTweet {
   TwitterImplement twitterImplement;

    public PostTweet(TwitterImplement twitterImplement){
        this.twitterImplement=twitterImplement;
    }

    public PostTweet() {

    }

    public Status sendTweet(String args) throws TwitterException {
        Logger logger = LoggerFactory.getLogger(PostTweet.class);
        Twitter twitter=twitterImplement.getTwitterObject();
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

