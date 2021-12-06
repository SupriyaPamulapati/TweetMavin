/*** this class task to take tweet from user
 */
package com.resources;

/*** this class task to take tweet from user
 */
public class MessageRequest {
    String msg;

    /**
     * .
     * constructor
     */
    public MessageRequest() {
    }

    /**
     * .
     * Constructor takes message and store
     *
     * @param msg
     */
    public MessageRequest(String msg) {
        this.msg = msg;
    }

    /**
     * @return retrun tweet
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
