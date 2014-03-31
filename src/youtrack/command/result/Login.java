package youtrack.command.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD)
public class Login {

	@XmlValue
	private String message;

	public Login() {
	}

	public String getError() {
		return message;
	}
}