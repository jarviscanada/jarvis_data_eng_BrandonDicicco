package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entities {
  private Hashtag[] hashtags;
  @JsonProperty("user_mentions")
  private UserMention[] userMentions;
}
