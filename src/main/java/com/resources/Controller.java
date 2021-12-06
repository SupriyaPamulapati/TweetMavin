/**
 * This package contains resources classes.
 */
package com.resources;

import com.service.TwitterImplement;
import model.SendResponse;
import model.TwitterResponseModel;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.BadRequestException;
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

    /**
     * fetchTweets method used to fetch tweets which is returned from TwitterImplement.myTimeline().
     *
     * @return used to return tweets as response.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getTweets")
    public List<TwitterResponseModel> fetchTweets() {
        return twitterImplement.getTweets();
    }

    /**
     * filterTweets method used to fetch filtered tweets from TwitterImplement.getFilteredTweets().
     *
     * @param searchKey of matching string
     * @return used to return filtered tweets as response.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/filteredTweets")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TwitterResponseModel> filteredTweets(@QueryParam("searchKey") String searchKey) throws TwitterException {
        List<TwitterResponseModel> response = twitterImplement.getFilteredTweets(searchKey);
        return response;
    }

    /**
     * pagination method used to fetch filtered tweets from TwitterImplement.getFilteredTweets().
     *
     * @param start size of page
     * @return used to return filtered tweets as response.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tweetsPage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pagination(@QueryParam("start") int start, @QueryParam("size") int size) throws TwitterException {
        List<TwitterResponseModel> response;
        response = twitterImplement.getTweetsPage(start, size);
        return Response.ok(response).build();
    }

    /**
     * postTweet method used to give response on post tweet to user timeline.
     * <p>
     * request used to get tweets which has to be posted.
     *
     * @param request used for requesting Configuration
     * @return used to return response \t based on successful or unsuccessful post of tweet.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/postTweets")
    public ResponseEntity<SendResponse> sendTweet(@RequestBody MessageRequest request) {
        logger.info("got into post");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        String post = request.getMsg();
        if (StringUtil.isEmpty(post)) {
            logger.error("error happened");
            return new ResponseEntity<SendResponse>(
                    new SendResponse("Please enter a Valid Tweet"), headers, HttpStatus.BAD_REQUEST_400);
        } else {
            try {
                Status status = twitterImplement.sendTweet(post);
                if (status.getText().equals(post)) {
                    logger.info("successfully posted");
                    return new ResponseEntity<SendResponse>(
                            new SendResponse("Tweet posted Successfully"), headers, HttpStatus.OK_200);
                } else {
                    logger.error("internal error occurred");
                    return new ResponseEntity<SendResponse>(
                            new SendResponse("Request tweet is not correct"), headers, HttpStatus.INTERNAL_SERVER_ERROR_500);
                }
            } catch (BadRequestException e) {
                logger.error("Tweet Was Not Done Invalid Request", e);
                return new ResponseEntity<SendResponse>(
                        new SendResponse("Please enter a Valid Tweet"), headers, HttpStatus.BAD_REQUEST_400);
            } catch (Exception e) {
                logger.error("Tweet Was Not Sent");
                return new ResponseEntity<SendResponse>(
                        new SendResponse("Request tweet is not correct"), headers, HttpStatus.INTERNAL_SERVER_ERROR_500);
            }
        }

    }
}
