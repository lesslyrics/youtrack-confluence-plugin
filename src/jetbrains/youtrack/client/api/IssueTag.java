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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an issue tag.
 */
@Schema(description = "Represents an issue tag.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class IssueTag extends WatchFolder {
  @JsonProperty("issues")
  private List<Issue> issues = null;

  @JsonProperty("color")
  private FieldStyle color = null;

  @JsonProperty("untagOnResolve")
  private Boolean untagOnResolve = null;

  public IssueTag issues(List<Issue> issues) {
    this.issues = issues;
    return this;
  }

  public IssueTag addIssuesItem(Issue issuesItem) {
    if (this.issues == null) {
      this.issues = new ArrayList<Issue>();
    }
    this.issues.add(issuesItem);
    return this;
  }

   /**
   * Get issues
   * @return issues
  **/
  @Schema(description = "")
  public List<Issue> getIssues() {
    return issues;
  }

  public void setIssues(List<Issue> issues) {
    this.issues = issues;
  }

  public IssueTag color(FieldStyle color) {
    this.color = color;
    return this;
  }

   /**
   * Get color
   * @return color
  **/
  @Schema(description = "")
  public FieldStyle getColor() {
    return color;
  }

  public void setColor(FieldStyle color) {
    this.color = color;
  }

  public IssueTag untagOnResolve(Boolean untagOnResolve) {
    this.untagOnResolve = untagOnResolve;
    return this;
  }

   /**
   * Get untagOnResolve
   * @return untagOnResolve
  **/
  @Schema(description = "")
  public Boolean isUntagOnResolve() {
    return untagOnResolve;
  }

  public void setUntagOnResolve(Boolean untagOnResolve) {
    this.untagOnResolve = untagOnResolve;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueTag issueTag = (IssueTag) o;
    return Objects.equals(this.issues, issueTag.issues) &&
        Objects.equals(this.color, issueTag.color) &&
        Objects.equals(this.untagOnResolve, issueTag.untagOnResolve) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issues, color, untagOnResolve, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueTag {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    issues: ").append(toIndentedString(issues)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    untagOnResolve: ").append(toIndentedString(untagOnResolve)).append("\n");
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
