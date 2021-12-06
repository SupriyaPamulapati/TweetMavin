package com.service;

import com.config.RestConfig;
import model.TwitterResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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


@CacheConfig(cacheNames = {"getTweets", "filteredTweets"})
@Service
public class TwitterImplement {
    RestConfig restConfig;
    ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;
    TwitterResponseModel twitterResponseModel;
    Twitter twitter;
    Logger log = LoggerFactory.getLogger(TwitterImplement.class);

    /**
     * .
     * Used for test case.
     * this constructor is used for getting Twitter object
     * based on the authentication of user
     */
    public TwitterImplement() {
        restConfig = new RestConfig();
        configurationBuilder = restConfig.configurationBuilder();
        twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter = twitterFactory.getInstance();

    }

    /**
     * .
     * Used for test case.
     *
     * @param twitterFactory
     * @param twitterResponseModel
     */
    public TwitterImplement(TwitterFactory twitterFactory, TwitterResponseModel twitterResponseModel) {
        this.twitterFactory = twitterFactory;
        this.twitter = twitterFactory.getInstance();
        this.twitterResponseModel = twitterResponseModel;
    }

    /**
     * @param args
     * @return This method will return Status object
     * which contains status of tweet which is posted on timeline
     */
    @Cacheable(cacheNames = {"getTweets"})
    @CacheEvict(cacheNames = {"getTweets"}, allEntries = true)
    public Status sendTweet(String args) throws TwitterException {
        Logger log = LoggerFactory.getLogger(TwitterImplement.class);
        Status status;
        status = twitter.updateStatus(args);
        return status;
    }

    /**
     * getTweets() used to get tweets from user timeline.
     *
     * @return returns tweets to resources class.
     */
    @Cacheable(cacheNames = {"getTweets"})
    @Scheduled(fixedRate = 2000)
    public List<TwitterResponseModel> getTweets() {
        log.info("Started get tweets method");
        List<TwitterResponseModel> tweetList = new ArrayList<>();
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
                tweetList.add(twitterResponseModel);
            }
        } catch (TwitterException e) {
            log.error("error in retrieving tweets ");
        }
        return tweetList;
    }

    /**
     * getFilteredTweets() used to get filtered tweets from user timeline.
     *
     * @param tweets is used to search in a list of tweets.
     * @return returns filtered tweets.
     */

    @Cacheable(cacheNames = {"filters"})
    public List<TwitterResponseModel> getFilteredTweets(String tweets) {
        List<TwitterResponseModel> tweetList = getTweets();
        int len = tweets.length();
        CharSequence charSequence = tweets.subSequence(0, len);
        List<TwitterResponseModel> filteredTweets = tweetList.stream().filter(t -> t.getMessage().contains(charSequence)).collect(Collectors.toList());
        return filteredTweets;
    }

    /**
     * etTweetsPage() used to get filtered tweets from user timeline.
     *
     * @param start size is used to search in a list of tweets.
     * @return returns filtered tweets.
     */
    @Cacheable(cacheNames = {"pages"})
    public List<TwitterResponseModel> getTweetsPage(int start, int size) throws TwitterException {
        List<TwitterResponseModel> tweetPage = getTweets();
        return tweetPage.subList(start, start + size);
    }
}
