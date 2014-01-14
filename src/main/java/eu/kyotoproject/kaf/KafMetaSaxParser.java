package eu.kyotoproject.kaf;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 23-sep-2008
 * Time: 17:54:07
 * To change this template use File | Settings | File Templates.
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
public class KafMetaSaxParser extends DefaultHandler {
    String value = "";
    public KafMetaData kafMeta;

    public KafMetaSaxParser() {
    }

    public void parseFile(String filePath) {
        try {
            kafMeta = new KafMetaData ();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            FileReader reader = new FileReader(filePath);
            InputSource inp = new InputSource(reader);
            parser.parse(inp, this);
            reader.close();
        } catch (FactoryConfigurationError factoryConfigurationError) {
            factoryConfigurationError.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("filePath = " + filePath);
            System.out.println("XML PARSER ERROR:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//--c

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
       value = "";
        if (qName.equalsIgnoreCase("kaf")) {
            kafMeta = new KafMetaData ();
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equals("xml:lang")) {
                    kafMeta.setLanguage(attributes.getValue(i).trim());
                }
            }
        }
        else   if (qName.equalsIgnoreCase("fileDesc")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("title")) {
                    kafMeta.setTitle(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("author")) {
                    kafMeta.setAuthor(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("filename")) {
                    kafMeta.setFilename(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("filesize")) {
                    try {
                        kafMeta.setFilesize(Long.parseLong(attributes.getValue(i).trim()));
                    } catch (NumberFormatException e) {
                        //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else if (name.equalsIgnoreCase("filetype")) {
                    kafMeta.setFiletype(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("metakey")) {
                    kafMeta.setMetakey(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("pages")) {
                    try {
                        kafMeta.setNPages(Integer.parseInt(attributes.getValue(i).trim()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
        else if (qName.equalsIgnoreCase("captureDesc")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("storagedate")) {
                    kafMeta.setDateString(attributes.getValue(i).trim());
                }
            }
        }
        else   if (qName.equalsIgnoreCase("public")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("project")) {
                    kafMeta.setProject(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("collectionid")) {
                    kafMeta.setCollectionId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("docid")) {
                    kafMeta.setDocId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("dmsid")) {
                    kafMeta.setPublicId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("uri")) {
                    kafMeta.setUrl(attributes.getValue(i).trim());
                }
            }
        }
        else {
            //////
        }
    }//--startElement


    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {
        value += new String(ch, start, length);
    }

}