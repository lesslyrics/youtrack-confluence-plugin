package jetbrains.youtrack.client;

import jetbrains.youtrack.client.api.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class IssuePresentation {

    private final Issue target;

    private final Map<String, String> fields = new HashMap<String, String>();

    public IssuePresentation(Issue target) {
        this.target = target;

        for (IssueCustomField field : target.getCustomFields()) {
            if (field instanceof ProjectCustomFieldAware) {
                ProjectCustomField cf = ((ProjectCustomFieldAware) field).getProjectCustomField();
                Object value = ((ProjectCustomFieldAware) field).getValue();
                String presentation = cf.getEmptyFieldText();
                if (value instanceof User) {
                    presentation = ((User) value).getFullName();
                } else if (value instanceof UserGroup) {
                    presentation = ((UserGroup) value).getName();
                } else if (value instanceof BundleElement) {
                    presentation = ((BundleElement) value).getName();
                } else if (value instanceof List) {
                    presentation = ((List<?>) value).stream().map((v) -> {
                        if (v instanceof User) {
                            return ((User) v).getFullName();
                        } else if (v instanceof UserGroup) {
                            return ((UserGroup) v).getName();
                        } else if (v instanceof BundleElement) {
                            return ((BundleElement) v).getName();
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.joining(", "));
                }
                if (field instanceof DateIssueCustomField) {
                    Object date = ((DateIssueCustomField) field).getValue();
                    if (date != null) {
                        long timestampt = Long.parseLong(date.toString());
                        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
                        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        presentation = isoFormat.format(timestampt);
                    }
                }
                if (field instanceof SimpleIssueCustomField) {
                    Object fieldValue = ((SimpleIssueCustomField) field).getValue();
                    if (fieldValue != null) {
                        presentation = fieldValue.toString();
                    }
                }
                fields.put(cf.getField().getName(), presentation);
            }
        }
        fields.put("summary", target.getSummary());
        fields.put("description", target.getDescription());

    }

    public Issue getIssue() {
        return target;
    }

    public Map<String, String> getFieldValues() {
        return new HashMap<>(fields);
    }

    public String getState() {
        return fields.get("State");
    }

    public String getPriority() {
        return fields.get("Priority");
    }

    public String getType() {
        return fields.get("Type");
    }

    public String getAssignee() {
        return fields.get("Assignee");
    }


}
