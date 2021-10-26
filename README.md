# TweetMavin
Pass in an argument representing a post to be published on Twitter and Retrieve latest home timeline from Twitter.

What it does:

In this project we will be Passing an argument representing a post to be published on Twitter and Retrieve latest home timeline from Twitter from intellij IDE. 
separate classes that can be run in a single code base. You will need to configure twitter4j.
Pass in an argument representing a post to be published on Twitter Retrieve latest home timeline from Twitter.
Ensure the twitter account follows someone so tweets show up. If you tweet a lot, that works too.

Installation Instructions:

Download Java 8 JDK and make sure it's Java 8. Create a Twitter development application. Set up java 8 as your default java environment.
You should be able to run java -version on the command line and get the correct version. Download and set up your Intellij as your development environment. 
Use the 'community edition'. 
The following libraries are included in the code: 
twitter4j-core - core component : support REST and 
Search API twitter4j-examples - examples 
twitter4j-async - Async API support : depending on twitter4j-core 
twitter4j-stream - Streaming API support : depending on twitter4j-core and twitter4j-async

Maven - https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html 
Download, install and configure dependency management tool in development environment. 
Ensure the framework contains all dependencies for the application.
No 3rd party libraries should be manually downloaded and included.

Added dropwizard as a dependency.
Create configuration and any necessary files to configure dropwizard application to start up.

Add the following REST endpoints
Post tweet - Create a POST route. This route should take a single post parameter 'message' which will represent the message of the tweet. When called properly, this route will post the message to the Twitter account.
Get timeline - Create a GET route. This route will retrieve a list of latest tweets from the home timeline.


Created a class and configure twitter4j. Authorization for twitter: Add the following keys to the code from the Twitter developer account: Consumerkey ConsumerSecretKey AccessTokenKey AccessSecretKey

