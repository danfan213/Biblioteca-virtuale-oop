package utility;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {
	private String tei;
	private String teiText;
	public ReadXMLFile(String tei){
		this.tei=tei;

	}

   public String parserTei() {
	   
    try {

	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();

	DefaultHandler handler = new DefaultHandler() {

	boolean title = false;
	boolean year = false;
	boolean author = false;
	boolean user= false;
	boolean titlePart= false;
	boolean text= false;

	public void startElement(String uri, String localName,String qName,
                Attributes attributes) throws SAXException {

		System.out.println("Start Element :" + qName);

//		if (qName.equalsIgnoreCase("title")) {
//			title = true;
//		}
//
//		if (qName.equalsIgnoreCase("year")) {
//			year = true;
//		}
//
//		if (qName.equalsIgnoreCase("author")) {
//			author = true;
//		}
//
//		if (qName.equalsIgnoreCase("user")) {
//			user = true;
//		}
//		if (qName.equalsIgnoreCase("titlePart")) {
//			titlePart = true;
//		}
		if (qName.equalsIgnoreCase("div")) {
			text = true;
		}

	}

	

	public void characters(char ch[], int start, int length) throws SAXException {
//
//		if (bfname) {
//			System.out.println("First Name : " + new String(ch, start, length));
//			bfname = false;
//		}
//
//		if (blname) {
//			System.out.println("Last Name : " + new String(ch, start, length));
//			blname = false;
//		}
//
//		if (bnname) {
//			System.out.println("Nick Name : " + new String(ch, start, length));
//			bnname = false;
//		}

		if (text) {
			teiText= new String(ch, start, length);
			text = false;
			tei="";
		}

	}

     };
     try{
     saxParser.parse(new InputSource(new StringReader(this.tei)), handler);
     }
     catch(java.lang.NullPointerException e){
    	 teiText="";
    	 return teiText;
     }
    } catch (Exception e) {
       e.printStackTrace();
     }
    return teiText;
   }

}