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

@Produces(MediaType.APPLICATION_JSON)
@Path("api/1.0/twitter")
public class Controller {
   /* ConfigurationBuilder configurationBuilder;
    TwitterFactory twitterFactory;
    public Controller() {
        configurationBuilder = RestConfig.configurationBuilder();
        twitterFactory = new TwitterFactory(configurationBuilder.build());
    }
    public Controller(ConfigurationBuilder configurationBuilder, TwitterFactory twitterFactory) {
        this.configurationBuilder = configurationBuilder;
        this.twitterFactory = twitterFactory;
    }*/

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return "ping Received" + new Date();
    }

    @GET
    @Path("/getTweets")
    public Response getTweets() {
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> status = null;
        try {
            status = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
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
            }
            return Response.ok(str).build();
        }
    }

    @GET
    @Path("/getTweet")
    public static ArrayList<String> timeline_Tweets() throws TwitterException {
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
        Twitter twitter = TwitterFactory.getSingleton();
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            return Response.status(400, "please Enter a valid tweet").build();
        } else {
            try {

                Status status = twitter.updateStatus(post);
                if (status.getText().equals(post)) {
                    return Response.status(200, "the tweet is successfully posted").build();
                } else {
                    return Response.status(500, "unable to process the request").build();
                }
            } catch (TwitterException e) {
                return Response.status(500, "the tweet is not successful").build();
            }
        }
    }

}

