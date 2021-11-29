package com.resources;

import com.service.TwitterImplement;
import model.SendResponse;
import model.TwitterResponseModel;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @RequestMapping("/healthCheck")
    public String healthCheck() {
        return "Ping Received at " + new Date();
    }

    @RequestMapping(method =  RequestMethod.GET,value = "/getTweets")
    public List<TwitterResponseModel> fetchTweets() {
        return twitterImplement.getTweets();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/filteredTweets")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TwitterResponseModel> filteredTweets(@QueryParam("searchKey") String searchKey) throws TwitterException {
        List<TwitterResponseModel> response = twitterImplement.getFilteredTweets(searchKey);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET ,value = "/tweetsPage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagination(@QueryParam("start") int start, @QueryParam("size") int size) throws TwitterException {
        List<TwitterResponseModel> response;
        response = twitterImplement.getTweetsPage(start, size);
        return Response.ok(response).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postTweets")
    public SendResponse sendTweet(@RequestBody MessageRequest request) throws TwitterException {
        logger.info("got into post");
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            logger.error("error happened");
            return new SendResponse("Field is empty",400);
        } else {
            Status status = twitterImplement.sendTweet(post);
            if (status.getText().equals(post)) {
                logger.info("successfully posted");
                return new SendResponse("Tweet posted Successfully",200);
            } else {
                logger.error("internal error occurred");
                return new SendResponse("Request incomplete",500);
            }
        }

    }


}
