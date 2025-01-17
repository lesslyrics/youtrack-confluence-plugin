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
/**
 * Work schedule settings.
 */
@Schema(description = "Work schedule settings.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class WorkTimeSettings {
  @JsonProperty("minutesADay")
  private Integer minutesADay = null;

  @JsonProperty("workDays")
  private List<Integer> workDays = null;

  @JsonProperty("firstDayOfWeek")
  private Integer firstDayOfWeek = null;

  @JsonProperty("daysAWeek")
  private Integer daysAWeek = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public WorkTimeSettings minutesADay(Integer minutesADay) {
    this.minutesADay = minutesADay;
    return this;
  }

   /**
   * Get minutesADay
   * @return minutesADay
  **/
  @Schema(description = "")
  public Integer getMinutesADay() {
    return minutesADay;
  }

  public void setMinutesADay(Integer minutesADay) {
    this.minutesADay = minutesADay;
  }

  public WorkTimeSettings workDays(List<Integer> workDays) {
    this.workDays = workDays;
    return this;
  }

  public WorkTimeSettings addWorkDaysItem(Integer workDaysItem) {
    if (this.workDays == null) {
      this.workDays = new ArrayList<Integer>();
    }
    this.workDays.add(workDaysItem);
    return this;
  }

   /**
   * Get workDays
   * @return workDays
  **/
  @Schema(description = "")
  public List<Integer> getWorkDays() {
    return workDays;
  }

  public void setWorkDays(List<Integer> workDays) {
    this.workDays = workDays;
  }

   /**
   * Get firstDayOfWeek
   * @return firstDayOfWeek
  **/
  @Schema(description = "")
  public Integer getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

   /**
   * Get daysAWeek
   * @return daysAWeek
  **/
  @Schema(description = "")
  public Integer getDaysAWeek() {
    return daysAWeek;
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
    WorkTimeSettings workTimeSettings = (WorkTimeSettings) o;
    return Objects.equals(this.minutesADay, workTimeSettings.minutesADay) &&
        Objects.equals(this.workDays, workTimeSettings.workDays) &&
        Objects.equals(this.firstDayOfWeek, workTimeSettings.firstDayOfWeek) &&
        Objects.equals(this.daysAWeek, workTimeSettings.daysAWeek) &&
        Objects.equals(this.id, workTimeSettings.id) &&
        Objects.equals(this.$type, workTimeSettings.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minutesADay, workDays, firstDayOfWeek, daysAWeek, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkTimeSettings {\n");
    
    sb.append("    minutesADay: ").append(toIndentedString(minutesADay)).append("\n");
    sb.append("    workDays: ").append(toIndentedString(workDays)).append("\n");
    sb.append("    firstDayOfWeek: ").append(toIndentedString(firstDayOfWeek)).append("\n");
    sb.append("    daysAWeek: ").append(toIndentedString(daysAWeek)).append("\n");
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
