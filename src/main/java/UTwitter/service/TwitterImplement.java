package UTwitter.service;

import UTwitter.RestConfig;
import model.TwitterResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TwitterImplement {
    RestConfig restConfig;
    ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;
    Twitter twitter;
    TwitterResponseModel _twitterResponseModel;
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
        Logger log = LoggerFactory.getLogger(TwitterImplement.class);
        Status status;
        status = twitter.updateStatus(args);
        return status;
    }

    public ArrayList<TwitterResponseModel> getTweets() {
        ArrayList<TwitterResponseModel> arrayList = new ArrayList<>();
        List<Status> statuses = null;
        try {
            statuses = twitter.getHomeTimeline();
            for (int i = 0; i < statuses.size(); i++) {
                String twitterHandle;
                String name;
                String message ;
                String profileImageUrl ;
                Date createdAt ;
                Status status = statuses.get(i);
                profileImageUrl = status.getUser().getProfileImageURL();
                 name = status.getUser().getName();
                 message = status.getText();
                 createdAt =status.getCreatedAt();
                 Format format = new SimpleDateFormat("dd-mm-yyy HH:mm:ss");
                 String date = format.format(createdAt);
                 twitterHandle = status.getUser().getScreenName();
                 _twitterResponseModel = new TwitterResponseModel(message,name,twitterHandle,profileImageUrl,date);
                arrayList.add(_twitterResponseModel);
            }
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
        }
        return arrayList;
    }
}