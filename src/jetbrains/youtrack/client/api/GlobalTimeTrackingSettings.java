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

import java.util.List;

/**
 * Represents time tracking settings of your server.
 */
@Schema(description = "Represents time tracking settings of your server.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class GlobalTimeTrackingSettings {
  @JsonProperty("workItemTypes")
  private List<WorkItemType> workItemTypes = null;

  @JsonProperty("workTimeSettings")
  private WorkTimeSettings workTimeSettings = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

   /**
   * Get workItemTypes
   * @return workItemTypes
  **/
  @Schema(description = "")
  public List<WorkItemType> getWorkItemTypes() {
    return workItemTypes;
  }

  public GlobalTimeTrackingSettings workTimeSettings(WorkTimeSettings workTimeSettings) {
    this.workTimeSettings = workTimeSettings;
    return this;
  }

   /**
   * Get workTimeSettings
   * @return workTimeSettings
  **/
  @Schema(description = "")
  public WorkTimeSettings getWorkTimeSettings() {
    return workTimeSettings;
  }

  public void setWorkTimeSettings(WorkTimeSettings workTimeSettings) {
    this.workTimeSettings = workTimeSettings;
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
    GlobalTimeTrackingSettings globalTimeTrackingSettings = (GlobalTimeTrackingSettings) o;
    return Objects.equals(this.workItemTypes, globalTimeTrackingSettings.workItemTypes) &&
        Objects.equals(this.workTimeSettings, globalTimeTrackingSettings.workTimeSettings) &&
        Objects.equals(this.id, globalTimeTrackingSettings.id) &&
        Objects.equals(this.$type, globalTimeTrackingSettings.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(workItemTypes, workTimeSettings, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GlobalTimeTrackingSettings {\n");
    
    sb.append("    workItemTypes: ").append(toIndentedString(workItemTypes)).append("\n");
    sb.append("    workTimeSettings: ").append(toIndentedString(workTimeSettings)).append("\n");
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
