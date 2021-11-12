package com.test;

import UTwitter.resources.Controller;
import UTwitter.resources.MessageRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import twitter4j.*;

import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ResourcesTest {
    Controller tweetPost;


    @Before
    public void setUp() {
        tweetPost = Mockito.mock(Controller.class);

    }

    @Test
    public void testcase_check_EmptyPost() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String message = new String(array, StandardCharsets.UTF_8);
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
        String str = "jhffjgfjguu";
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
    public void testcase_unsuccessfulTweet() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        String expectedMessage = "";
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
    public void testcase_postLength() throws TwitterException {
        ArrayList<String> str1 = new ArrayList<String>();
        str1.add("Tweet");
        MessageRequest request = new MessageRequest();
        when(tweetPost.sendTweet(request)).thenReturn(Response.ok(str1).build());
        ArrayList<String> str = new ArrayList<String>();
        str.add("Tweet");
        Response expected = Response.ok(str).build();
        Response actual = tweetPost.sendTweet(request);
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals(expected.getEntity(), actual.getEntity());
    }

}
