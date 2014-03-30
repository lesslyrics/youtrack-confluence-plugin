package youtrack;

import youtrack.comments.IssueComment;
import youtrack.fields.IssueField;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {
	private static final String UNKNOWN = "?";
	@XmlAttribute(name = "id")
	private String id;

	public void setFieldArray(List<IssueField> fieldArray) {
		this.fieldArray = fieldArray;
	}

	public List<IssueField> getFieldArray() {
		return fieldArray;
	}

	@XmlElement(name = "field")
	private List<IssueField> fieldArray;

	public List<IssueComment> getComments() {
		return comments;
	}

	@XmlElement(name = "comment")
	private List<IssueComment> comments;

	@XmlTransient
	private HashMap<String, IssueField> fields;

//	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
//		fields = new HashMap<String, IssueField>();
//		for (IssueField issueField : fieldArray) {
//			fields.put(issueField.getName(), issueField);
//		}
//	}

	public String getId() {
		return id;
	}

	public Boolean isResolved() {
		return fields.containsKey("resolved");
	}

	public String state() {
		return (fields.containsKey("State")) ? fields.get("State").getValue().getValue() : UNKNOWN;
	}

	public String votes() {
		return (fields.containsKey("votes")) ? fields.get("votes").getValue().getValue() : UNKNOWN;
	}

	public String type() {
		return (fields.containsKey("Type")) ? fields.get("Type").getValue().getValue() : UNKNOWN;
	}

	public String priority() {
		return (fields.containsKey("Priority")) ? fields.get("Priority").getValue().getValue() : UNKNOWN;
	}

	public String assignee() {
		return (fields.containsKey("Assignee")) ? fields.get("Assignee").getValue().getValue() : UNKNOWN;
	}

	public String reporter() {
		return (fields.containsKey("reporterFullName")) ? fields.get("reporterFullName").getValue().getValue() : UNKNOWN;
	}

	public String summary() {
		return (fields.containsKey("summary")) ? fields.get("summary").getValue().getValue() : UNKNOWN;
	}
}