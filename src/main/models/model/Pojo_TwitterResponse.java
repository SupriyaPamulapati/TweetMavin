package model;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

public class Pojo_TwitterResponse {
    User user;
    ArrayList<String> userHandles = new ArrayList<>();
    List<String> twitterTimelineList = new ArrayList<>();

    public Pojo_TwitterResponse(User user) {
        this.user = user;
    }

    public List<String> getTwitterTimelineList() {
        userHandles = user.getUserHandle();
        twitterTimelineList.add(user.getName());
        for (int i = 0; i < userHandles.size(); i++) {
            twitterTimelineList.add(userHandles.get(i));
        }
        twitterTimelineList.add(user.getProfileURL());
        return twitterTimelineList;
    }
}
