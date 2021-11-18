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

import java.util.ArrayList;
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
        user = new User();
        pojo_twitterResponse = new Pojo_TwitterResponse(user);
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

    public List<String> GetTweets() {
        List<String> twitterHandle = null;
        try {
            List<Status> statuses = twitter.getHomeTimeline();
            user.setName("User Name :"+statuses.get(0).getUser().getName());
            user.setProfileURL("profile URL"+statuses.get(0).getUser().getProfileImageURL());
            for (int i = 0; i < statuses.size(); i++) {
                Status status = statuses.get(i);
                user.setUserHandle("@"+status.getUser().getScreenName()+"  "+status.getText()+"  @Date-"+status.getCreatedAt()+"  "+status.getURLEntities());
            }
            twitterHandle=pojo_twitterResponse.getTwitterTimelineList();
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
        }
        return twitterHandle;
    }
}