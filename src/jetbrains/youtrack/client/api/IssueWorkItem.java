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
 * Represents a work item in an issue.
 */
@Schema(description = "Represents a work item in an issue.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class IssueWorkItem {
  @JsonProperty("author")
  private User author = null;

  @JsonProperty("creator")
  private User creator = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("textPreview")
  private String textPreview = null;

  @JsonProperty("type")
  private WorkItemType type = null;

  @JsonProperty("created")
  private Long created = null;

  @JsonProperty("updated")
  private Long updated = null;

  @JsonProperty("duration")
  private DurationValue duration = null;

  @JsonProperty("date")
  private Long date = null;

  @JsonProperty("issue")
  private Issue issue = null;

  @JsonProperty("usesMarkdown")
  private Boolean usesMarkdown = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public IssueWorkItem author(User author) {
    this.author = author;
    return this;
  }

   /**
   * Get author
   * @return author
  **/
  @Schema(description = "")
  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public IssueWorkItem creator(User creator) {
    this.creator = creator;
    return this;
  }

   /**
   * Get creator
   * @return creator
  **/
  @Schema(description = "")
  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public IssueWorkItem text(String text) {
    this.text = text;
    return this;
  }

   /**
   * Get text
   * @return text
  **/
  @Schema(description = "")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

   /**
   * Get textPreview
   * @return textPreview
  **/
  @Schema(description = "")
  public String getTextPreview() {
    return textPreview;
  }

  public IssueWorkItem type(WorkItemType type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @Schema(description = "")
  public WorkItemType getType() {
    return type;
  }

  public void setType(WorkItemType type) {
    this.type = type;
  }

  public IssueWorkItem created(Long created) {
    this.created = created;
    return this;
  }

   /**
   * Get created
   * @return created
  **/
  @Schema(description = "")
  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public IssueWorkItem updated(Long updated) {
    this.updated = updated;
    return this;
  }

   /**
   * Get updated
   * @return updated
  **/
  @Schema(description = "")
  public Long getUpdated() {
    return updated;
  }

  public void setUpdated(Long updated) {
    this.updated = updated;
  }

  public IssueWorkItem duration(DurationValue duration) {
    this.duration = duration;
    return this;
  }

   /**
   * Get duration
   * @return duration
  **/
  @Schema(description = "")
  public DurationValue getDuration() {
    return duration;
  }

  public void setDuration(DurationValue duration) {
    this.duration = duration;
  }

  public IssueWorkItem date(Long date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @Schema(description = "")
  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public IssueWorkItem issue(Issue issue) {
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

  public IssueWorkItem usesMarkdown(Boolean usesMarkdown) {
    this.usesMarkdown = usesMarkdown;
    return this;
  }

   /**
   * Get usesMarkdown
   * @return usesMarkdown
  **/
  @Schema(description = "")
  public Boolean isUsesMarkdown() {
    return usesMarkdown;
  }

  public void setUsesMarkdown(Boolean usesMarkdown) {
    this.usesMarkdown = usesMarkdown;
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
    IssueWorkItem issueWorkItem = (IssueWorkItem) o;
    return Objects.equals(this.author, issueWorkItem.author) &&
        Objects.equals(this.creator, issueWorkItem.creator) &&
        Objects.equals(this.text, issueWorkItem.text) &&
        Objects.equals(this.textPreview, issueWorkItem.textPreview) &&
        Objects.equals(this.type, issueWorkItem.type) &&
        Objects.equals(this.created, issueWorkItem.created) &&
        Objects.equals(this.updated, issueWorkItem.updated) &&
        Objects.equals(this.duration, issueWorkItem.duration) &&
        Objects.equals(this.date, issueWorkItem.date) &&
        Objects.equals(this.issue, issueWorkItem.issue) &&
        Objects.equals(this.usesMarkdown, issueWorkItem.usesMarkdown) &&
        Objects.equals(this.id, issueWorkItem.id) &&
        Objects.equals(this.$type, issueWorkItem.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, creator, text, textPreview, type, created, updated, duration, date, issue, usesMarkdown, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueWorkItem {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    textPreview: ").append(toIndentedString(textPreview)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    issue: ").append(toIndentedString(issue)).append("\n");
    sb.append("    usesMarkdown: ").append(toIndentedString(usesMarkdown)).append("\n");
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
