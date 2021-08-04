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
 * Represents the Notifications settings of the YouTrack service.
 */
@Schema(description = "Represents the Notifications settings of the YouTrack service.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class NotificationSettings {
  @JsonProperty("emailSettings")
  private EmailSettings emailSettings = null;

  @JsonProperty("jabberSettings")
  private JabberSettings jabberSettings = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

  public NotificationSettings emailSettings(EmailSettings emailSettings) {
    this.emailSettings = emailSettings;
    return this;
  }

   /**
   * Get emailSettings
   * @return emailSettings
  **/
  @Schema(description = "")
  public EmailSettings getEmailSettings() {
    return emailSettings;
  }

  public void setEmailSettings(EmailSettings emailSettings) {
    this.emailSettings = emailSettings;
  }

  public NotificationSettings jabberSettings(JabberSettings jabberSettings) {
    this.jabberSettings = jabberSettings;
    return this;
  }

   /**
   * Get jabberSettings
   * @return jabberSettings
  **/
  @Schema(description = "")
  public JabberSettings getJabberSettings() {
    return jabberSettings;
  }

  public void setJabberSettings(JabberSettings jabberSettings) {
    this.jabberSettings = jabberSettings;
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
    NotificationSettings notificationSettings = (NotificationSettings) o;
    return Objects.equals(this.emailSettings, notificationSettings.emailSettings) &&
        Objects.equals(this.jabberSettings, notificationSettings.jabberSettings) &&
        Objects.equals(this.id, notificationSettings.id) &&
        Objects.equals(this.$type, notificationSettings.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emailSettings, jabberSettings, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificationSettings {\n");
    
    sb.append("    emailSettings: ").append(toIndentedString(emailSettings)).append("\n");
    sb.append("    jabberSettings: ").append(toIndentedString(jabberSettings)).append("\n");
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
