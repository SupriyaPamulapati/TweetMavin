package test.resourceTest;

import UTwitter.RestConfig;
import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
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
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ResourcesTest {
    Controller tweetPost;
    RestConfig restConfig;




    @Before
    public void setUp() {
        restConfig = Mockito.mock(RestConfig.class);
        tweetPost = mock(Controller.class);

    }

    @Test
    public void testcase_check_EmptyPost() throws TwitterException {
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        Twitter twitter = TwitterFactory.getSingleton();
        String message = "good to go";
        boolean b;
        try {
            Status actual = twitter.updateStatus(message);
            b = true;
        } catch (Exception e) {
            b = false;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void test_post_RepeatedTweet() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        String expectedMessage = "Test";
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        Status status = null;
        try {
            status = twitter.updateStatus(expectedMessage);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        String actualMessage = status.getText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testcase_unsuccessfulTweet() throws TwitterException {
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

    @Test
    public void testcase_postLength() {
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        ArrayList<String> str1 = new ArrayList<String>();
        str1.add("Tweet");
        MessageRequest request = new MessageRequest();
        when(tweetPost.postTweets(request)).thenReturn(Response.ok(str1).build());
        ArrayList<String> str = new ArrayList<String>();
        str.add("Tweet");
        Response expected = Response.ok(str).build();
        Response actual = tweetPost.postTweets(request);
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals(expected.getEntity(), actual.getEntity());
    }

    @Test
    public void testcase_getTweets() throws TwitterException {
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        ArrayList<String> str = new ArrayList<String>();
        str.add("hii");
        when(tweetPost.getTweets()).thenReturn(Response.ok(str).build());
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("hii");
        Response expectedTweet = Response.ok(arrayList).build();
        Response actualTweet = tweetPost.getTweets();
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
    }

    @Test
    public void testcase_nullTweets_get() {
        when(tweetPost.getTweets()).thenReturn(Response.ok().build());
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        Response expectedTweet = Response.ok().build();
        Response actualTweet = tweetPost.getTweets();
        Assert.assertEquals(expectedTweet.getEntity(), actualTweet.getEntity());
        Assert.assertEquals(expectedTweet.getStatus(), actualTweet.getStatus());
    }

    @Test
    public void testcase_getTweets_timeline() throws TwitterException {
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
        Twitter twitter = TwitterFactory.getSingleton();
        ArrayList<String> arrayList = new ArrayList<String>();
        List<Status> status = twitter.getHomeTimeline();
        for (Status st : status) {
            arrayList.add(st.getText());
        }
        ArrayList<String> expected = arrayList;
        ArrayList<String> actual = Controller.timeline_Tweets();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testcase_searchTweets() throws TwitterException {
        when(restConfig.configurationBuilder()).thenReturn(new ConfigurationBuilder());
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
}
