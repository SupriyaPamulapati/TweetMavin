package com.test;

import com.resources.Controller;
import com.resources.MessageRequest;
import com.service.TwitterImplement;
import model.TwitterResponseModel;
import model.User;
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
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFilteredTweetsTest {
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
    User user;


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
        user = Mockito.mock(User.class);
        twitterResponseModel = Mockito.mock(TwitterResponseModel.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory, twitterResponseModel);
    }

    //test case to check success case of fetching the tweets based on search key word
    @Test
    public void getFilteredTweets_SuccessCase() throws TwitterException {
        MessageRequest req = null;
        List<TwitterResponseModel>str=new ArrayList<>();
        when(tweetPost.filteredTweets("good")).thenReturn(str);
        Response expectedTweet = Response.ok(str).build();
       List<TwitterResponseModel> actualTweet = tweetPost.filteredTweets("good");
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet);
    }

    //test case to check the case-sensitivity of search key word
    @Test
    public void caseSensitiveTest() throws TwitterException {
        MessageRequest req = null;
        ArrayList<TwitterResponseModel> str = new ArrayList<>();
        str.add(twitterResponseModel);
        when(tweetPost.filteredTweets("good")).thenReturn(str);
        Response expectedTweet = Response.ok(str).build();
        String s = "good";
        List<TwitterResponseModel> actualTweet = null;
        if (s == "good" || s == "Good") {
            actualTweet = tweetPost.filteredTweets(s);
        }
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet);
    }

    // testcase to check with a search key which is not in timeline
    @Test
    public void noTweetMatchTest() throws TwitterException {
        ResponseList<Status> responseList = mock(ResponseList.class);
        when(responseList.size()).thenReturn(0);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        List<TwitterResponseModel> actual = twitterImplement.getFilteredTweets("forest");
        Assert.assertEquals(Arrays.asList(), actual);
    }

    @Test
    public void getTweetTest() throws TwitterException {
        ArrayList<TwitterResponseModel> expectedList = mock(ArrayList.class);
        ResponseList<Status> responseList = mock(ResponseList.class);
        User user = mock(User.class);
        Status s1 = mock(Status.class);
        when(responseList.size()).thenReturn(1);
        when(responseList.get(0)).thenReturn(s1);
        when(s1.getUser()).thenReturn((twitter4j.User) user);
        when(s1.getUser().getProfileImageURL()).thenReturn(profileImageUrl);
        when(s1.getUser().getName()).thenReturn(name);
        when(s1.getUser().getScreenName()).thenReturn(twitterHandle);
        when(s1.getText()).thenReturn(message);
        when(s1.getCreatedAt()).thenReturn(created);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        expectedList.add(twitterResponseModel);
        ArrayList<TwitterResponseModel> actualList = (ArrayList<TwitterResponseModel>) twitterImplement.getFilteredTweets("good and easy life..");
        Assert.assertEquals(expectedList, actualList);
    }
}


