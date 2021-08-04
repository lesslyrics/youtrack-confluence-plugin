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
 * UserProfiles
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class UserProfiles {
  @JsonProperty("general")
  private GeneralUserProfile general = null;

  @JsonProperty("notifications")
  private NotificationsUserProfile notifications = null;

  @JsonProperty("timetracking")
  private TimeTrackingUserProfile timetracking = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public UserProfiles general(GeneralUserProfile general) {
    this.general = general;
    return this;
  }

   /**
   * Get general
   * @return general
  **/
  @Schema(description = "")
  public GeneralUserProfile getGeneral() {
    return general;
  }

  public void setGeneral(GeneralUserProfile general) {
    this.general = general;
  }

  public UserProfiles notifications(NotificationsUserProfile notifications) {
    this.notifications = notifications;
    return this;
  }

   /**
   * Get notifications
   * @return notifications
  **/
  @Schema(description = "")
  public NotificationsUserProfile getNotifications() {
    return notifications;
  }

  public void setNotifications(NotificationsUserProfile notifications) {
    this.notifications = notifications;
  }

  public UserProfiles timetracking(TimeTrackingUserProfile timetracking) {
    this.timetracking = timetracking;
    return this;
  }

   /**
   * Get timetracking
   * @return timetracking
  **/
  @Schema(description = "")
  public TimeTrackingUserProfile getTimetracking() {
    return timetracking;
  }

  public void setTimetracking(TimeTrackingUserProfile timetracking) {
    this.timetracking = timetracking;
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
    UserProfiles userProfiles = (UserProfiles) o;
    return Objects.equals(this.general, userProfiles.general) &&
        Objects.equals(this.notifications, userProfiles.notifications) &&
        Objects.equals(this.timetracking, userProfiles.timetracking) &&
        Objects.equals(this.id, userProfiles.id) &&
        Objects.equals(this.$type, userProfiles.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(general, notifications, timetracking, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserProfiles {\n");
    
    sb.append("    general: ").append(toIndentedString(general)).append("\n");
    sb.append("    notifications: ").append(toIndentedString(notifications)).append("\n");
    sb.append("    timetracking: ").append(toIndentedString(timetracking)).append("\n");
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