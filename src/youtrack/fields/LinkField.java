package youtrack.fields;

import youtrack.fields.values.LinkFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class LinkField extends IssueField {
	@XmlElement(name = "value")
	private LinkFieldValue value;

	public LinkField() {
	}

	@Override
	public LinkFieldValue getValue() {
		return value;
	}

	@Override
	public String getStringValue() {
		return value.getValue();
	}
}
