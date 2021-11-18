package com.test;

import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
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
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RetrieveTweetsTest_implement {
    Controller tweetPost;
    TwitterFactory twitterFactory;
    TwitterImplement twitterImplement;
    Twitter twitter;


    @Before
    public void setUp() {
        tweetPost = Mockito.mock(Controller.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory);
    }

    @Test
    public void testcase_searchTweets() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("SupriyaChowdar9");
        QueryResult result = twitter.search(query);
        boolean b = false;
        try {
            for (Status sts : result.getTweets()) {
                System.out.println("@" + sts.getUser().getScreenName() + sts.getText());
                b = true;
            }
        } catch (Exception e) {
            b = false;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testcase1_getTweets() {
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
        when(tweetPost.fetchTweets()).thenReturn(Response.ok().build());
        Response expectedTweet = Response.ok().build();
        Response actualTweet = tweetPost.fetchTweets();
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
    }

    @Test
    public void testCase_fetchTweet_successCase() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        Status s1 = mock(Status.class);
        Status s2 = mock(Status.class);
        Status s3 = mock(Status.class);
        when(responseList.size()).thenReturn(3);
        when(responseList.get(0)).thenReturn(s1);
        when(s1.getText()).thenReturn("Tweet1");
        when(responseList.get(1)).thenReturn(s2);
        when(s2.getText()).thenReturn("Tweet2");
        when(responseList.get(2)).thenReturn(s3);
        when(s3.getText()).thenReturn("Tweet3");
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<String> expected = Arrays.asList("Tweet1", "Tweet2", "Tweet3");
        List<String> actual = twitterImplement.GetTweets();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCase_emptyTimeline() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        when(responseList.size()).thenReturn(0);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<String> actual = twitterImplement.GetTweets();
        Assert.assertEquals(Arrays.asList(), actual);
    }

}
