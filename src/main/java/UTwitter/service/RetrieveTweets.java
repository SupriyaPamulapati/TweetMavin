package UTwitter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

public class RetrieveTweets {
   TwitterImplement twitterImplement;
    Logger log = LoggerFactory.getLogger(RetrieveTweets.class);

    public RetrieveTweets(){

    }
    public RetrieveTweets(TwitterImplement twitterImplement){
        this.twitterImplement=twitterImplement;
    }
    public ArrayList<String> myTimeline(){
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
           Twitter twitter=twitterImplement.getTwitterObject();
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
        return arrayList;

    }
}
