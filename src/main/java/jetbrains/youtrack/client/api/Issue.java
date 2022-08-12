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
import java.util.ArrayList;
import java.util.List;

import static com.atlassian.renderer.v2.components.HtmlEscaper.escapeAll;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

/**
 * Represents an issue in YouTrack.
 */
@Schema(description = "Represents an issue in YouTrack.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class Issue {
  @JsonProperty("idReadable")
  private String idReadable = null;

  @JsonProperty("created")
  private Long created = null;

  @JsonProperty("updated")
  private Long updated = null;

  @JsonProperty("resolved")
  private Long resolved = null;

  @JsonProperty("numberInProject")
  private Long numberInProject = null;

  @JsonProperty("project")
  private Project project = null;

  @JsonProperty("summary")
  private String summary = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("usesMarkdown")
  private Boolean usesMarkdown = null;

  @JsonProperty("wikifiedDescription")
  private String wikifiedDescription = null;

  @JsonProperty("reporter")
  private User reporter = null;

  @JsonProperty("updater")
  private User updater = null;

  @JsonProperty("draftOwner")
  private User draftOwner = null;

  @JsonProperty("isDraft")
  private Boolean isDraft = null;

  @JsonProperty("visibility")
  private Visibility visibility = null;

  @JsonProperty("votes")
  private Integer votes = null;

  @JsonProperty("comments")
  private List<IssueComment> comments = null;

  @JsonProperty("commentsCount")
  private Integer commentsCount = null;

  @JsonProperty("tags")
  private List<IssueTag> tags = null;

  @JsonProperty("links")
  private List<IssueLink> links = null;

  @JsonProperty("externalIssue")
  private ExternalIssue externalIssue = null;

  @JsonProperty("customFields")
  private List<IssueCustomField> customFields = null;

  @JsonProperty("voters")
  private IssueVoters voters = null;

  @JsonProperty("watchers")
  private IssueWatchers watchers = null;

  @JsonProperty("attachments")
  private List<IssueAttachment> attachments = null;

  @JsonProperty("subtasks")
  private IssueLink subtasks = null;

  @JsonProperty("parent")
  private IssueLink parent = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

   /**
   * Get idReadable
   * @return idReadable
  **/
  @Schema(description = "")
  public String getIdReadable() {
    return escapeAll(idReadable, false);
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
   * Get resolved
   * @return resolved
  **/
  @Schema(description = "")
  public Long getResolved() {
    return resolved;
  }

   /**
   * Get numberInProject
   * @return numberInProject
  **/
  @Schema(description = "")
  public Long getNumberInProject() {
    return numberInProject;
  }

  public Issue project(Project project) {
    this.project = project;
    return this;
  }

   /**
   * Get project
   * @return project
  **/
  @Schema(description = "")
  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Issue summary(String summary) {
    this.summary = summary;
    return this;
  }

   /**
   * Get summary
   * @return summary
  **/
  @Schema(description = "")
  public String getSummary() {
    return escapeAll(summary, false);
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Issue description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @Schema(description = "")
  public String getDescription() {
    return escapeAll(description, false);
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Issue usesMarkdown(Boolean usesMarkdown) {
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
   * Get wikifiedDescription
   * @return wikifiedDescription
  **/
  @Schema(description = "")
  public String getWikifiedDescription() {
    return wikifiedDescription;
  }

  public Issue reporter(User reporter) {
    this.reporter = reporter;
    return this;
  }

   /**
   * Get reporter
   * @return reporter
  **/
  @Schema(description = "")
  public User getReporter() {
    return reporter;
  }

  public void setReporter(User reporter) {
    this.reporter = reporter;
  }

  public Issue updater(User updater) {
    this.updater = updater;
    return this;
  }

   /**
   * Get updater
   * @return updater
  **/
  @Schema(description = "")
  public User getUpdater() {
    return updater;
  }

  public void setUpdater(User updater) {
    this.updater = updater;
  }

  public Issue draftOwner(User draftOwner) {
    this.draftOwner = draftOwner;
    return this;
  }

   /**
   * Get draftOwner
   * @return draftOwner
  **/
  @Schema(description = "")
  public User getDraftOwner() {
    return draftOwner;
  }

  public void setDraftOwner(User draftOwner) {
    this.draftOwner = draftOwner;
  }

   /**
   * Get isDraft
   * @return isDraft
  **/
  @Schema(description = "")
  public Boolean isIsDraft() {
    return isDraft;
  }

  public Issue visibility(Visibility visibility) {
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

   /**
   * Get votes
   * @return votes
  **/
  @Schema(description = "")
  public Integer getVotes() {
    return votes;
  }

  public Issue comments(List<IssueComment> comments) {
    this.comments = comments;
    return this;
  }

  public Issue addCommentsItem(IssueComment commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<IssueComment>();
    }
    this.comments.add(commentsItem);
    return this;
  }

   /**
   * Get comments
   * @return comments
  **/
  @Schema(description = "")
  public List<IssueComment> getComments() {
    return comments;
  }

  public void setComments(List<IssueComment> comments) {
    this.comments = comments;
  }

   /**
   * Get commentsCount
   * @return commentsCount
  **/
  @Schema(description = "")
  public Integer getCommentsCount() {
    return commentsCount;
  }

  public Issue tags(List<IssueTag> tags) {
    this.tags = tags;
    return this;
  }

  public Issue addTagsItem(IssueTag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<IssueTag>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @Schema(description = "")
  public List<IssueTag> getTags() {
    return tags;
  }

  public void setTags(List<IssueTag> tags) {
    this.tags = tags;
  }

   /**
   * Get links
   * @return links
  **/
  @Schema(description = "")
  public List<IssueLink> getLinks() {
    return links;
  }

  public Issue externalIssue(ExternalIssue externalIssue) {
    this.externalIssue = externalIssue;
    return this;
  }

   /**
   * Get externalIssue
   * @return externalIssue
  **/
  @Schema(description = "")
  public ExternalIssue getExternalIssue() {
    return externalIssue;
  }

  public void setExternalIssue(ExternalIssue externalIssue) {
    this.externalIssue = externalIssue;
  }

   /**
   * Get customFields
   * @return customFields
  **/
  @Schema(description = "")
  public List<IssueCustomField> getCustomFields() {
    return customFields;
  }

  public Issue voters(IssueVoters voters) {
    this.voters = voters;
    return this;
  }

   /**
   * Get voters
   * @return voters
  **/
  @Schema(description = "")
  public IssueVoters getVoters() {
    return voters;
  }

  public void setVoters(IssueVoters voters) {
    this.voters = voters;
  }

  public Issue watchers(IssueWatchers watchers) {
    this.watchers = watchers;
    return this;
  }

   /**
   * Get watchers
   * @return watchers
  **/
  @Schema(description = "")
  public IssueWatchers getWatchers() {
    return watchers;
  }

  public void setWatchers(IssueWatchers watchers) {
    this.watchers = watchers;
  }

  public Issue attachments(List<IssueAttachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public Issue addAttachmentsItem(IssueAttachment attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<IssueAttachment>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

   /**
   * Get attachments
   * @return attachments
  **/
  @Schema(description = "")
  public List<IssueAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<IssueAttachment> attachments) {
    this.attachments = attachments;
  }

  public Issue subtasks(IssueLink subtasks) {
    this.subtasks = subtasks;
    return this;
  }

   /**
   * Get subtasks
   * @return subtasks
  **/
  @Schema(description = "")
  public IssueLink getSubtasks() {
    return subtasks;
  }

  public void setSubtasks(IssueLink subtasks) {
    this.subtasks = subtasks;
  }

  public Issue parent(IssueLink parent) {
    this.parent = parent;
    return this;
  }

   /**
   * Get parent
   * @return parent
  **/
  @Schema(description = "")
  public IssueLink getParent() {
    return parent;
  }

  public void setParent(IssueLink parent) {
    this.parent = parent;
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
    Issue issue = (Issue) o;
    return Objects.equals(this.idReadable, issue.idReadable) &&
        Objects.equals(this.created, issue.created) &&
        Objects.equals(this.updated, issue.updated) &&
        Objects.equals(this.resolved, issue.resolved) &&
        Objects.equals(this.numberInProject, issue.numberInProject) &&
        Objects.equals(this.project, issue.project) &&
        Objects.equals(this.summary, issue.summary) &&
        Objects.equals(this.description, issue.description) &&
        Objects.equals(this.usesMarkdown, issue.usesMarkdown) &&
        Objects.equals(this.wikifiedDescription, issue.wikifiedDescription) &&
        Objects.equals(this.reporter, issue.reporter) &&
        Objects.equals(this.updater, issue.updater) &&
        Objects.equals(this.draftOwner, issue.draftOwner) &&
        Objects.equals(this.isDraft, issue.isDraft) &&
        Objects.equals(this.visibility, issue.visibility) &&
        Objects.equals(this.votes, issue.votes) &&
        Objects.equals(this.comments, issue.comments) &&
        Objects.equals(this.commentsCount, issue.commentsCount) &&
        Objects.equals(this.tags, issue.tags) &&
        Objects.equals(this.links, issue.links) &&
        Objects.equals(this.externalIssue, issue.externalIssue) &&
        Objects.equals(this.customFields, issue.customFields) &&
        Objects.equals(this.voters, issue.voters) &&
        Objects.equals(this.watchers, issue.watchers) &&
        Objects.equals(this.attachments, issue.attachments) &&
        Objects.equals(this.subtasks, issue.subtasks) &&
        Objects.equals(this.parent, issue.parent) &&
        Objects.equals(this.id, issue.id) &&
        Objects.equals(this.$type, issue.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idReadable, created, updated, resolved, numberInProject, project, summary, description, usesMarkdown, wikifiedDescription, reporter, updater, draftOwner, isDraft, visibility, votes, comments, commentsCount, tags, links, externalIssue, customFields, voters, watchers, attachments, subtasks, parent, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Issue {\n");
    
    sb.append("    idReadable: ").append(toIndentedString(idReadable)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    resolved: ").append(toIndentedString(resolved)).append("\n");
    sb.append("    numberInProject: ").append(toIndentedString(numberInProject)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    summary: ").append(toIndentedString(summary)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    usesMarkdown: ").append(toIndentedString(usesMarkdown)).append("\n");
    sb.append("    wikifiedDescription: ").append(toIndentedString(wikifiedDescription)).append("\n");
    sb.append("    reporter: ").append(toIndentedString(reporter)).append("\n");
    sb.append("    updater: ").append(toIndentedString(updater)).append("\n");
    sb.append("    draftOwner: ").append(toIndentedString(draftOwner)).append("\n");
    sb.append("    isDraft: ").append(toIndentedString(isDraft)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
    sb.append("    votes: ").append(toIndentedString(votes)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    commentsCount: ").append(toIndentedString(commentsCount)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    externalIssue: ").append(toIndentedString(externalIssue)).append("\n");
    sb.append("    customFields: ").append(toIndentedString(customFields)).append("\n");
    sb.append("    voters: ").append(toIndentedString(voters)).append("\n");
    sb.append("    watchers: ").append(toIndentedString(watchers)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    subtasks: ").append(toIndentedString(subtasks)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
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
