package UTwitter.resources;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Date;
import java.util.List;



    @Path("api/1.0/twitter")

    public class Resources {
        @GET
        @Path("/healthCheck")
        public String healthCheck(){
            return "ping Received"+ new Date();
        }

        @POST
        @Path("/post")
        public String tweetAgain() throws TwitterException {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("D4sBnyGzRjF8a78CW2aidR4zz")
                    .setOAuthConsumerSecret("r9CyHHov8tuMK9sfC4CHWmNgzpijv6DGCUipf1SXcySxB9XluD")
                    .setOAuthAccessToken("1450686116353376256-x1gEkshgxazcqGlkYwv2fDZAvIfW0s")
                    .setOAuthAccessTokenSecret("Pse1CapF8eN5isvZhevLwosdnHFJrU6JZlSolOiilnB2k");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            twitter.updateStatus("again");

            return "recieved a tweet";
        }

        @GET
        @Path("/get")
        public String timeline() throws TwitterException {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("D4sBnyGzRjF8a78CW2aidR4zz")
                    .setOAuthConsumerSecret("r9CyHHov8tuMK9sfC4CHWmNgzpijv6DGCUipf1SXcySxB9XluD")
                    .setOAuthAccessToken("1450743367696994308-WM1cr2g13cLdLWQEnXo4QlKPc4Foi6")
                    .setOAuthAccessTokenSecret("Pse1CapF8eN5isvZhevLwosdnHFJrU6JZlSolOiilnB2k");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            List<Status> status = twitter.getHomeTimeline();
            for (Status st : status) {
                System.out.println(st.getUser().getName() + "-------" + st.getText());

            }
            return "get a tweet";
        }



}
