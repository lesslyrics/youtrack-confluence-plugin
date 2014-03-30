package youtrack;

import youtrack.comments.IssueComment;
import youtrack.fields.IssueField;

import javax.xml.bind.Unmarshaller;
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

	@XmlElement(name = "field")
	private List<IssueField> fieldArray;
	@XmlElement(name = "comment")
	private List<IssueComment> comments;
	@XmlTransient
	private HashMap<String, IssueField> fields;

	public List<IssueComment> getComments() {
		return comments;
	}

	@SuppressWarnings("UnusedDeclaration")
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		fields = new HashMap<String, IssueField>();
		for (IssueField issueField : fieldArray) {
			fields.put(issueField.getName(), issueField);
		}
	}

	public String getId() {
		return id;
	}

	public Boolean isResolved() {
		return fields.containsKey("resolved");
	}

	public String state() {
		return (fields.containsKey("State")) ? fields.get("State").getStringValue() : UNKNOWN;
	}

	public String votes() {
		return (fields.containsKey("votes")) ? fields.get("votes").getStringValue() : UNKNOWN;
	}

	public String type() {
		return (fields.containsKey("Type")) ? fields.get("Type").getStringValue() : UNKNOWN;
	}

	public String priority() {
		return (fields.containsKey("Priority")) ? fields.get("Priority").getStringValue() : UNKNOWN;
	}

	public String assignee() {
		return (fields.containsKey("Assignee")) ? fields.get("Assignee").getStringValue() : UNKNOWN;
	}

	public String reporter() {
		return (fields.containsKey("reporterFullName")) ? fields.get("reporterFullName").getStringValue() : UNKNOWN;
	}

	public String summary() {
		return (fields.containsKey("summary")) ? fields.get("summary").getStringValue() : UNKNOWN;
	}

}