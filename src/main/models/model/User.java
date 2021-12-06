package model;

/**
 * User class contain user details
 */
public class User {
    String twitterHandle;
    String name;
    String profileImageUrl;

    public User(String twitterHandle, String name, String profileImageUrl) {
        this.twitterHandle = twitterHandle;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

