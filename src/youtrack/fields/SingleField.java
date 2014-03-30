package youtrack.fields;

import youtrack.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class SingleField extends IssueField {
	@XmlElement(name = "value")
	private IssueFieldValue value;

	public SingleField() {
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
