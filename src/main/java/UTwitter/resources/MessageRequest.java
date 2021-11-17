package UTwitter.resources;


public class MessageRequest {
    String msg;

    public MessageRequest() {
    }
    public MessageRequest(String msg){
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
