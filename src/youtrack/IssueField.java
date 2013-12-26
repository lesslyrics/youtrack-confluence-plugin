package youtrack;

import javax.xml.bind.annotation.*;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "field")
public class IssueField {
    @XmlAttribute(name = "name")
    private String id;

    @XmlElement(name = "value")
    private String[] values;

    public String getId() {
        return id;
    }

    public String first() {
        return values[0];
    }

    public String[] getValues() {
        return values;
    }

}
