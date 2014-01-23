package youtrack;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.HashMap;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {
    private static final String UNKNOWN = "?";
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "field")
    private IssueField[] fieldArray;

    @XmlTransient
    private HashMap<String, IssueField> fields;

    public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        fields = new HashMap<String, IssueField>();
        for (IssueField issueField : fieldArray) {
            fields.put(issueField.getId(), issueField);
        }
    }

    public String getId() {
        return id;
    }

    public Boolean isResolved() {
        return fields.containsKey("resolved");
    }

    public String state() {
        return (fields.containsKey("State")) ? fields.get("State").first() : UNKNOWN;
    }

    public String votes() {
        return (fields.containsKey("votes")) ? fields.get("votes").first() : UNKNOWN;
    }

    public String type() {
        return (fields.containsKey("Type")) ? fields.get("Type").first() : UNKNOWN;
    }

    public String priority() {
        return (fields.containsKey("Priority")) ? fields.get("Priority").first() : UNKNOWN;
    }

    public String assignee() {
        return (fields.containsKey("Assignee")) ? fields.get("Assignee").first() : UNKNOWN;
    }

    public String reporter() {
        return (fields.containsKey("reporterFullName")) ? fields.get("reporterFullName").first() : UNKNOWN;
    }

    public String summary() {
        return (fields.containsKey("summary")) ? fields.get("summary").first() : UNKNOWN;
    }
}