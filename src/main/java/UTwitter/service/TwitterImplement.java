package UTwitter.service;

import UTwitter.RestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


public class TwitterImplement {
    RestConfig restConfig;
    ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;
    Twitter twitter;
    Logger log = LoggerFactory.getLogger(TwitterImplement.class);

    public TwitterImplement() {
        restConfig = new RestConfig();
        configurationBuilder = restConfig.configurationBuilder();
        twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter = twitterFactory.getInstance();
    }

    public TwitterImplement(TwitterFactory twitterFactory) {
        this.twitterFactory = twitterFactory;
        this.twitter = twitterFactory.getInstance();
    }


    public Status sendTweet(String args) throws TwitterException {
        Logger logger = LoggerFactory.getLogger(TwitterImplement.class);
        Status status;
        status = twitter.updateStatus(args);
        return status;
    }

    public ArrayList<String> GetTweets(){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            List<Status> statuses = twitter.getHomeTimeline();
            for (int i=0;i<statuses.size();i++) {
                Status status = statuses.get(i);
                arrayList.add(status.getText());
            }
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
            if(arrayList.isEmpty()){
                log.error("no Tweets, Empty timeline");
                arrayList.add("No Tweets found");
            }
        }
        return arrayList;
    }
}