package UTwitter.resources;

import UTwitter.RestConfig;
import org.eclipse.jetty.util.StringUtil;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces(MediaType.APPLICATION_JSON)
@Path("api/1.0/twitter")
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
       log.info("processing HealthCheck");
        return "ping Received" + new Date();
    }

    @GET
    @Path("/getTweets")
    public Response getTweets() {
        log.info("Processing get request");
        log.info("get");
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> status = null;
        try {
            status = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
            log.error("error",e);
        }
        int size = status.size();
        String[] str = new String[size];
        int i = 0;

        if (StringUtil.isEmpty(String.valueOf(str))) {
            return Response.status(400, "please Enter a valid tweet").build();
        } else {
            for (Status st : status) {
                str[i] = st.getUser().getName() + "-------" + st.getText();
                i++;
                log.info("Successfully retrieved Tweets");
            }
            return Response.ok(str).build();
        }
    }

    @GET
    @Path("/getTweet")
    public static ArrayList<String> timeline_Tweets() throws TwitterException {
        log.info("processing get Tweets");
        Twitter twitter = TwitterFactory.getSingleton();
        ArrayList<String> arrayList = new ArrayList<String>();
        List<Status> status = twitter.getHomeTimeline();
        for (Status st : status) {
            arrayList.add(st.getText());
        }
        return arrayList;
    }

    @POST
    @Path("/postTweets")
    public Response postTweets(MessageRequest request) {
        log.info("Processing post request");
        Twitter twitter = TwitterFactory.getSingleton();
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            log.error("please enter some valid tweet");
            return Response.status(400, "please Enter a valid tweet").build();
        } else {
            try {

                Status status = twitter.updateStatus(post);
                if (status.getText().equals(post)) {
                    log.info("Tweet request Successful");
                    return Response.status(200, "the tweet is successfully posted").build();
                } else {
                    log.error("unable to process request");
                    return Response.status(500, "unable to process the request").build();
                }
            } catch (TwitterException e) {
                log.error("Tweet request is unsuccessful",e);
                return Response.status(500, "the tweet is not successful").build();
            }
        }
    }

}


