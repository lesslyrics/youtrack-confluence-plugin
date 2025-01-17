/*
 * YouTrack REST API
 * YouTrack issue tracking and project management system
 *
 * OpenAPI spec version: 2099.99
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package jetbrains.youtrack.client.api;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a vote for duplicates of the issue.
 */
@Schema(description = "Represents a vote for duplicates of the issue.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class DuplicateVote {
  @JsonProperty("issue")
  private Issue issue = null;

  @JsonProperty("user")
  private User user = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public DuplicateVote issue(Issue issue) {
    this.issue = issue;
    return this;
  }

   /**
   * Get issue
   * @return issue
  **/
  @Schema(description = "")
  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public DuplicateVote user(User user) {
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @Schema(description = "")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

   /**
   * Get id
   * @return id
  **/
  @Schema(description = "")
  public String getId() {
    return id;
  }

   /**
   * Get $type
   * @return $type
  **/
  @Schema(description = "")
  public String get$Type() {
    return $type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DuplicateVote duplicateVote = (DuplicateVote) o;
    return Objects.equals(this.issue, duplicateVote.issue) &&
        Objects.equals(this.user, duplicateVote.user) &&
        Objects.equals(this.id, duplicateVote.id) &&
        Objects.equals(this.$type, duplicateVote.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issue, user, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DuplicateVote {\n");
    
    sb.append("    issue: ").append(toIndentedString(issue)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    $type: ").append(toIndentedString($type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
