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

import static com.atlassian.renderer.v2.components.HtmlEscaper.escapeAll;

/**
 * Represents a YouTrack project.
 */
@Schema(description = "Represents a YouTrack project.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class Project extends IssueFolder {
  @JsonProperty("startingNumber")
  private Long startingNumber = null;

  @JsonProperty("shortName")
  private String shortName = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("leader")
  private User leader = null;

  @JsonProperty("createdBy")
  private User createdBy = null;

  @JsonProperty("issues")
  private List<Issue> issues = null;

  @JsonProperty("customFields")
  private Object customFields = null;

  @JsonProperty("archived")
  private Boolean archived = null;

  @JsonProperty("fromEmail")
  private String fromEmail = null;

  @JsonProperty("replyToEmail")
  private String replyToEmail = null;

  @JsonProperty("template")
  private Boolean template = null;

  @JsonProperty("iconUrl")
  private String iconUrl = null;

  @JsonProperty("team")
  private UserGroup team = null;

  public Project startingNumber(Long startingNumber) {
    this.startingNumber = startingNumber;
    return this;
  }

   /**
   * Get startingNumber
   * @return startingNumber
  **/
  @Schema(description = "")
  public Long getStartingNumber() {
    return startingNumber;
  }

  public void setStartingNumber(Long startingNumber) {
    this.startingNumber = startingNumber;
  }

  public Project shortName(String shortName) {
    this.shortName = shortName;
    return this;
  }

   /**
   * Get shortName
   * @return shortName
  **/
  @Schema(description = "")
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Project description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @Schema(description = "")
  public String getDescription() {
    return escapeAll(description, true);
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Project leader(User leader) {
    this.leader = leader;
    return this;
  }

   /**
   * Get leader
   * @return leader
  **/
  @Schema(description = "")
  public User getLeader() {
    return leader;
  }

  public void setLeader(User leader) {
    this.leader = leader;
  }

  public Project createdBy(User createdBy) {
    this.createdBy = createdBy;
    return this;
  }

   /**
   * Get createdBy
   * @return createdBy
  **/
  @Schema(description = "")
  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public Project issues(List<Issue> issues) {
    this.issues = issues;
    return this;
  }

  public Project addIssuesItem(Issue issuesItem) {
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

   /**
   * Get customFields
   * @return customFields
  **/
  @Schema(description = "")
  public Object getCustomFields() {
    return customFields;
  }

  public Project archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

   /**
   * Get archived
   * @return archived
  **/
  @Schema(description = "")
  public Boolean isArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public Project fromEmail(String fromEmail) {
    this.fromEmail = fromEmail;
    return this;
  }

   /**
   * Get fromEmail
   * @return fromEmail
  **/
  @Schema(description = "")
  public String getFromEmail() {
    return escapeAll(fromEmail, true);
  }

  public void setFromEmail(String fromEmail) {
    this.fromEmail = fromEmail;
  }

  public Project replyToEmail(String replyToEmail) {
    this.replyToEmail = replyToEmail;
    return this;
  }

   /**
   * Get replyToEmail
   * @return replyToEmail
  **/
  @Schema(description = "")
  public String getReplyToEmail() {
    return escapeAll(replyToEmail,true);
  }

  public void setReplyToEmail(String replyToEmail) {
    this.replyToEmail = replyToEmail;
  }

  public Project template(Boolean template) {
    this.template = template;
    return this;
  }

   /**
   * Get template
   * @return template
  **/
  @Schema(description = "")
  public Boolean isTemplate() {
    return template;
  }

  public void setTemplate(Boolean template) {
    this.template = template;
  }

   /**
   * Get iconUrl
   * @return iconUrl
  **/
  @Schema(description = "")
  public String getIconUrl() {
    return iconUrl;
  }

  public Project team(UserGroup team) {
    this.team = team;
    return this;
  }

   /**
   * Get team
   * @return team
  **/
  @Schema(description = "")
  public UserGroup getTeam() {
    return team;
  }

  public void setTeam(UserGroup team) {
    this.team = team;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return Objects.equals(this.startingNumber, project.startingNumber) &&
        Objects.equals(this.shortName, project.shortName) &&
        Objects.equals(this.description, project.description) &&
        Objects.equals(this.leader, project.leader) &&
        Objects.equals(this.createdBy, project.createdBy) &&
        Objects.equals(this.issues, project.issues) &&
        Objects.equals(this.customFields, project.customFields) &&
        Objects.equals(this.archived, project.archived) &&
        Objects.equals(this.fromEmail, project.fromEmail) &&
        Objects.equals(this.replyToEmail, project.replyToEmail) &&
        Objects.equals(this.template, project.template) &&
        Objects.equals(this.iconUrl, project.iconUrl) &&
        Objects.equals(this.team, project.team) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startingNumber, shortName, description, leader, createdBy, issues, customFields, archived, fromEmail, replyToEmail, template, iconUrl, team, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Project {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    startingNumber: ").append(toIndentedString(startingNumber)).append("\n");
    sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    leader: ").append(toIndentedString(leader)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    issues: ").append(toIndentedString(issues)).append("\n");
    sb.append("    customFields: ").append(toIndentedString(customFields)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    fromEmail: ").append(toIndentedString(fromEmail)).append("\n");
    sb.append("    replyToEmail: ").append(toIndentedString(replyToEmail)).append("\n");
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
    sb.append("    iconUrl: ").append(toIndentedString(iconUrl)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
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
