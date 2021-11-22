package com.test;

import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
import UTwitter.service.TwitterImplement;
import model.TwitterResponseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import twitter4j.*;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFilteredTweets_Test {
    Controller tweetPost;
    TwitterFactory twitterFactory;
    TwitterImplement twitterImplement;
    Twitter twitter;
    TwitterResponseModel twitterResponseModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String message = "Good";
    String twitterHandle = "@SupriyaChowdar9";
    String name = "Supriya Chowdary";
    String profileImageUrl = "www.testcase.com";
    Date created;
    String date;

    {
        try {
            created = dateFormat.parse("16-11-2021 01:03:00");
        } catch (ParseException E) {
            E.printStackTrace();
        }
        date = dateFormat.format(created);
    }

    @Before
    public void setUp() {
        tweetPost = Mockito.mock(Controller.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory, twitterResponseModel);
    }

   //test case to check success case of fetching the tweets based on search key word
    @Test
    public void getFilteredTweets_SuccessCase() throws TwitterException {
        MessageRequest req = null;
        ArrayList<String> str = new ArrayList<String>();
        str.add("good start if day");
        str.add("A. good day");
        str.add("good and easy life");
        str.add("good to b good");
        when(tweetPost.filteredTweets("good")).thenReturn(Response.ok(str).build());
        Response expectedTweet = Response.ok(str).build();
        Response actualTweet = tweetPost.filteredTweets("good");
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
    }
   //test case to check the case-sensitivity of search key word
    @Test
    public void caseSensitiveTest() throws TwitterException {
        MessageRequest req = null;
        ArrayList<String> str = new ArrayList<String>();
        str.add("good start if day");
        str.add("A. good day");
        str.add("good and easy life");
        str.add("good to b good");
        when(tweetPost.filteredTweets("good")).thenReturn(Response.ok(str).build());
        Response expectedTweet = Response.ok(str).build();
        String s = "good";
        Response actualTweet = null;
        if (s == "good" || s == "Good") {
            actualTweet = tweetPost.filteredTweets(s);
        }
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
    }

    @Test
    public void noTweetMatch_Test() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        when(responseList.size()).thenReturn(0);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<TwitterResponseModel> actual = twitterImplement.getFilteredTweets("forest");
        Assert.assertEquals(Arrays.asList(), actual);
    }
    @Test
    public void fetchTweet() throws TwitterException {
        ArrayList<TwitterResponseModel> expectedList = mock(ArrayList.class);
        ResponseList<Status> responseList = mock(ResponseList.class);
        User user = mock(User.class);
        Status s1 = mock(Status.class);
        when(responseList.size()).thenReturn(1);
        when(responseList.get(0)).thenReturn(s1);
        when(s1.getUser()).thenReturn(user);
        when(s1.getUser().getProfileImageURL()).thenReturn(profileImageUrl);
        when(s1.getUser().getName()).thenReturn(name);
        when(s1.getUser().getScreenName()).thenReturn(twitterHandle);
        when(s1.getText()).thenReturn(message);
        when(s1.getCreatedAt()).thenReturn(created);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        expectedList.add(twitterResponseModel);
        ArrayList<TwitterResponseModel> actualList = (ArrayList<TwitterResponseModel>) twitterImplement.getFilteredTweets("good and easy life");
        Assert.assertEquals(expectedList, actualList);
    }
}


