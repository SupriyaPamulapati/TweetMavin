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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    String twitterHandle="@masum";
    String name="Raushan";
    String message="tweet1";
    String profileImageUrl="www.RajProfile.com";
    Date created;
    String date;

    @Before
    public void setUp() {
        tweetPost = Mockito.mock(Controller.class);
        twitter = mock(Twitter.class);
        twitterFactory = mock(TwitterFactory.class);
        when(twitterFactory.getInstance()).thenReturn(twitter);
        twitterImplement = new TwitterImplement(twitterFactory, twitterResponseModel);
    }

    @Test
    public void getFilteredTweets_SuccessCase() throws TwitterException {
        MessageRequest req = null;
        ArrayList<String> str = new ArrayList<String>();
        str.add("good start if day");
        str.add("A. good day");
        str.add("good and easy life");
        str.add("good to b good");
        when(tweetPost.filteredTweets("good")).thenReturn(Response.ok(str).build());
        String[] s= new String[]{"good start if day", "A. good day", "good and easy life", "good to b good"};
        Response expectedTweet = Response.ok(s).build();
        Response actualTweet = tweetPost.filteredTweets("good");
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
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
    public void testCase_fetchFilterTweet_successCase() throws TwitterException{
        Status s1 = mock(Status.class);
        User user=mock(User.class);
        ResponseList<Status> responseList = mock(ResponseList.class);
        List<TwitterResponse> twitListExpected=mock(ArrayList.class);
        when(responseList.size()).thenReturn(1);
        when(responseList.get(0)).thenReturn(s1);
        when(s1.getUser()).thenReturn(user);
        when(s1.getUser().getProfileImageURL()).thenReturn("SupriyaChowdar9");
        when(s1.getUser().getName()).thenReturn("Supriya Chowdary");
        when(s1.getUser().getScreenName()).thenReturn(twitterHandle);
        when(s1.getText()).thenReturn(message);
        when(s1.getCreatedAt()).thenReturn(created);
        when(twitter.getHomeTimeline()).thenReturn(responseList);
        twitListExpected.add(null);
        List<TwitterResponseModel> actualListExpected = twitterImplement.getFilteredTweets("good");
        Assert.assertEquals(twitListExpected,actualListExpected);
    }

}


