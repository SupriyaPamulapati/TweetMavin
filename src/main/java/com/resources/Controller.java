package com.resources;

import com.service.TwitterImplement;
import model.TwitterResponseModel;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@RestController
public class Controller {
    public static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    TwitterImplement twitterImplement;

    public Controller(TwitterImplement twitterImplement) {
        this.twitterImplement = twitterImplement;
    }

    public Controller() {

    }

    @Bean
    public TwitterImplement getTwitterData() {
        return new TwitterImplement();
    }

    @RequestMapping("/healthCheck")
    public String healthCheck() {
        return "Ping Received at " + new Date();
    }

    @RequestMapping("/getTweets")
    public Response fetchTweets() {
        return Response.ok(twitterImplement.getTweets()).build();
    }

    @RequestMapping("/filteredTweets/{search}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response filteredTweets(@PathVariable String search) throws TwitterException {
        List<TwitterResponseModel> response = twitterImplement.getFilteredTweets(search);
        return Response.ok(response).build();
    }

    @RequestMapping("/tweetsPage/start/{start}/size/{size}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagination(@PathVariable int start, @PathVariable int size) throws TwitterException {
        List<TwitterResponseModel> response;
        response = twitterImplement.getTweetsPage(start, size);
        return Response.ok(response).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postTweets")
    public Response sendTweet(@RequestBody MessageRequest request) throws TwitterException {
        logger.info("got into post");
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            logger.error("error happened");
            return Response.status(400, "Please enter valid tweet").build();
        } else {
            Status status = twitterImplement.sendTweet(post);
            if (status.getText().equals(post)) {
                logger.info("successfully posted");
                return Response.status(200, "Request is successful").build();
            } else {
                logger.error("internal error occurred");
                return Response.status(500, "internal server error").build();
            }
        }

    }


}
