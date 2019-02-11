package com.dong.matko.toolbox.cpw;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.microsoft.schemas.sharepoint.soap.Copy;
import com.microsoft.schemas.sharepoint.soap.CopyResultCollection;
import com.microsoft.schemas.sharepoint.soap.CopySoap;
import com.microsoft.schemas.sharepoint.soap.DestinationUrlCollection;
import com.microsoft.schemas.sharepoint.soap.FieldInformation;
import com.microsoft.schemas.sharepoint.soap.FieldInformationCollection;
import com.microsoft.schemas.sharepoint.soap.GetListItems;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponse;
import com.microsoft.schemas.sharepoint.soap.Lists;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import com.microsoft.schemas.sharepoint.soap.Versions;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap;

public class SharePointClient {

	private String username;
	private String password;

	private String BasesharepointUrl = "https://cpw.dongenergy.dk";
	private String site = "/sites/anh01/SWP_QA";
	private String listName = "TEKDOC";

	@SuppressWarnings("unused")
	private VersionsSoap versionssoapstub;
	private ListsSoap listsoapstub;
	private CopySoap copysoapstub;

	private Logger LOGGER = Logger.getLogger(getClass().getName());

	public SharePointClient(String _username, String _password) {
		try {
			username = _username;
			password = _password;
			NtlmAuthenticator authenticator = new NtlmAuthenticator(username, password);
			Authenticator.setDefault(authenticator);

			//LOGGER.info(SharePointClient.getSharePointList("TEKDOC", "", "").toString());

			//Checks-out a file
			//String checkoutURL = BasesharepointUrl+"test.log";
			//SharePointClient.checkOutFile(listsoapstub,checkoutURL);
			//SharePointClient.undoCheckOutFile(listsoapstub,checkoutURL);
			//SharePointClient.checkOutFile(listsoapstub,checkoutURL);


			//Download a document with CopySoap Web Service
			//DownloadDocumentVersionsFromSPDocumentLibrary(BasesharepointUrl+"test.log");

			// <!-- Do something on downloaded document -->
			//
			// Upload a file - Remember its not needed to checkout first, you can directly upload a new document.
			//
			//SharePointClient.UploadFileUsingCopyWebService("sharepoint-downloads/test.log");

			//Checks-In
			//SharePointClient.checkInFile(listsoapstub, checkoutURL, "Test Checkin");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex);
		}
	}

