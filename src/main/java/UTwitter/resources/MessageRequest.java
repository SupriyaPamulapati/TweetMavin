package UTwitter.resources;

import UTwitter.service.RetrieveTweets;
import UTwitter.service.PostTweet;
import UTwitter.service.TwitterImplement;

public class MessageRequest {
    String msg;

    TwitterImplement twitterImplement;
    public MessageRequest(TwitterImplement twitterImplement) {
        this.twitterImplement=twitterImplement;
    }

    public MessageRequest() {

    }
    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public PostTweet getSendTweetObject(TwitterImplement twitterImplement)
    {
        return new PostTweet(twitterImplement);
    }
    public RetrieveTweets getRetrieveTweetsObject(TwitterImplement twitterImplement)
    {
        return new RetrieveTweets(twitterImplement);
    }
}
