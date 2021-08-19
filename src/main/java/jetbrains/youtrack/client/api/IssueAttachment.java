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
 * Represents a file that is attached to an issue or a comment.
 */
@Schema(description = "Represents a file that is attached to an issue or a comment.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class IssueAttachment {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("author")
  private User author = null;

  @JsonProperty("created")
  private Long created = null;

  @JsonProperty("updated")
  private Long updated = null;

  @JsonProperty("size")
  private Long size = null;

  @JsonProperty("extension")
  private String extension = null;

  @JsonProperty("charset")
  private String charset = null;

  @JsonProperty("mimeType")
  private String mimeType = null;

  @JsonProperty("metaData")
  private String metaData = null;

  @JsonProperty("draft")
  private Boolean draft = null;

  @JsonProperty("removed")
  private Boolean removed = null;

  @JsonProperty("base64Content")
  private String base64Content = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("visibility")
  private Visibility visibility = null;

  @JsonProperty("issue")
  private Issue issue = null;

  @JsonProperty("comment")
  private IssueComment comment = null;

  @JsonProperty("thumbnailURL")
  private String thumbnailURL = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public IssueAttachment name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @Schema(description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IssueAttachment author(User author) {
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

   /**
   * Get created
   * @return created
  **/
  @Schema(description = "")
  public Long getCreated() {
    return created;
  }

   /**
   * Get updated
   * @return updated
  **/
  @Schema(description = "")
  public Long getUpdated() {
    return updated;
  }

   /**
   * Get size
   * @return size
  **/
  @Schema(description = "")
  public Long getSize() {
    return size;
  }

   /**
   * Get extension
   * @return extension
  **/
  @Schema(description = "")
  public String getExtension() {
    return extension;
  }

   /**
   * Get charset
   * @return charset
  **/
  @Schema(description = "")
  public String getCharset() {
    return charset;
  }

   /**
   * Get mimeType
   * @return mimeType
  **/
  @Schema(description = "")
  public String getMimeType() {
    return mimeType;
  }

   /**
   * Get metaData
   * @return metaData
  **/
  @Schema(description = "")
  public String getMetaData() {
    return metaData;
  }

   /**
   * Get draft
   * @return draft
  **/
  @Schema(description = "")
  public Boolean isDraft() {
    return draft;
  }

   /**
   * Get removed
   * @return removed
  **/
  @Schema(description = "")
  public Boolean isRemoved() {
    return removed;
  }

  public IssueAttachment base64Content(String base64Content) {
    this.base64Content = base64Content;
    return this;
  }

   /**
   * Get base64Content
   * @return base64Content
  **/
  @Schema(description = "")
  public String getBase64Content() {
    return base64Content;
  }

  public void setBase64Content(String base64Content) {
    this.base64Content = base64Content;
  }

   /**
   * Get url
   * @return url
  **/
  @Schema(description = "")
  public String getUrl() {
    return url;
  }

  public IssueAttachment visibility(Visibility visibility) {
    this.visibility = visibility;
    return this;
  }

   /**
   * Get visibility
   * @return visibility
  **/
  @Schema(description = "")
  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public IssueAttachment issue(Issue issue) {
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

  public IssueAttachment comment(IssueComment comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @Schema(description = "")
  public IssueComment getComment() {
    return comment;
  }

  public void setComment(IssueComment comment) {
    this.comment = comment;
  }

   /**
   * Get thumbnailURL
   * @return thumbnailURL
  **/
  @Schema(description = "")
  public String getThumbnailURL() {
    return thumbnailURL;
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
    IssueAttachment issueAttachment = (IssueAttachment) o;
    return Objects.equals(this.name, issueAttachment.name) &&
        Objects.equals(this.author, issueAttachment.author) &&
        Objects.equals(this.created, issueAttachment.created) &&
        Objects.equals(this.updated, issueAttachment.updated) &&
        Objects.equals(this.size, issueAttachment.size) &&
        Objects.equals(this.extension, issueAttachment.extension) &&
        Objects.equals(this.charset, issueAttachment.charset) &&
        Objects.equals(this.mimeType, issueAttachment.mimeType) &&
        Objects.equals(this.metaData, issueAttachment.metaData) &&
        Objects.equals(this.draft, issueAttachment.draft) &&
        Objects.equals(this.removed, issueAttachment.removed) &&
        Objects.equals(this.base64Content, issueAttachment.base64Content) &&
        Objects.equals(this.url, issueAttachment.url) &&
        Objects.equals(this.visibility, issueAttachment.visibility) &&
        Objects.equals(this.issue, issueAttachment.issue) &&
        Objects.equals(this.comment, issueAttachment.comment) &&
        Objects.equals(this.thumbnailURL, issueAttachment.thumbnailURL) &&
        Objects.equals(this.id, issueAttachment.id) &&
        Objects.equals(this.$type, issueAttachment.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, author, created, updated, size, extension, charset, mimeType, metaData, draft, removed, base64Content, url, visibility, issue, comment, thumbnailURL, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueAttachment {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
    sb.append("    charset: ").append(toIndentedString(charset)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
    sb.append("    draft: ").append(toIndentedString(draft)).append("\n");
    sb.append("    removed: ").append(toIndentedString(removed)).append("\n");
    sb.append("    base64Content: ").append(toIndentedString(base64Content)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
    sb.append("    issue: ").append(toIndentedString(issue)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    thumbnailURL: ").append(toIndentedString(thumbnailURL)).append("\n");
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