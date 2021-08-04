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
 * Represents a change in a &#x60;String&#x60;-type attribute with the support of markup: &#x60;description&#x60; in an Issue or IssueWorkItem, and the &#x60;text&#x60; of the IssueComment. This entity lets you get the rendered text after the change.
 */
@Schema(description = "Represents a change in a `String`-type attribute with the support of markup: `description` in an Issue or IssueWorkItem, and the `text` of the IssueComment. This entity lets you get the rendered text after the change.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class TextMarkupActivityItem extends SimpleValueActivityItem {
  @JsonProperty("removed")
  private String removed = null;

  @JsonProperty("added")
  private String added = null;

  @JsonProperty("markup")
  private String markup = null;

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
    TextMarkupActivityItem textMarkupActivityItem = (TextMarkupActivityItem) o;
    return Objects.equals(this.removed, textMarkupActivityItem.removed) &&
        Objects.equals(this.added, textMarkupActivityItem.added) &&
        Objects.equals(this.markup, textMarkupActivityItem.markup) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(removed, added, markup, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TextMarkupActivityItem {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
