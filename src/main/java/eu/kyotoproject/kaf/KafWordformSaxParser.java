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
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 17-dec-2008
 * Time: 14:38:37
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
public class KafWordformSaxParser extends DefaultHandler {

    String value = "";
    public ArrayList<KafWordForm> kafWordFormList;
    KafWordForm kafWordForm;

    public KafWordformSaxParser() {
        init();
    }

    public KafWordformSaxParser(String filePath, String encoding) {
        try {
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            FileReader reader = new FileReader(filePath);
            InputSource inp = new InputSource(reader);
            inp.setEncoding(encoding);
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

    public void parseFile(String filePath) {
        try {
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            InputSource inp = new InputSource (new FileReader(filePath));
            //inp.setEncoding(encoding);
            parser.parse(inp, this);
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

    public void parseContent(String content) {
        try {
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            parser.parse(new ByteArrayInputStream(content.getBytes()), this);
        } catch (FactoryConfigurationError factoryConfigurationError) {
            factoryConfigurationError.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("XML PARSER ERROR:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//--c

    void init() {
         kafWordFormList = new ArrayList<KafWordForm>();
         kafWordForm = new KafWordForm();
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
        //System.out.println("qName = " + qName);
       value = "";
       if (qName.equals("wf")) {
           //<wf wid="wid58832.8">dat</wf>
           kafWordForm = new KafWordForm();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equals("wid")) {
                   kafWordForm.setWid(attributes.getValue(i).trim());
               }
               if (name.equals("para")) {
                   kafWordForm.setPara(attributes.getValue(i).trim());
               }
               if (name.equals("page")) {
                   kafWordForm.setPage(attributes.getValue(i).trim());
               }
               if (name.equals("sent")) {
                   kafWordForm.setSent(attributes.getValue(i).trim());
               }
           }
       }
    }//--startElement


    public void endElement(String uri, String localName, String qName)
            throws SAXException {
            if (qName.equals("wf")) {
                kafWordForm.setWf(value.trim());
                kafWordFormList.add(kafWordForm);
            }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {
        value += new String(ch, start, length);
       // System.out.println("value:"+value);
    }

    public KafWordForm getWordForm (String wid) {
        for (int i = 0; i < this.kafWordFormList.size(); i++) {
            KafWordForm kw  = this.kafWordFormList.get(i);
            if (kw.getWid().equals(wid)) {
                return kw;
            }
        }
        return null;
    }

    public int getWordFormRank (String wid) {
        for (int i = 0; i < this.kafWordFormList.size(); i++) {
            KafWordForm kw  = (KafWordForm) this.kafWordFormList.get(i);
            if (kw.getWid().equals(wid)) {
                return i;
            }
        }
        return -1;
    }
    

}