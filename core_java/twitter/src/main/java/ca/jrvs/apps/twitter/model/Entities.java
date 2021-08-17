package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hashtags",
    "user_mentions"
})
public class Entities {

  public Hashtag[] getHashtags() {
    return hashtags;
  }

  public void setHashtags(Hashtag[] hashtags) {
    this.hashtags = hashtags;
  }

  public UserMention[] getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(UserMention[] userMentions) {
    this.userMentions = userMentions;
  }

  private Hashtag[] hashtags;
  @JsonProperty("user_mentions")
  private UserMention[] userMentions;
}
