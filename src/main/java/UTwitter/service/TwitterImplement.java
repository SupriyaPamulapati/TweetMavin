package UTwitter.service;

import UTwitter.RestConfig;
import model.Pojo_TwitterResponse;
import model.User;
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
    User user;
    Pojo_TwitterResponse pojo_twitterResponse;
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

    public ArrayList<Pojo_TwitterResponse> GetTweets() {
        String twitterHandle;
        String name;
        String message = null;
        String profileImageUrl = null;
        Date createdAt = null;

        ArrayList<Pojo_TwitterResponse> arrayList = new ArrayList<>();
        List<Status> statuses = null;
        try {
            statuses = twitter.getHomeTimeline();
            for (int i = 0; i < statuses.size(); i++) {
                Status status = statuses.get(i);
                profileImageUrl = status.getUser().getProfileImageURL();
                 name = status.getUser().getName();
                 message = status.getText();
                 createdAt =status.getCreatedAt();
                 Format format = new SimpleDateFormat("dd-mm-yyy HH:mm:ss");
                 String date = format.format(createdAt);
                 twitterHandle = status.getUser().getScreenName();
                 pojo_twitterResponse = new Pojo_TwitterResponse(message,name,twitterHandle,profileImageUrl,date);
                arrayList.add(pojo_twitterResponse);
            }
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
        }
        return arrayList;
    }
}