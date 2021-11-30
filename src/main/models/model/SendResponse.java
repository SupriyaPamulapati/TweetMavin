package model;

public class SendResponse {
    private String message;
    private int statusCode;
    private int code;

    public SendResponse(int code,String message, int statusCode) {
        this.code=code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
