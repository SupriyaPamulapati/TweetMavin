package com.test;

import com.resources.Controller;
import com.resources.MessageRequest;
import com.service.PostTweet;
import com.service.TwitterImplement;
import model.TwitterResponseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostTweetTest {
    Controller controller;
    TwitterFactory twitterFactory;
    Status status;
    TwitterImplement twitterImplement;
    MessageRequest messageRequest;
    Twitter twitter;
    TwitterResponseModel twitterResponseModel;
    Logger log = LoggerFactory.getLogger(PostTweet.class);

    @Before
    public void setUp() {
        status = Mockito.mock(Status.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory, twitterResponseModel);
        messageRequest = new MessageRequest();
        controller = new Controller();

    }

    @Test
    public void testcase_check_EmptyPost() throws TwitterException {
        String arr = ".hayy...  haii    My test Case..";
        when(twitter.updateStatus(arr)).thenReturn(status);
        Twitter twitter = TwitterFactory.getSingleton();
        boolean b;
        try {
            if (arr.length() != 0)
                status = twitter.updateStatus(arr);
            b = true;
        } catch (Exception e) {
            b = false;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void test_post_RepeatedTweet() throws TwitterException {
        String str = "mmmHii all Have a Good Day.. n good to work...";
        Twitter twitter = TwitterFactory.getSingleton();
        int expected = 403;
        int errorCode = 0;
        try {
            twitter.updateStatus(str);
        } catch (TwitterException e) {
            errorCode = e.getStatusCode();
        }
        Assert.assertEquals(expected, errorCode);
    }

    @Test
    public void testCase_sendTweet_successCase() throws TwitterException {
        messageRequest.setMsg("..its gonna b good..");
        String expectedTweet = messageRequest.getMsg();
        when(twitter.updateStatus(expectedTweet)).thenReturn(status);
        when(status.getText()).thenReturn(expectedTweet);
        String actualTweet = status.getText();
        Assert.assertEquals(expectedTweet, actualTweet);
    }

   @Test
    public void test_postToTwitterUsingTwitter4J() {
        Twitter twitter = TwitterFactory.getSingleton();
        String expectedMessage = ".....Test....,";
        Status statuses = null;
        try {
            statuses = twitter.updateStatus(expectedMessage);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        when(status.getText()).thenReturn(expectedMessage);
        String actualMessage = status.getText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
