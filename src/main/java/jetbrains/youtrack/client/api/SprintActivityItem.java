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
 * Represents a change in the issue when it was added to or removed from an agile board sprint.
 */
@Schema(description = "Represents a change in the issue when it was added to or removed from an agile board sprint.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class SprintActivityItem extends MultiValueActivityItem {
  @JsonProperty("target")
  private Issue target = null;

  @JsonProperty("removed")
  private List<Sprint> removed = null;

  @JsonProperty("added")
  private List<Sprint> added = null;

  public SprintActivityItem target(Issue target) {
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @Schema(description = "")
  public Issue getTarget() {
    return target;
  }

  public void setTarget(Issue target) {
    this.target = target;
  }

   /**
   * Get removed
   * @return removed
  **/
  @Schema(description = "")
  public List<Sprint> getRemoved() {
    return removed;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public List<Sprint> getAdded() {
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
    SprintActivityItem sprintActivityItem = (SprintActivityItem) o;
    return Objects.equals(this.target, sprintActivityItem.target) &&
        Objects.equals(this.removed, sprintActivityItem.removed) &&
        Objects.equals(this.added, sprintActivityItem.added) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, removed, added, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SprintActivityItem {\n");
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
