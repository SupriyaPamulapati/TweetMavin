package UTwitter.resources;

import UTwitter.service.TwitterImplement;
import model.TwitterResponseModel;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Date;


@Produces(MediaType.APPLICATION_JSON)
@Path("/api/1.0/twitter")
public class Controller {
    public static final Logger logger = LoggerFactory.getLogger(Controller.class);
    TwitterImplement twitterImplement;



    public Controller(TwitterImplement twitterImplement)
    {
        this.twitterImplement = twitterImplement;
    }

    public Controller()
    {
        twitterImplement = new TwitterImplement();
    }

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return "Ping Received at " + new Date();
    }

    @GET
    @Path("/getTweets")
    public Response fetchTweets() {
      return Response.ok(twitterImplement.getTweets()).build();
    }

    @GET
    @Path("/filteredTweets")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response filteredTweets(@QueryParam("searchKey") String searchKey) throws TwitterException{
        List<TwitterResponseModel>  response ;
            response = twitterImplement.getFilteredTweets(searchKey) ;
            return Response.ok(response).build();
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
            Status status = twitterImplement.sendTweet(post);
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