	public boolean initSoaps(){
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Authenticating LISTS, VERSIONS & COPY Web services with username: " + username + "\r\n\n");
			listsoapstub = getSPListSoapStub(username, password, BasesharepointUrl, site);
			versionssoapstub = getSPVersionsStub(username, password,BasesharepointUrl, site);
			copysoapstub = getSPCopySoapStub(username, password, BasesharepointUrl, site);
			//getSharePointList("TEKDOC","","");
			return testConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info("Failed to create soaps!\r\n\n");
			return false;
		}
	}

	public URL convertToURLEscapingIllegalCharacters(String string){
		try {
			String decodedURL = URLDecoder.decode(string, "UTF-8");
			URL url = new URL(decodedURL);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			return uri.toURL();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	public ListsSoap getSPListSoapStub(String username, String password, String url, String site) throws Exception {
		ListsSoap port = null;
		if (username != null && password != null) {
			try {
				URL wsdlURL = new URL(getClass().getResource("/wsdl/lists.wsdl").toExternalForm());
				Lists service = new Lists(wsdlURL);
				port = service.getListsSoap();
				((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
				((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

				URL convertedurl = convertToURLEscapingIllegalCharacters(url+site+"/_vti_bin/Lists.asmx");
				((BindingProvider) port).getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedurl.toString());

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error: " + e.toString());
			}
		} else {
			throw new Exception("Couldn't authenticate: Invalid connection details given.");
		}
		return port;
	}


	public VersionsSoap getSPVersionsStub(String userName, String password, String url, String site) throws Exception {
		VersionsSoap port = null;

		if (userName != null && password != null) {
			try {
				URL wsdlURL = new URL(getClass().getResource("/wsdl/versions.wsdl").toExternalForm());
				Versions service = new Versions(wsdlURL);
				port = service.getVersionsSoap();
				((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userName);
				((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
				URL convertedurl = convertToURLEscapingIllegalCharacters(url+site+"/_vti_bin/Versions.asmx");
				((BindingProvider) port).getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedurl.toString());
			} catch (Exception e) {
				throw new Exception("Error: " + e.toString());
			}
		} else {
			throw new Exception("Couldn�t authenticate: Invalid connection details given.");
		}
		return port;
	}

	public CopySoap getSPCopySoapStub(String userName, String password, String url, String site) throws Exception {
		CopySoap port = null;

		if (userName != null && password != null) {
			try {
				URL wsdlURL = new URL(getClass().getResource("/wsdl/copy.wsdl").toExternalForm());
				Copy service = new Copy(wsdlURL);
				port = service.getCopySoap();
				((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userName);
				((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
				URL convertedurl = convertToURLEscapingIllegalCharacters(url+site+"/_vti_bin/Copy.asmx");
				((BindingProvider) port).getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedurl.toString());
			}
			catch (Exception e) {
				throw new Exception("Error: " + e.toString());
			}
		} else {
			throw new Exception("Couldn�t authenticate: Invalid connection details given.");
		}
		return port;
	}

	/**
	 * Creates a string from an XML file with start and end indicators
	 * @param docToString document to convert
	 * @return string of the xml document
	 */
	public String xmlToString(Document docToString) {
		String returnString = "";
		try {
			//create string from xml tree
			//Output the XML
			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans;
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			StreamResult streamResult = new StreamResult(sw);
			DOMSource source = new DOMSource(docToString);
			trans.transform(source, streamResult);
			String xmlString = sw.toString();
			returnString = returnString + xmlString;
		} catch (TransformerException ex) {
			LOGGER.severe(ex.toString());
		}
		return returnString;
	}

	/**
	 * Connects to a SharePoint Lists Web Service through the given open port,
	 * and reads all the elements of the given list. Only the given column names
	 * are displayed.
	 */
	public void displaySharePointList() throws Exception {
		try {



			// you can also give id of "Documents" node
			//{44131435-EAFB-4244-AA39-F431F55ADA9B}
			//

			String rowLimit = "3000";
			ArrayList<String> listColumnNames = new ArrayList<String>();
			listColumnNames.add("LinkFilename");
			listColumnNames.add("FileRef");


			//Here are additional parameters that may be set
			String viewName = "";
			GetListItems.ViewFields viewFields = null;
			GetListItems.Query query = new GetListItems.Query();
			query.getContent().add(generateXmlNode("<Query><Where><BeginsWith><FieldRef Name='Tekdoc_Unit' /><Value Type='Text'>A01</Value></BeginsWith></Where></Query>"));
			GetListItems.QueryOptions queryOptions = new GetListItems.QueryOptions();
			queryOptions.getContent().add(generateXmlNode("<QueryOptions><IncludeAttachmentUrls>True</IncludeAttachmentUrls><View Scope='RecursiveAll' /></QueryOptions>"));
			String webID = "";

			//Calling the List Web Service
			GetListItemsResponse.GetListItemsResult result = listsoapstub.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
			//GetListResponse.GetListResult result = listsoapstub.getList(listName);
			Object listResult = result.getContent().get(0);
			if ((listResult != null) && (listResult instanceof Element)) {
				Element node = (Element) listResult;

				//Dumps the retrieved info in the console
				Document document = node.getOwnerDocument();
				LOGGER.info("SharePoint Online Lists Web Service Response:\n\r" + xmlToString(document));

				//selects a list of nodes which have z:row elements
				NodeList list = node.getElementsByTagName("z:row");
				//LOGGER.info("=> " + list.getLength() + " results from SharePoint Online");

				TreeSet<String> folders = new TreeSet<String>();

				//Displaying every result received from SharePoint, with its ID
				for (int i = 0; i < list.getLength(); i++) {
					//Gets the attributes of the current row/element
					//NamedNodeMap attributes = list.item(i).getAttributes();
					//String mFolder = attributes.getNamedItem("ows_FileRef").getNodeValue().replace(attributes.getNamedItem("ows_ID").getNodeValue() + ";#" + site.substring(1) + "/" + listName + "/", "");
					//String folder = mFolder.substring(0, mFolder.indexOf("/"));
					//folders.add(folder);
					//LOGGER.info("******** Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue()+" ********");

					//Displays all the attributes of the list item that correspond to the column names given
					/*for (String columnName : listColumnNames) {
						String internalColumnName = "ows_" + columnName;
						if (attributes.getNamedItem(internalColumnName) != null) {
							LOGGER.info(columnName + ": " + attributes.getNamedItem(internalColumnName).getNodeValue());
						} else {
							throw new Exception("Couldn't find the '" + columnName + "' column in the '" + listName + "' list in SharePoint.\n");
						}
					}*/
				}
				for(String f : folders){
					System.out.printf(f + "\n");
				}
			} else {
				throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Exception. See stacktrace.\n" + ex.toString() + "\n");
		}
	}

	public ArrayList<CPWFile> getSharePointList(String _listName, String searchType, String searchArg) throws Exception {
		try {

			ArrayList<CPWFile> fileList = new ArrayList<CPWFile>();

			// you can also give id of "Documents" node
			//{44131435-EAFB-4244-AA39-F431F55ADA9B}
			//

			String rowLimit = "5";

			String viewName = "";
			GetListItems.ViewFields viewFields = null;
			GetListItems.Query query = null;
			if(!searchType.equals("") && !searchArg.equals("")){
				query = new GetListItems.Query();
				query.getContent().add(generateXmlNode("<Query><Where><BeginsWith><FieldRef Name='" + searchType + "' /><Value Type='Text'>" + searchArg + "</Value></BeginsWith></Where></Query>"));
			}
			GetListItems.QueryOptions queryOptions = new GetListItems.QueryOptions();
			queryOptions.getContent().add(generateXmlNode("<QueryOptions><IncludeAttachmentUrls>True</IncludeAttachmentUrls><View Scope='Recursive' /></QueryOptions>"));
			String webID = "";

			GetListItemsResponse.GetListItemsResult result = listsoapstub.getListItems(_listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
			Object listResult = result.getContent().get(0);
			if ((listResult != null) && (listResult instanceof Element)) {
				Element node = (Element) listResult;

				//Document document = node.getOwnerDocument();
				//LOGGER.info("SharePoint Online Lists Web Service Response:\n\r" + xmlToString(document));

				NodeList list = node.getElementsByTagName("z:row");

				for (int i = 0; i < list.getLength(); i++) {
					NamedNodeMap attributes = list.item(i).getAttributes();

					CPWFile file = new CPWFile();
					String internalColumnName = "ows_LinkFilename";
					if (attributes.getNamedItem(internalColumnName) != null) {
						file.setName(attributes.getNamedItem(internalColumnName).getNodeValue());
					}
					internalColumnName = "ows_FileRef";
					if (attributes.getNamedItem(internalColumnName) != null) {
						file.setPath(attributes.getNamedItem(internalColumnName).getNodeValue());
					}
					internalColumnName = "Tekdoc_DocumentNumber";
					if (attributes.getNamedItem(internalColumnName) != null) {
						file.setRDSPP(attributes.getNamedItem(internalColumnName).getNodeValue());
					}
					internalColumnName = "ows_Modified";
					if (attributes.getNamedItem(internalColumnName) != null) {
						file.setMod(attributes.getNamedItem(internalColumnName).getNodeValue());
					}
					internalColumnName = "vti_modifiedby";
					if (attributes.getNamedItem(internalColumnName) != null) {
						file.setModBy(attributes.getNamedItem(internalColumnName).getNodeValue());
					}
					fileList.add(file);

				}
				return fileList;
			} else {
				throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Exception. See stacktrace.\n" + ex.toString() + "\n");
		}
	}

	public boolean testConnection() {
		try {

			String rowLimit = "1";
			String viewName = "";
			GetListItems.ViewFields viewFields = null;
			GetListItems.Query query = null;
			GetListItems.QueryOptions queryOptions = null;
			String webID = "";

			listsoapstub.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Node generateXmlNode(String sXML) throws ParserConfigurationException,
	SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document documentOptions = builder.parse(new InputSource(new StringReader(sXML)));
		Node elementOptions = documentOptions.getFirstChild();
		try {
			saveToXml(documentOptions, "fil2e.xml");
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return elementOptions;
	}


	/**
	 * Checks-out the specified file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @return true if the operation succeeded; otherwise, false.
	 */
	public boolean checkOutFile(ListsSoap port, String pageUrl) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Checking-out pageUrl=" + pageUrl);
		}
		String checkoutToLocal = "true";
		String lastModified    = "";
		boolean result = port.checkOutFile(pageUrl, checkoutToLocal, lastModified);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Check-out result = " + result);
		}
		return result;
	}

	/**
	 * Undo checked-out file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @return true if the operation succeeded; otherwise, false.
	 */
	public boolean undoCheckOutFile(ListsSoap port, String pageUrl) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Undo checkout pageUrl=" + pageUrl);
		}
		boolean result = port.undoCheckOut(pageUrl);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Undo checkout result = " + result);
		}
		return result;
	}

	/**
	 * Checks-in the specified file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @param comment
	 * @return true if the operation succeeded; otherwise, false.
	 */
	public boolean checkInFile(ListsSoap port, String pageUrl, String comment) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Checking-in pageUrl=" + pageUrl + " comment=" + comment);
		}
		// checkinType = values 0, 1 or 2, where 0 = MinorCheckIn, 1 = MajorCheckIn, and 2 = OverwriteCheckIn.
		String checkinType = "0";
		boolean result = port.checkInFile(pageUrl, comment, checkinType);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Check-in result = " + result);
		}
		return result;
	}


	/**
	 *

	 public static void DownloadDocumentVersionsFromSPDocumentLibrary(String FileUrl) throws Exception {
	 try {
		 URL convertedurl = convertToURLEscapingIllegalCharacters(FileUrl);
		 System.out.println("convertedurl.toString()=" + convertedurl.toString());
		 GetVersionsResponse.GetVersionsResult result = DocumentLibrary.VersionsGetVersions(versionssoapstub,convertedurl.toString());
		 Object listResult = result.getContent().get(0);
		 System.out.println("GetVersions Result=" + result);
		 if ((listResult != null) && (listResult instanceof ElementNSImpl)) {
		 ElementNSImpl node = (ElementNSImpl) listResult;

		 //Dumps the retrieved info in the console
		 Document document = node.getOwnerDocument();
		 System.out.println("SharePoint Online Lists Web Service Response:" + xmlToString(document));

		 //selects a list of nodes which have z:row elements
		 NodeList list = node.getElementsByTagName("result");//("z:row");
		 System.out.println("=> " + list.getLength() + " results from SharePoint Online");

		 //Displaying every result received from SharePoint
		 for (int i = 0; i < list.getLength(); i++) {

			 //Gets the attributes of the current row/element
			 NamedNodeMap attributes = list.item(i).getAttributes();
			 String ver = attributes.getNamedItem("version").getNodeValue();
			 //Download Latest Version only
			 if (ver.indexOf("@") != -1) {
				 System.out.println("******** Url: " + attributes.getNamedItem("url").getNodeValue() + " ********");
				 //Download File on Local Hard Disk using Copy Web Service
				 DownloadFileUsingCopyWebService(attributes.getNamedItem("url").getNodeValue(), "sharepoint-downloads", attributes.getNamedItem("version").getNodeValue());
			 }
		 }
		 } else {
		 	throw new Exception("List response from SharePoint is either null or corrupt\n");
		 }

		 } catch (Exception e) {
			 e.printStackTrace();
			 throw new Exception("Error: " + e.toString());
		 }
}
	 */

	/**
	 *
	 * @param sourceUrl
	 * @param destination
	 * @param versionNumber
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void DownloadFileUsingCopyWebService(String sourceUrl, String destination, String versionNumber) throws Exception {
		try {

			//Extract the filename from the source URL
			String fileName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1);

			//		 if (versionNumber != null) {
			//			 fileName = versionNumber + "-" + fileName;
			//		 }
			destination = destination + "\\" + fileName;

			//Prepare the Parameters required for the method
			javax.xml.ws.Holder fieldInfoArray = new javax.xml.ws.Holder();
			javax.xml.ws.Holder cResultArray = new javax.xml.ws.Holder();
			javax.xml.ws.Holder fileContents = new javax.xml.ws.Holder(); // no need to initialize the GetItem takes care of that.

			//Cal Web Service Method
			copysoapstub.getItem(sourceUrl, cResultArray, fieldInfoArray, fileContents);

			//Write the byte[] to the output file
			//Integer val = fileContents.value;
			FileOutputStream fos = new FileOutputStream(destination);
			fos.write((byte[])fileContents.value);
			fos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error: " + ex.toString());
		}
	}
	public static void saveToXml(Document xmlDoc, String filePath) throws TransformerException {
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = new StreamResult(new File(filePath));

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		transformer.transform(source, result);
	}

	/**
	 *
	 * @param sourceUrl
	 * @param destination
	 * @param versionNumber
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public void UploadFileUsingCopyWebService(String filepath) throws Exception {
		try {

			File file = new File(filepath);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte[] stream = new byte[bis.available()];
			bis.read(stream);
			FieldInformation fieldInformation = new FieldInformation();
			fieldInformation.setDisplayName(file.getName());
			fieldInformation.setInternalName(file.getName());
			FieldInformationCollection fields = new FieldInformationCollection();
			fields.getFieldInformation().add(fieldInformation);

			String sourceUrl = file.getName();
			DestinationUrlCollection destinationUrls = new DestinationUrlCollection();
			destinationUrls.getString().add(BasesharepointUrl +file.getName());
			Holder<Long> copyIntoItemsResult = new Holder<Long>();
			Holder<CopyResultCollection> results = new Holder<CopyResultCollection>();

			copysoapstub.copyIntoItems(sourceUrl, destinationUrls, fields, stream, copyIntoItemsResult, results);
			// error message always exists
			String errorMessage = results.value.getCopyResult().get(0).getErrorMessage();
			if (errorMessage != null) {
				throw new FileAlreadyExistsException("File already exists in directory");
			} else {
				System.out.println("file " + file.getName() + " has been successfully uploaded to server" + "\n ");
			}
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		} catch (Exception ex) {
			throw new Exception("Error: " + ex.toString());
		}
	}

}
