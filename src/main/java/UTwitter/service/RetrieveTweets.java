package UTwitter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RetrieveTweets {
    TwitterImplement twitterService;
    Logger log = LoggerFactory.getLogger(RetrieveTweets.class);

    public RetrieveTweets(TwitterImplement twitterService){
        this.twitterService= twitterService;
    }
    public RetrieveTweets(){

    }
    public Response myTimeline(){
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            Twitter twitter=twitterService.getTwitterObject();
            System.out.println("hiuii"+twitter.getClass().getName());
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
                arrayList.add(status.getText());
            }
        } catch (TwitterException e) {
          log.error("error in retrieving tweets ");
        }
        if(arrayList.isEmpty()){
            log.error("no Tweets, Empty timeline");
            arrayList.add("No Tweets found");
        }
        return Response.ok(arrayList).build();

    }
}
