package youtrack;

import org.xml.sax.SAXException;
import youtrack.fields.*;
import youtrack.fields.values.AttachmentFieldValue;
import youtrack.fields.values.LinkFieldValue;
import youtrack.fields.values.MultiUserFieldValue;

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
/*
        Properties prop = new Properties();
        ClassLoader loader = getClass().getClassLoader();
        InputStream stream = loader.getResourceAsStream("/resources/settings.properties");
        prop.load(stream);
        userName = prop.getProperty("username");
        password = prop.getProperty("password");
        baseHost = prop.getProperty("host");
*/
		userName = "megor";
		password = "H8gpr09,";
		baseHost = "http://youtrack.jetbrains.com/rest/";
	}

	/**
	 * Helper method to deserealize XML to objects. Used to create Issue from XML response received from YouTrack.
	 *
	 * @param xmlString  Raw XML code.
	 * @param resultType Type of object to retrieve.
	 * @return Instance of an object.
	 * @throws ParserConfigurationException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Object GetObjectFromXml(String xmlString, Class resultType) throws ParserConfigurationException, JAXBException, SAXException, IOException, XMLStreamException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder;
//		builder = factory.newDocumentBuilder();


		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlString));
		xsr = new MyStreamReaderDelegate(xsr);

		JAXBContext jaxbContext = JAXBContext.newInstance(resultType, IssueField.class, CustomFieldValue.class, AttachmentField.class,
				LinkField.class, MultiUserField.class, SingleField.class, MultiUserFieldValue.class, AttachmentFieldValue.class,
				LinkFieldValue.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		factory.setNamespaceAware(true);
		return jaxbUnmarshaller.unmarshal(xsr);
//		return jaxbUnmarshaller.unmarshal(builder.parse(new InputSource(new StringReader(xmlString))));
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
			result = (Issue) GetObjectFromXml(xmlString, Issue.class);
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

	private static class MyStreamReaderDelegate extends StreamReaderDelegate {

		public MyStreamReaderDelegate(XMLStreamReader xsr) {
			super(xsr);
		}

		@Override
		public String getAttributeValue(int index) {
			String attributeValue = super.getAttributeValue(index);
			String attributeLocalName = getAttributeLocalName(index);
			if (attributeLocalName.equals("type"))
				return attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1);
			else return super.getAttributeValue(index);
		}

	}

}