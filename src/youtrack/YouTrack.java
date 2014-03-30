package youtrack;

import org.xml.sax.SAXException;
import youtrack.issue.field.*;
import youtrack.issue.field.value.AttachmentFieldValue;
import youtrack.issue.field.value.LinkFieldValue;
import youtrack.issue.field.value.MultiUserFieldValue;
import youtrack.issue.Issue;

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
import java.util.Properties;

/**
 * Created by Egor.Malyshev on 18.12.13.
 */
public class YouTrack {

	private final String enc = "UTF-8";

	String userName;
	String password;
	String baseHost;

	public YouTrack() {
	}

	/**
	 * Returns authentication token for use with YouTrack REST API. Make sure you have configured access in settings.properties file.
	 *
	 * @return token or an empty string if an error occurred.
	 */

	public String getAuth() {
		String result = "";
		try {
			init();
			String contentType = "application/x-www-form-urlencoded";
			URL url = new URL(baseHost + "user/login");
			HttpURLConnection conn = (HttpURLConnection) getUrlConnection(url);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", contentType);
			String encodedData = "login=" + URLEncoder.encode(userName, enc) + "&password=" + URLEncoder.encode(password, enc);
			conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), enc);
			wr.write(encodedData);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), enc));
			String line;
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			wr.close();
			rd.close();
			result = conn.getHeaderField("Set-Cookie");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private void init() throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream("/resources/settings.properties");
		prop.load(stream);
		userName = prop.getProperty("username");
		password = prop.getProperty("password");
		baseHost = prop.getProperty("host");
	}

	/**
	 * Helper method to deserealize XML to objects. Used to create Issue from XML response received from YouTrack.
	 *
	 * @param xmlString Raw XML code.
	 * @return Instance of an issue.
	 * @throws ParserConfigurationException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Issue issueFromXml(String xmlString) throws ParserConfigurationException, JAXBException, SAXException, IOException, XMLStreamException {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xmlString));

		streamReader = new HackedReader(streamReader);

		JAXBContext jaxbContext = JAXBContext.newInstance(Issue.class, IssueField.class, CustomFieldValue.class, AttachmentField.class,
				LinkField.class, MultiUserField.class, SingleField.class, MultiUserFieldValue.class, AttachmentFieldValue.class,
				LinkFieldValue.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Issue) jaxbUnmarshaller.unmarshal(streamReader);
	}

	/**
	 * Retreives specified YouTrack issue as an object.
	 *
	 * @param id        Issue id.
	 * @param authToken authentincation token.
	 * @return An instance of Issue or null if there was an error.
	 */
	public Issue getIssue(String id, String authToken) {
		Issue result = null;
		try {
			init();
			String xmlString = "";
			HttpURLConnection conn = (HttpURLConnection) getUrlConnection(new URL(baseHost + "issue/" + id));
			conn.setRequestProperty("Cookie", authToken);
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), enc));
			String line;
			while ((line = rd.readLine()) != null) {
				xmlString += line;
			}
			rd.close();
			result = issueFromXml(xmlString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
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