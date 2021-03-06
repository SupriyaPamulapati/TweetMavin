package com.test;

import com.resources.Controller;
import com.service.TwitterImplement;
import model.TwitterResponseModel;
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
public class RetrieveTweetsTest {
    Controller tweetPost;
    TwitterFactory twitterFactory;
    TwitterImplement twitterImplement;
    Twitter twitter;
    TwitterResponseModel twitterResponseModel;



    @Before
    public void setUp() {
        twitterResponseModel = Mockito.mock(TwitterResponseModel.class);
        tweetPost = Mockito.mock(Controller.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory, twitterResponseModel);

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
        List<TwitterResponseModel> str = new ArrayList<>();
        str.add(twitterResponseModel);
        when(tweetPost.fetchTweets()).thenReturn(str);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("haii");
        Response expectedTweet = Response.ok(arrayList).build();
        List<TwitterResponseModel> actualTweet = tweetPost.fetchTweets();
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet);
    }

    @Test
    public void testcase_noTweetsFound() {
        ArrayList<TwitterResponseModel> str = null;
        when(tweetPost.fetchTweets()).thenReturn(str);
        Response expectedTweet = Response.ok().build();
        List<TwitterResponseModel> actualTweet = tweetPost.fetchTweets();
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet);
    }

    @Test
    public void testCase_fetchTweet_successCase() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        Status s1 = mock(Status.class);
        Status s2 = mock(Status.class);
        Status s3 = mock(Status.class);
        User user=mock(User.class);
        when(responseList.size()).thenReturn(3);
        when(responseList.get(0)).thenReturn(s1);
        when(s1.getText()).thenReturn("Tweet1");
        when(responseList.get(1)).thenReturn(s2);
        when(s2.getText()).thenReturn("Tweet2");
        when(responseList.get(2)).thenReturn(s3);
        when(s3.getText()).thenReturn("Tweet3");
        when(s1.getUser()).thenReturn(user);
        when(s2.getUser()).thenReturn(user);
        when(s3.getUser()).thenReturn(user);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<String> expected = Arrays.asList("Tweet1", "Tweet2", "Tweet3");
        List<TwitterResponseModel> actual = twitterImplement.getTweets();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCase_emptyTimeline() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        when(responseList.size()).thenReturn(0);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<TwitterResponseModel> actual = twitterImplement.getTweets();
        Assert.assertEquals(Arrays.asList(), actual);
    }

}

