package UTwitter.resources;

import org.eclipse.jetty.util.StringUtil;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
    @Produces(MediaType.APPLICATION_JSON)
    @Path("api/1.0/twitter")

    public class Resources {
        @GET
        @Path("/healthCheck")
        public String healthCheck() {
            return "ping Received" + new Date();
        }

        @POST
        @Path("/postTweets")
        public Response tweetPost(Request request) {

            Twitter twitter = TwitterFactory.getSingleton();

            String post=request.getMsg();
            if(StringUtil.isEmpty(post)){
                return Response.status(400,"please Enter a valid tweet").build();
            }
            else {
                try {
                    twitter.updateStatus(post);
                }
                catch (TwitterException e) {
                    return Response.status(500,"tweet posted").build();
                }
                return Response.status(200,"tweet posted").build();
            }
        }

        @GET
        @Path("/getTweets")
        public Object tweetGet() throws TwitterException {

            Twitter twitter = TwitterFactory.getSingleton();
            List<Status> status = twitter.getHomeTimeline();
            int size = status.size();
            String str[] = new String[size];
            int i=0;

            if(StringUtil.isEmpty(String.valueOf(str))) {
                return Response.status(400, "please Enter a valid tweet").build();
            }
            else {
                for (Status st : status) {
                    str[i]=st.getUser().getName() + "-------" + st.getText();
                    i++;
                   }
                return str;
                }
        }
    }

