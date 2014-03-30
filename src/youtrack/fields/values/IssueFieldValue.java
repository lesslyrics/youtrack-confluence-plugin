package youtrack.fields.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "value")
public class IssueFieldValue {

	@XmlValue
	private String value;

	public IssueFieldValue() {
	}

	public String getValue() {
		return value;
	}

}
