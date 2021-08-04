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
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Represents a change in the &#x60;attachments&#x60; attribute of an IssueComment.
 */
@Schema(description = "Represents a change in the `attachments` attribute of an IssueComment.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class CommentAttachmentsActivityItem extends MultiValueActivityItem {
  @JsonProperty("target")
  private IssueComment target = null;

  @JsonProperty("removed")
  private List<IssueAttachment> removed = null;

  @JsonProperty("added")
  private List<IssueAttachment> added = null;

  public CommentAttachmentsActivityItem target(IssueComment target) {
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @Schema(description = "")
  public IssueComment getTarget() {
    return target;
  }

  public void setTarget(IssueComment target) {
    this.target = target;
  }

   /**
   * Get removed
   * @return removed
  **/
  @Schema(description = "")
  public List<IssueAttachment> getRemoved() {
    return removed;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public List<IssueAttachment> getAdded() {
    return added;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentAttachmentsActivityItem commentAttachmentsActivityItem = (CommentAttachmentsActivityItem) o;
    return Objects.equals(this.target, commentAttachmentsActivityItem.target) &&
        Objects.equals(this.removed, commentAttachmentsActivityItem.removed) &&
        Objects.equals(this.added, commentAttachmentsActivityItem.added) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, removed, added, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentAttachmentsActivityItem {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    removed: ").append(toIndentedString(removed)).append("\n");
    sb.append("    added: ").append(toIndentedString(added)).append("\n");
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
