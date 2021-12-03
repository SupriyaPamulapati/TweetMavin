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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RetrieveTweetsTest {
    Controller tweetPost;
    TwitterFactory twitterFactory;
    TwitterImplement twitterImplement;
    Twitter twitter;
    TwitterResponseModel twitterResponseModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String twitterHandle="@Supriya";
    String name="Supriya";
    String message="try";
    String profileImageUrl="www.Supriya.com";
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
        arrayList.add(null);
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
    public void fetchTweetTest_successCase_listIsNotEmpty() throws TwitterException {
        ResponseList<Status> list = mock(ResponseList.class);
        List<TwitterResponseModel> listExpected=spy(ArrayList.class);
        User user=mock(User.class);
        Status s1 = mock(Status.class);
        when(list.size()).thenReturn(1);
        when(list.get(0)).thenReturn(s1);
        when(s1.getUser()).thenReturn(user);
        when(s1.getUser().getProfileImageURL()).thenReturn(profileImageUrl);
        when(s1.getUser().getName()).thenReturn(name);
        when(s1.getUser().getScreenName()).thenReturn(twitterHandle);
        when(s1.getText()).thenReturn(message);
        when(s1.getCreatedAt()).thenReturn(created);
        when(twitter.getHomeTimeline()).thenReturn(list);
        listExpected.add((twitterResponseModel));
        List<TwitterResponseModel> actualTweet = twitterImplement.getTweets();
        Assert.assertEquals(listExpected.size(),actualTweet.size());
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

