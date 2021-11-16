package UTwitter.resources;

import UTwitter.RestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RetrieveTweets {
    RestConfig restConfig;
    Logger log = LoggerFactory.getLogger(RetrieveTweets.class);

    public RetrieveTweets(){

    }
    public RetrieveTweets(RestConfig restConfig){
        this.restConfig=restConfig;
    }
    public Response myTimeline(){
        RestConfig restConfig = new RestConfig();
        ConfigurationBuilder configurationBuilder =restConfig.configurationBuilder();
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
            Twitter twitter=twitterFactory.getInstance();
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
                arrayList.add(status.getText());
            }
        } catch (TwitterException e) {
          log.error("error in retrieving tweets ");
            if(arrayList.isEmpty()){
                log.error("no Tweets, Empty timeline");
                arrayList.add("No Tweets found");
            }
        }
        return Response.ok(arrayList).build();

    }
}
