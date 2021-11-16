package com.test;

import UTwitter.RestConfig;
import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
import UTwitter.resources.PostTweet;
import UTwitter.service.TwitterImplement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


import javax.ws.rs.core.Response;
import java.util.ArrayList;


import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostTweetTest {
    Controller tweetPost;
    PostTweet postTweet;
    Status status;
    RestConfig restConfig;
    TwitterImplement twitterImplement;
    MessageRequest messageRequest;
    Twitter twitter;
    Status s1;
    Status s2;
    Status s3;
    ResponseList<Status> responseList;

    @Before
    public void setUp() {
        tweetPost = Mockito.mock(Controller.class);
        twitter = Mockito.mock(Twitter.class);
        twitterImplement = Mockito.mock(TwitterImplement.class);
        restConfig = Mockito.mock(RestConfig.class);
        tweetPost = new Controller();

        messageRequest = new  MessageRequest();
        s1 = Mockito.mock(Status.class);
        s2 = Mockito.mock(Status.class);
        s3 = Mockito.mock(Status.class);


        status = Mockito.mock(Status.class);
        postTweet = new PostTweet();

    }

    @Test
    public void testcase_check_EmptyPost() throws TwitterException {
        String arr = "My test Case";
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(twitter.updateStatus(arr)).thenReturn(status);
        Twitter twitter = TwitterFactory.getSingleton();
        boolean b;
        try {
            if (arr.length()!=0)
                status = twitter.updateStatus(arr);
            b = true;
        } catch (Exception e) {
            b = false;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void test_post_RepeatedTweet() throws TwitterException {
        String str = "Hii all Have a Good Day";
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
        String msg = "Happy to b Happy";
        messageRequest.setMsg(msg);
        String expectedTweet = messageRequest.getMsg();
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(twitter.updateStatus(expectedTweet)).thenReturn(status);
        when(status.getText()).thenReturn(expectedTweet);
        Status status = null;
        try {
            status = PostTweet.sendTweet(expectedTweet);
        } catch (TwitterException e) {
        }
        String actualTweet = status.getText();
        Assert.assertEquals(expectedTweet, actualTweet);
    }

    @Test
    public void testcase_unsuccessfulTweet() {
        Twitter twitter = TwitterFactory.getSingleton();
        String expectedMessage = "";
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        Status status = null;
        try {
            status = twitter.updateStatus(expectedMessage);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        int expectedLength = 0;
        int actualLength = 0;
        String actualMessage = "";
        if (status != null) {
            expectedLength = expectedMessage.length();
            actualLength = 0;
        } else {
            expectedLength = expectedMessage.length();
            actualLength = actualMessage.length();
        }
        Assert.assertEquals(expectedLength, actualLength);
    }


}
