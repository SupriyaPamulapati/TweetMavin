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
import java.util.stream.Collectors;


public class TwitterImplement {
    RestConfig restConfig;
    ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;
    Twitter twitter;
    TwitterResponseModel twitterResponseModel;
    Logger log = LoggerFactory.getLogger(TwitterImplement.class);


    public TwitterImplement() {
        restConfig = new RestConfig();
        configurationBuilder = restConfig.configurationBuilder();
        twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter = twitterFactory.getInstance();
    }

    public TwitterImplement(TwitterFactory twitterFactory, TwitterResponseModel twitterResponseModel) {
        this.twitterFactory = twitterFactory;
        this.twitter = twitterFactory.getInstance();
        this.twitterResponseModel = twitterResponseModel;
    }

    public Status sendTweet(String args) throws TwitterException {
        Logger log = LoggerFactory.getLogger(TwitterImplement.class);
        Status status;
        status = twitter.updateStatus(args);
        return status;
    }

    public ArrayList<TwitterResponseModel> getTweets() {
        ArrayList<TwitterResponseModel> arrayList = new ArrayList<>();
        try {
            List<Status> statuses = twitter.getHomeTimeline();
            for (int i = 0; i < statuses.size(); i++) {
                Status status = statuses.get(i);
                String profileImageUrl = status.getUser().getProfileImageURL();
                String name = status.getUser().getName();
                String message = status.getText();
                Date createdAt = status.getCreatedAt();
                Format format = new SimpleDateFormat("dd-mm-yyy HH:mm:ss");
                String date = format.format(createdAt);
                String twitterHandle = status.getUser().getScreenName();
                twitterResponseModel = new TwitterResponseModel(message, name, twitterHandle, profileImageUrl, date);
                arrayList.add(twitterResponseModel);
            }
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
        }
        return arrayList;
    }


    public List<TwitterResponseModel> getFilteredTweets(String tweets) {
        ArrayList<TwitterResponseModel> tweetList;
        List<TwitterResponseModel> filteredTweets;
         tweetList= getTweets();
        int len = tweets.length();
        CharSequence charSequence = tweets.subSequence(0, len);
        filteredTweets = tweetList.stream().filter(t -> t.getMessage().contains(charSequence)).collect(Collectors.toList());
        return filteredTweets;
    }


}
