package UTwitter.resources;

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


    public Controller(MessageRequest request, PostTweet postTweet, RetrieveTweets retrieveTweets) {
        this.request = request;
        this.postTweet = postTweet;
        this.retrieveTweets = retrieveTweets;
    }

    public Controller() {
    }

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return "Ping Received at " + new Date();
    }

    @GET
    @Path("getTweets")
    public Response fetchTweets() {
        RetrieveTweets retrieveTweets = new RetrieveTweets();
        return retrieveTweets.myTimeline();
    }

    @POST
    @Path("/postTweets")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendTweet(MessageRequest request) throws TwitterException {
        logger.info("got into post");
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            logger.error("error happened");
            return Response.status(400, "Please enter valid tweet").build();
        } else {
            PostTweet postTweet = new PostTweet();
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
