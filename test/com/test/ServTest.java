package com.test;

import UTwitter.resources.MessageRequest;
import UTwitter.service.PostTweet;
import UTwitter.service.RetrieveTweets;
import UTwitter.service.TwitterImplement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ServTest {
    PostTweet postTweet;
    Status status;
    MessageRequest Request;

    TwitterImplement twitterImplement;
    RetrieveTweets getTimelineTweets;
    Twitter twitter;
    Status s1;
    Status s2;
    Status s3;
    ResponseList<Status> responseList;

    @Before
    public void setUp() {
        twitter = Mockito.mock(Twitter.class);
        twitterImplement = Mockito.mock(TwitterImplement.class);
        s1 = Mockito.mock(Status.class);
        s2 = Mockito.mock(Status.class);
        s3 = Mockito.mock(Status.class);
        responseList = Mockito.mock(ResponseList.class);
        getTimelineTweets = new RetrieveTweets(twitterImplement);

        status = Mockito.mock(Status.class);
        postTweet = new PostTweet(twitterImplement);
    }

    @Test
    public void testCase_sendTweet_successCase() throws TwitterException {
        Request.setMsg("Sad moment missing Home");
        String expectedTweet = Request.getMsg();
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(twitter.updateStatus(expectedTweet)).thenReturn(status);
        when(status.getText()).thenReturn(expectedTweet);
        Status status = null;
        try {
            status = PostTweet.sendTweet(expectedTweet);
        } catch (TwitterException e) {
            //logger.error("Exception Occur",e);
        }
        String actualTweet = status.getText();
        Assert.assertEquals(expectedTweet, actualTweet);
    }

    @Test
    public void testCase_fetchTweet_successCase() throws TwitterException {
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        responseList.add(s1);
        responseList.add(s2);
        responseList.add(s3);
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        when(responseList.size()).thenReturn(3);
        when(responseList.get(1)).thenReturn(s1);
        when(s1.getText()).thenReturn("Tweet1");
        when(responseList.get(2)).thenReturn(s2);
        when(s2.getText()).thenReturn("Tweet2");
        when(responseList.get(3)).thenReturn(s3);
        when(s3.getText()).thenReturn("Tweet3");
        Response responseExpected = Response.ok(Arrays.asList("Tweet1", "Tweet2", "Tweet3")).build();
        Response responseActual = getTimelineTweets.myTimeline();
        Assert.assertEquals(responseExpected.getLength(), responseActual.getLength());
    }

    @Test
    public void testCase_fetchNoTweetOnTimeline_successCase() throws TwitterException {
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        responseList.add(null);
        when(responseList.size()).thenReturn(0);
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        Response responseExpected = Response.ok(responseList).build();
        Response responseActual = getTimelineTweets.myTimeline();
        Assert.assertEquals(responseExpected.getLength(), responseActual.getLength());

    }

}

