package com.test;

import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
import UTwitter.resources.PostTweet;
import UTwitter.resources.RetrieveTweets;
import UTwitter.service.TwitterImplement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import twitter4j.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RetrieveTweetsTest {
    Controller tweetPost;
    PostTweet postTweet;
    Status status;
    MessageRequest messageRequest;

    TwitterImplement twitterImplement;
    RetrieveTweets getTimelineTweets;
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
        s1 = Mockito.mock(Status.class);
        s2 = Mockito.mock(Status.class);
        s3 = Mockito.mock(Status.class);
        responseList = Mockito.mock(ResponseList.class);
        getTimelineTweets = new RetrieveTweets();

        status = Mockito.mock(Status.class);
        postTweet = new PostTweet();
        messageRequest = new  MessageRequest();
    }
    @Test
    public void testcase_searchTweets() throws TwitterException {
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("SupriyaChowdar9");
        QueryResult result = twitter.search(query);
        boolean b = false;
        try {
            for (Status sts : result.getTweets()) {
                System.out.println("@" + sts.getUser().getScreenName() +sts.getText());
                b = true;
            }
        } catch (Exception e) {
            b = false;
        }
        Assert.assertTrue(b);
    }
    @Test
    public void testcase1_getTweets() {
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        MessageRequest req = null;
        ArrayList<String> str = new ArrayList<String>();
        str.add("hlo");
        when(tweetPost.fetchTweets()).thenReturn(Response.ok(str).build());
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("hlo");
        Response expectedTweet = Response.ok(arrayList).build();
        Response actualTweet = tweetPost.fetchTweets();
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
    }
    @Test
    public void testcase_noTweetsFound() {
        MessageRequest req = null;
        when(twitterImplement.getTwitterObject()).thenReturn(twitter);
        when(tweetPost.fetchTweets()).thenReturn(Response.ok().build());
        Response expectedTweet = Response.ok(req).build();
        Response actualTweet = tweetPost.fetchTweets();
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
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
        Assert.assertEquals(responseExpected.getEntity(), responseActual.getEntity());
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

