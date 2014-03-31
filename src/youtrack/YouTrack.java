package youtrack;

import org.xml.sax.SAXException;
import youtrack.command.Command;
import youtrack.command.result.*;
import youtrack.command.result.Error;
import youtrack.issue.Issue;
import youtrack.issue.field.*;
import youtrack.issue.field.value.AttachmentFieldValue;
import youtrack.issue.field.value.LinkFieldValue;
import youtrack.issue.field.value.MultiUserFieldValue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Egor.Malyshev on 18.12.13.
 */
public class YouTrack {

	private final static String enc = "UTF-8";
	private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private final String hostAddress;

	public YouTrack(String hostAddress) {

		this.hostAddress = hostAddress;
	}

	/**
	 * Executes a YouTrack command described by an object that implements @link Command interface.
	 *
	 * @return instance of @link Result containing command execution results.
	 */

	public Result execute(Command command) {

		Result result = new Result();

		try {

			String commandData = "";
			URL url = new URL(hostAddress + command.getUrl());
			HttpURLConnection httpURLConnection = (HttpURLConnection) getUrlConnection(url);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod(command.getMethod());

			if (command.getParams() != null) {
				for (String commandKey : command.getParams().keySet()) {

					commandData += commandKey + "=" + URLEncoder.encode(command.getParams().get(commandKey), enc) + "&";
				}
				commandData = commandData.substring(0, commandData.length() - 1);
				httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(commandData.length()));
			}

			if (command.usesAuthorization()) {
				httpURLConnection.setRequestProperty("Cookie", command.getAuthorizationToken());
			}

			OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), enc);
			writer.write(commandData);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), enc));
			String responseBuffer;
			String response = "";

			while ((responseBuffer = reader.readLine()) != null) {
				response += responseBuffer;
			}

			writer.close();
			reader.close();

			result.setAuthToken(httpURLConnection.getHeaderField("Set-Cookie"));

			Object responseObject = objectFromXml(response);

			if (responseObject instanceof Issue) {

				result.setIssue((Issue) responseObject);
			} else {
				if (responseObject instanceof Error) {
					result.setError((Error) responseObject);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Helper method to deserealize XML to objects. Used to interpret XML response received from YouTrack.
	 *
	 * @param xmlString Raw XML code.
	 * @return Instance of an object.
	 * @throws ParserConfigurationException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Object objectFromXml(String xmlString) throws ParserConfigurationException, JAXBException, SAXException, IOException, XMLStreamException {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xmlString));

		streamReader = new HackedReader(streamReader);

		JAXBContext jaxbContext = JAXBContext.newInstance(Issue.class, IssueField.class, CustomFieldValue.class, AttachmentField.class,
				LinkField.class, MultiUserField.class, SingleField.class, MultiUserFieldValue.class, AttachmentFieldValue.class,
				LinkFieldValue.class, youtrack.command.result.Error.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(streamReader);
	}

	/**
	 * Helper method to get an instance of URLConnection with custom timeouts.
	 *
	 * @param remoteUrl URL to which connection is requested.
	 * @return URLConnection instance.
	 * @throws IOException
	 */
	private URLConnection getUrlConnection(URL remoteUrl) throws IOException {
		URLConnection urlConnection = remoteUrl.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(15000);
		return urlConnection;
	}

	/**
	 * Class to work around the JAXB name handling.
	 * Forces upper case on the first letter of xsi:type attribute.
	 */
	private class HackedReader extends StreamReaderDelegate {

		public HackedReader(XMLStreamReader xmlStreamReader) {
			super(xmlStreamReader);
		}

		@Override
		public String getAttributeValue(int index) {
			String attributeValue = super.getAttributeValue(index);
			if (getAttributeLocalName(index).equals("type"))
				return attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1);
			else return super.getAttributeValue(index);
		}

	}

}