package UTwitter.resources;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;



    @Path("api/1.0/twitter")

    public class Resources {

        class Request{
            String  message;

        }

        @GET
        @Path("/healthCheck")
        public String healthCheck() {
            return "ping Received" + new Date();
        }

        @POST
        @Path("/post")
        public Response tweetpost(String post) {

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("cnmrNRHyyRDr7tQEp29gyz6Ue")
                    .setOAuthConsumerSecret("EiViCo8tBkbfxrZiUjqtWvDQmPTWTXsPvpzhIgEXFnzrVwCkd6")
                    .setOAuthAccessToken("1450686116353376256-WHmrrz0iJhqvslfd3PKwdofj5an7bU")
                    .setOAuthAccessTokenSecret("3H9hIiTTqa89WSoStyt1KOaBnEJKD7hBmVGIyoxXjtKAE");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            if(StringUtil.isEmpty(post)){
                return Response.status(400,"please Enter a valid tweet").build();
            }
            else {
                try {
                    twitter.updateStatus(post);
                }
                catch (TwitterException e) {
                    e.printStackTrace();
                }

                return Response.status(200,"tweet posted").build();
            }
        }

        @GET
        @Path("/get")
        public String tweetget() throws TwitterException {

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("cnmrNRHyyRDr7tQEp29gyz6Ue")
                    .setOAuthConsumerSecret("EiViCo8tBkbfxrZiUjqtWvDQmPTWTXsPvpzhIgEXFnzrVwCkd6")
                    .setOAuthAccessToken("1450686116353376256-WHmrrz0iJhqvslfd3PKwdofj5an7bU")
                    .setOAuthAccessTokenSecret("3H9hIiTTqa89WSoStyt1KOaBnEJKD7hBmVGIyoxXjtKAE");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            List<Status> status = twitter.getHomeTimeline();
            for (Status st : status) {
               System.out.println(st.getUser().getName() + "-------" + st.getText());

            }
            return "Tweet posted";
        }
    }

