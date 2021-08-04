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

/**
 * Represents an activity that affects a custom field of the &#x60;text&#x60; type of an issue.
 */
@Schema(description = "Represents an activity that affects a custom field of the `text` type of an issue.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class TextCustomFieldActivityItem extends CustomFieldActivityItem {
  @JsonProperty("target")
  private Issue target = null;

  @JsonProperty("removed")
  private String removed = null;

  @JsonProperty("added")
  private String added = null;

  @JsonProperty("markup")
  private String markup = null;

  public TextCustomFieldActivityItem target(Issue target) {
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
  public String getRemoved() {
    return removed;
  }

   /**
   * Get added
   * @return added
  **/
  @Schema(description = "")
  public String getAdded() {
    return added;
  }

   /**
   * Get markup
   * @return markup
  **/
  @Schema(description = "")
  public String getMarkup() {
    return markup;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TextCustomFieldActivityItem textCustomFieldActivityItem = (TextCustomFieldActivityItem) o;
    return Objects.equals(this.target, textCustomFieldActivityItem.target) &&
        Objects.equals(this.removed, textCustomFieldActivityItem.removed) &&
        Objects.equals(this.added, textCustomFieldActivityItem.added) &&
        Objects.equals(this.markup, textCustomFieldActivityItem.markup) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, removed, added, markup, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TextCustomFieldActivityItem {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    removed: ").append(toIndentedString(removed)).append("\n");
    sb.append("    added: ").append(toIndentedString(added)).append("\n");
    sb.append("    markup: ").append(toIndentedString(markup)).append("\n");
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
