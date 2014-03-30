package youtrack.fields;

import youtrack.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CustomFieldValue extends IssueField {
	@XmlElement(name = "value")
	private IssueFieldValue value;

	public CustomFieldValue() {
	}

	@Override
	public IssueFieldValue getValue() {
		return value;
	}

	@Override
	public String getStringValue() {
		return value.getValue();
	}
}
