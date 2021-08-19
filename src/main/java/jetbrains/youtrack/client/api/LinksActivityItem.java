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
 * Represents a change in the &#x60;issues&#x60; attribute of an IssueLink entity. That is, each issue has the &#x60;links&#x60; attribute that contains an array of IssueLink entities. Each of them represents a link between the target issue and a collection of issues. For example, a set of issues to which the target one is linked as \&quot;Duplicated by\&quot;. The change in the list of these issues is represented by &#x60;LinksActivityItem&#x60; entity.
 */
@Schema(description = "Represents a change in the `issues` attribute of an IssueLink entity. That is, each issue has the `links` attribute that contains an array of IssueLink entities. Each of them represents a link between the target issue and a collection of issues. For example, a set of issues to which the target one is linked as \"Duplicated by\". The change in the list of these issues is represented by `LinksActivityItem` entity.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class LinksActivityItem extends MultiValueActivityItem {
  @JsonProperty("target")
  private Issue target = null;

  @JsonProperty("removed")
  private List<Issue> removed = null;

  @JsonProperty("added")
  private List<Issue> added = null;

  public LinksActivityItem target(Issue target) {
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
  public List<Issue> getRemoved() {
    return removed;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public List<Issue> getAdded() {
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
    LinksActivityItem linksActivityItem = (LinksActivityItem) o;
    return Objects.equals(this.target, linksActivityItem.target) &&
        Objects.equals(this.removed, linksActivityItem.removed) &&
        Objects.equals(this.added, linksActivityItem.added) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, removed, added, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LinksActivityItem {\n");
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