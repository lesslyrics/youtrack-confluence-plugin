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
 * Represents date format.
 */
@Schema(description = "Represents date format.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class DateFormatDescriptor {
  @JsonProperty("presentation")
  private String presentation = null;

  @JsonProperty("pattern")
  private String pattern = null;

  @JsonProperty("datePattern")
  private String datePattern = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

   /**
   * Get presentation
   * @return presentation
  **/
  @Schema(description = "")
  public String getPresentation() {
    return presentation;
  }

   /**
   * Get pattern
   * @return pattern
  **/
  @Schema(description = "")
  public String getPattern() {
    return pattern;
  }

   /**
   * Get datePattern
   * @return datePattern
  **/
  @Schema(description = "")
  public String getDatePattern() {
    return datePattern;
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
    DateFormatDescriptor dateFormatDescriptor = (DateFormatDescriptor) o;
    return Objects.equals(this.presentation, dateFormatDescriptor.presentation) &&
        Objects.equals(this.pattern, dateFormatDescriptor.pattern) &&
        Objects.equals(this.datePattern, dateFormatDescriptor.datePattern) &&
        Objects.equals(this.id, dateFormatDescriptor.id) &&
        Objects.equals(this.$type, dateFormatDescriptor.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(presentation, pattern, datePattern, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DateFormatDescriptor {\n");
    
    sb.append("    presentation: ").append(toIndentedString(presentation)).append("\n");
    sb.append("    pattern: ").append(toIndentedString(pattern)).append("\n");
    sb.append("    datePattern: ").append(toIndentedString(datePattern)).append("\n");
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