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
 * Default settings for the build-type field.
 */
@Schema(description = "Default settings for the build-type field.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-08-04T14:25:38.465627+03:00[Europe/Moscow]")
public class BuildBundleCustomFieldDefaults extends BundleCustomFieldDefaults {
  @JsonProperty("bundle")
  private BuildBundle bundle = null;

  @JsonProperty("defaultValues")
  private List<BuildBundleElement> defaultValues = null;

  public BuildBundleCustomFieldDefaults bundle(BuildBundle bundle) {
    this.bundle = bundle;
    return this;
  }

   /**
   * Get bundle
   * @return bundle
  **/
  @Schema(description = "")
  public BuildBundle getBundle() {
    return bundle;
  }

  public void setBundle(BuildBundle bundle) {
    this.bundle = bundle;
  }

  public BuildBundleCustomFieldDefaults defaultValues(List<BuildBundleElement> defaultValues) {
    this.defaultValues = defaultValues;
    return this;
  }

  public BuildBundleCustomFieldDefaults addDefaultValuesItem(BuildBundleElement defaultValuesItem) {
    if (this.defaultValues == null) {
      this.defaultValues = new ArrayList<BuildBundleElement>();
    }
    this.defaultValues.add(defaultValuesItem);
    return this;
  }

   /**
   * Get defaultValues
   * @return defaultValues
  **/
  @Schema(description = "")
  public List<BuildBundleElement> getDefaultValues() {
    return defaultValues;
  }

  public void setDefaultValues(List<BuildBundleElement> defaultValues) {
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
    BuildBundleCustomFieldDefaults buildBundleCustomFieldDefaults = (BuildBundleCustomFieldDefaults) o;
    return Objects.equals(this.bundle, buildBundleCustomFieldDefaults.bundle) &&
        Objects.equals(this.defaultValues, buildBundleCustomFieldDefaults.defaultValues) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bundle, defaultValues, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BuildBundleCustomFieldDefaults {\n");
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
