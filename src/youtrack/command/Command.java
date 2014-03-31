package youtrack.command;

import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public interface Command {

	String getUrl();

	Map<String, String> getParams();

	String getMethod();

	boolean usesAuthorization();

	String getAuthorizationToken();

}