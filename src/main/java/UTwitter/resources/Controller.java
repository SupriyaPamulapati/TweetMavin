package UTwitter.resources;

import UTwitter.service.PostTweet;
import UTwitter.service.RetrieveTweets;
import UTwitter.service.TwitterImplement;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


@Produces(MediaType.APPLICATION_JSON)
@Path("/api/1.0/twitter")
public class Controller {
    public static final Logger logger = LoggerFactory.getLogger(Controller.class);
    MessageRequest request;
    PostTweet postTweet;
    RetrieveTweets retrieveTweets;
    TwitterImplement twitterImplement = new TwitterImplement();

    public Controller(MessageRequest request, PostTweet postTweet, RetrieveTweets retrieveTweets, TwitterImplement twitterImplement) {
        this.request = request;
        this.postTweet = postTweet;
        this.retrieveTweets = retrieveTweets;
        this.twitterImplement = twitterImplement;
    }

    public Controller() {
    }

    @GET
    @Path("GetTweets")
    public Response fetchTweets(MessageRequest request) {
        RetrieveTweets retrieveTweets = request.getRetrieveTweetsObject(twitterImplement);
        return retrieveTweets.myTimeline();
    }


    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return "Ping Received at " + new Date();
    }

    @POST
    @Path("/tweetAgain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendTweet(MessageRequest request) throws TwitterException {
        logger.info("got into post");
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            logger.error("error happened");
            return Response.status(400, "Please enter valid tweet").build();
        } else {
            PostTweet postTweet = request.getSendTweetObject(twitterImplement);
            Status status = postTweet.sendTweet(post);
            if (status.getText().equals(post)) {
                logger.info("successfully posted");
                return Response.status(200, "Request is successful").build();
            } else {
                logger.error("internal error occurred");
                return Response.status(500, "internal server error").build();
            }
        }

    }

}
