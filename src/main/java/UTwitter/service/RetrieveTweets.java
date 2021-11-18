package UTwitter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetrieveTweets {
    TwitterImplement twitterImplement;
    Logger logger= LoggerFactory.getLogger(RetrieveTweets.class);
    public RetrieveTweets(TwitterImplement twitterImplement)
    {
        this.twitterImplement=twitterImplement;
    }
    public RetrieveTweets(){}
}