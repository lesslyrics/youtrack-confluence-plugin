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

/**
 * Represents default settings for the user-type field.
 */
@Schema(description = "Represents default settings for the user-type field.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class UserCustomFieldDefaults extends CustomFieldDefaults {
  @JsonProperty("bundle")
  private UserBundle bundle = null;

  @JsonProperty("defaultValues")
  private List<User> defaultValues = null;

  public UserCustomFieldDefaults bundle(UserBundle bundle) {
    this.bundle = bundle;
    return this;
  }

   /**
   * Get bundle
   * @return bundle
  **/
  @Schema(description = "")
  public UserBundle getBundle() {
    return bundle;
  }

  public void setBundle(UserBundle bundle) {
    this.bundle = bundle;
  }

  public UserCustomFieldDefaults defaultValues(List<User> defaultValues) {
    this.defaultValues = defaultValues;
    return this;
  }

  public UserCustomFieldDefaults addDefaultValuesItem(User defaultValuesItem) {
    if (this.defaultValues == null) {
      this.defaultValues = new ArrayList<User>();
    }
    this.defaultValues.add(defaultValuesItem);
    return this;
  }

   /**
   * Get defaultValues
   * @return defaultValues
  **/
  @Schema(description = "")
  public List<User> getDefaultValues() {
    return defaultValues;
  }

  public void setDefaultValues(List<User> defaultValues) {
    this.defaultValues = defaultValues;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCustomFieldDefaults userCustomFieldDefaults = (UserCustomFieldDefaults) o;
    return Objects.equals(this.bundle, userCustomFieldDefaults.bundle) &&
        Objects.equals(this.defaultValues, userCustomFieldDefaults.defaultValues) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundle, defaultValues, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCustomFieldDefaults {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    bundle: ").append(toIndentedString(bundle)).append("\n");
    sb.append("    defaultValues: ").append(toIndentedString(defaultValues)).append("\n");
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
