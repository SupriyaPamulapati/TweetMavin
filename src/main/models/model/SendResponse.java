package model;

public class SendResponse {
    private String message;

    public SendResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Used to send response on tweet posted.
     *
     * @param message is a response message.
     */

    public void setMessage(String message) {
        this.message = message;
    }
}
