package eu.kyotoproject.kaf;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**	Parses the Kaf file to create a mapping from tid to page.
 * 
 * @author feikje
 * @version 1.0 25-08-2010
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class KafTermPageSaxParser extends DefaultHandler
{
	private Map<String,String> tidToPage = new HashMap<String,String>();
	private Map<String,String> widToPage = new HashMap<String,String>();
	private String currentTid, language;
		
	public KafTermPageSaxParser(File file) throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();
        FileReader reader = new FileReader(file);
        InputSource inp = new InputSource(reader);
        parser.parse(inp, this);
        reader.close();
	}
	
	public String getPage(String tid)
	{
		if (tidToPage.containsKey(tid))
			return tidToPage.get(tid);
		return null;
	}
	
	public String getLanguage()
	{
		return this.language;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes att) 
		throws SAXException
	{
		if (qName.equals("KAF"))
		{
			language = att.getValue("xml:lang");
		}
		else if (qName.equals("wf"))
		{
			String page = att.getValue("page");
			String wid = att.getValue("wid");
			if ((page != null) && (wid != null))
				widToPage.put(wid, page);
		}
		else if (qName.equals("term"))
		{
			currentTid = att.getValue("tid");
		}
		else if (qName.equals("target") && (currentTid != null))
		{
			String wid = att.getValue("id");
			if (widToPage.containsKey(wid))
			{
				tidToPage.put(currentTid, widToPage.get(wid));
				currentTid = null;
			}
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equals("term"))
			currentTid = null;
	}
}
