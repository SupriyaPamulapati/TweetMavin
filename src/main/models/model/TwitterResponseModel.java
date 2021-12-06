package model;

public class TwitterResponseModel {
    String message;
    User user;
    String createdAt;

    public TwitterResponseModel(String message, String twitterHandle, String name, String profileImageUrl, String createdAt) {
        this.message = message;
        this.user = new User(name, twitterHandle, profileImageUrl);
        this.createdAt = createdAt;
    }

    /**
     * .
     * Constructor
     */
    public TwitterResponseModel() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return createdAt;
    }

    public String getCreated() {
        return createdAt;
    }
}
