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
 * Represents a value of the text field. Returns both source and rendered text.
 */
@Schema(description = "Represents a value of the text field. Returns both source and rendered text.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "$type", visible = true )
@JsonSubTypes({
})

public class TextFieldValue {
  @JsonProperty("text")
  private String text = null;

  @JsonProperty("markdownText")
  private String markdownText = null;

  @JsonProperty("id")
  private String id = null;

  @JsonTypeId
  private String $type = null;

   /**
   * Get text
   * @return text
  **/
  @Schema(description = "")
  public String getText() {
    return text;
  }

   /**
   * Get markdownText
   * @return markdownText
  **/
  @Schema(description = "")
  public String getMarkdownText() {
    return markdownText;
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
    TextFieldValue textFieldValue = (TextFieldValue) o;
    return Objects.equals(this.text, textFieldValue.text) &&
        Objects.equals(this.markdownText, textFieldValue.markdownText) &&
        Objects.equals(this.id, textFieldValue.id) &&
        Objects.equals(this.$type, textFieldValue.$type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, markdownText, id, $type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TextFieldValue {\n");
    
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    markdownText: ").append(toIndentedString(markdownText)).append("\n");
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