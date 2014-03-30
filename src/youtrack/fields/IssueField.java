package youtrack.fields;

import youtrack.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.*;

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
}
