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
 * Represents an event when a user resolves an issue.
 */
@Schema(description = "Represents an event when a user resolves an issue.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class IssueResolvedActivityItem extends SimpleValueActivityItem {
  @JsonProperty("removed")
  private Long removed = null;

  @JsonProperty("added")
  private Long added = null;

   /**
   * Get removed
   * @return removed
  **/
  @Schema(description = "")
  public Long getRemoved() {
    return removed;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public Long getAdded() {
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
    IssueResolvedActivityItem issueResolvedActivityItem = (IssueResolvedActivityItem) o;
    return Objects.equals(this.removed, issueResolvedActivityItem.removed) &&
        Objects.equals(this.added, issueResolvedActivityItem.added) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(removed, added, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueResolvedActivityItem {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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