package com.socialmediaraiser.core.twitter.helpers.dto.tweet;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.socialmediaraiser.core.twitter.IUser;
import com.socialmediaraiser.core.twitter.helpers.JsonHelper;
import com.socialmediaraiser.core.twitter.helpers.dto.user.UserDTOv2;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version labs
 */
@Data
public class TweetDTOv2 implements ITweet {

    private TweetData[] data;
    private Includes includes;

    @Data
    private static class TweetData{
        private String id;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonAlias({"text", "full_text"})
        private String text;
        @JsonProperty("author_id")
        private String authorId;
        @JsonProperty("in_reply_to_user_id")
        private String inReplyToUserId;
        @JsonProperty("referenced_tweets")
        private List<ReferencedTweetDTO> referencedTweets;
        private JsonNode entities;
        private TwitterStatsDTO stats;
        @JsonProperty("possibly_sensitive")
        private boolean possiblySensitive;
        private String lang;
        private String source;
        private String format;
        private JsonNode attachments;
        private JsonNode geo;
    }

    @Data
    private static class Includes{
        private List<UserDTOv2> users;
    }



    @Override
    public String getInReplyToStatusId(){
        if(this.data[0].getReferencedTweets().size()>0){
            return this.data[0].getReferencedTweets().get(0).getId();
        }
        return null;
    }

    @Override
    public String getLang() {
        return this.data[0].getLang();
    }

    @Override
    public String getId() {
        return this.data[0].getId();
    }

    @Override
    public String getText() {
        return this.data[0].getText();
    }

    @Override
    public int getRetweetCount() {
        return this.data[0].getStats().getRetweetCount();
    }

    @Override
    public int getLikeCount() {
        return  this.data[0].getStats().getLikeCount();
    }

    @Override
    public int getReplyCount() {
        return this.data[0].getStats().getReplyCount();
    }

    @Override
    public String getInReplyToUserId(){
        return this.data[0].getInReplyToUserId();
    }

    @Override
    public IUser getUser() {
        return this.includes.getUsers().get(0);
    }

    @Data
    private static class ReferencedTweetDTO {
        private String type;
        private String id;
    }

    @Data
    private static class TwitterStatsDTO {
        @JsonProperty("retweet_count")
        private int retweetCount;
        @JsonProperty("reply_count")
        private int replyCount;
        @JsonProperty("like_count")
        private int likeCount;
        @JsonProperty("quote_count")
        private int quoteCount;
    }

    public Date getCreatedAt(){
        return JsonHelper.getDateFromTwitterString(this.data[0].getCreatedAt());
    }
}