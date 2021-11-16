package UTwitter.resources;


import UTwitter.service.TwitterImplement;

public class MessageRequest {
    String msg;
    TwitterImplement twitterImplement;

    public MessageRequest() {

    }
    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }

}
