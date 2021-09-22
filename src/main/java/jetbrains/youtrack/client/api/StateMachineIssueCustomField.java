package jetbrains.youtrack.client.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class StateMachineIssueCustomField extends DatabaseSingleValueIssueCustomField {

    @JsonProperty("value")
    private BundleElement value = null;

    public StateMachineIssueCustomField value(BundleElement value) {
        this.value = value;
        return this;
    }

    /**
     * Get value
     * @return value
     **/
    @Schema(description = "")
    public BundleElement getValue() {
        return value;
    }

    public void setValue(BundleElement value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StateMachineIssueCustomField issueCustomField = (StateMachineIssueCustomField) o;
        return Objects.equals(this.value, issueCustomField.value) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, super.hashCode());
    }

}
