package UTwitter.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class PostTweet {
    static TwitterImplement twitterImplement;

    public PostTweet(TwitterImplement twitterImplement){

        this.twitterImplement = twitterImplement;
    }
    public static Status sendTweet(String args) throws TwitterException {
        Logger logger = LoggerFactory.getLogger(PostTweet.class);
        Twitter twitter =twitterImplement.getTwitterObject();
        Status status = null;
        try {
            if (args.length()!=0 ==true)
                status = twitter.updateStatus(args);
            else
                status=null;
        }catch (Exception e){
            if(args.length()>280 == false){
                logger.error("Tweets length is to long");
                throw new TwitterException("Tweet should be shorter");
            }
            if(status==null){
                logger.error("duplicate tweets");
                throw new TwitterException("Tweet is duplicate");
            }
        }
        return status;
    }
}

