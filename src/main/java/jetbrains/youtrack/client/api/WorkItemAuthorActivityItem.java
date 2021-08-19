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

/**
 * Represents a change in the &#x60;author&#x60; attribute of a work item.
 */
@Schema(description = "Represents a change in the `author` attribute of a work item.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class WorkItemAuthorActivityItem extends SingleValueActivityItem {
  @JsonProperty("target")
  private IssueWorkItem target = null;

  @JsonProperty("removed")
  private User removed = null;

  @JsonProperty("added")
  private User added = null;

  public WorkItemAuthorActivityItem target(IssueWorkItem target) {
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @Schema(description = "")
  public IssueWorkItem getTarget() {
    return target;
  }

  public void setTarget(IssueWorkItem target) {
    this.target = target;
  }

  public WorkItemAuthorActivityItem removed(User removed) {
    this.removed = removed;
    return this;
  }

   /**
   * Get removed
   * @return removed
  **/
  @Schema(description = "")
  public User getRemoved() {
    return removed;
  }

  public void setRemoved(User removed) {
    this.removed = removed;
  }

  public WorkItemAuthorActivityItem added(User added) {
    this.added = added;
    return this;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public User getAdded() {
    return added;
  }

  public void setAdded(User added) {
    this.added = added;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkItemAuthorActivityItem workItemAuthorActivityItem = (WorkItemAuthorActivityItem) o;
    return Objects.equals(this.target, workItemAuthorActivityItem.target) &&
        Objects.equals(this.removed, workItemAuthorActivityItem.removed) &&
        Objects.equals(this.added, workItemAuthorActivityItem.added) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, removed, added, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkItemAuthorActivityItem {\n");
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