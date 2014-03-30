package youtrack.issue.field;

import youtrack.issue.field.value.IssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IssueField {
	@XmlAttribute(name = "name")
	protected String name;

	public IssueField() {
	}

	public String getName() {
		return name;
	}

	public abstract IssueFieldValue getValue();

	public abstract String getStringValue();
}
