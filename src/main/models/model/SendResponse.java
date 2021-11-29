package model;

public class SendResponse {
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private int statusCode;



    public SendResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public SendResponse() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
