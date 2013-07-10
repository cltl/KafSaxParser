package eu.kyotoproject.kaf;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class KafTermSaxParser extends DefaultHandler {
    String pos = "";
    String value = "";
    public ArrayList<KafTerm> kafTermList;
    ArrayList<String> spans;
    ArrayList<KafSense> senseTags;
    KafTerm kafTerm;
    boolean getSpan = false;
    public String startElement;

    public KafTermSaxParser() {
        init();
        initResults();        
    }

    public KafTermSaxParser(String filePath, String encoding) 
    {
    	parseFile(filePath, encoding);
    }//--c

    public boolean parseFile(String filePath)
    {
    	return parseFile(filePath, null);
    }
    
    public boolean parseFile(File file)
    {
    	try
    	{
    		FileReader reader = new FileReader(file);
            InputSource inp = new InputSource (reader);
    		boolean result = parse(inp, null);
    		reader.close();
    		return result;
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean parseFile(String filePath, String encoding) 
    {
		try 
		{
			InputSource inp = new InputSource (new FileReader(filePath));
			return parse(inp, encoding);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
    }//--c

    public boolean parseFilePos(String filePath, String pos) 
    {
        this.pos = pos;
        return parseFile(filePath, null);
    }//--c

    public boolean parse(InputSource source, String encoding)
    {
    	try {
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            if (encoding != null)
            	source.setEncoding(encoding);
            parser.parse(source, this);
            return true;
        } catch (FactoryConfigurationError factoryConfigurationError) {
            factoryConfigurationError.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
          //  System.out.println("filePath = " + filePath);
         //   System.out.println("XML PARSER ERROR:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean parseContent(String content) 
    {
    	InputSource input = new InputSource(new ByteArrayInputStream(content.getBytes()));
    	return parse(input, null);
    }//--c

    void init() {
         spans = new ArrayList<String>();
         senseTags = new ArrayList<KafSense>();
         kafTerm = new KafTerm();
         getSpan = false;
         startElement = "";
    }

    void initResults() {
        kafTermList = new ArrayList<KafTerm>();
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
        //System.out.println("qName = " + qName);
       value = "";
        if (qName.equalsIgnoreCase("KAF")) {
            //<KAF xml:lang="en" doc="1000">
            startElement = "<KAF";
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equals("xml:lang")) {
                    startElement += " "+name+"=\""+attributes.getValue(i).trim()+"\"";
                }
                else if (name.equals("doc")) {
                    startElement += " "+name+"=\""+attributes.getValue(i).trim()+"\"";
                }
            }
            startElement += ">\n";
        }
        else if (qName.equals("term")) {
           /*
           <term tid="tid53895.2" lemma="en" pos="none" type="none"><span>
<target wid="wid53895.2"/></span></term>
<term tid="tid53895.4" lemma="ben" pos="V" type="open"><span>
<target wid="wid53895.4"/></span></term>
            */
           senseTags = new ArrayList<KafSense>();
           spans = new ArrayList<String>();
           kafTerm = new KafTerm();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equals("tid")) {
                   kafTerm.setTid(attributes.getValue(i).trim());
                   if (kafTerm.getTid().length()>0) {
                   }
                   //System.out.println("tid attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
               }
               else if (name.equals("lemma")) {
                   kafTerm.setLemma(attributes.getValue(i).trim());
                   if (kafTerm.getLemma().length()>0) {
                   }
                   //System.out.println("lemma attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
               }
               else if (name.equals("head")) {
                   kafTerm.setHead(attributes.getValue(i).trim());
               }
               else if (name.equals("pos")) {
                   kafTerm.setPos(attributes.getValue(i).trim());
               }
               else if (name.equals("type")) {
                   kafTerm.setType(attributes.getValue(i).trim());
               }
               else if (name.equals("netype")) {
                   kafTerm.setNetype(attributes.getValue(i).trim());
               }
               else if (name.equals("parent")) {
                   kafTerm.setParent(attributes.getValue(i).trim());
               }
               else if (name.equals("modifier")) {
                   kafTerm.setModifier(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equals("target")) {
           for (int i = 0; i < attributes.getLength(); i++) {
               spans.add(attributes.getValue(i).trim());
           }
       }

       else if (qName.equals("externalRef")) {
            //// new style for sensecodes
            /*
               <externalReferences>
          <externalRef resource="WN-1.7" reference="EN-17-00861095-v" confidence="0.80"/>
          <externalRef resource="WN-1.7" reference="EN-17-00859568-v" confidence="0.20"/>
        </externalReferences>
             */
           KafSense sense = new KafSense();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equals("reference")) {
                   sense.setSensecode(attributes.getValue(i).trim());
                  // System.out.println("sense.getSenseCode() = " + sense.getSenseCode());
               }
               else if (name.equals("confidence")) {
                   double conf = 0;
                   try {
                       conf = Double.parseDouble(attributes.getValue(i).trim());
                   } catch (NumberFormatException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   }
                   sense.setConfidence(conf);
               }
               senseTags.add(sense);
           }
       }
       else if (qName.equals("sense")) {
            //// old style for senseAlt
           KafSense sense = new KafSense();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equals("sensecode")) {
                   sense.setSensecode(attributes.getValue(i).trim());
                  // System.out.println("sense.getSenseCode() = " + sense.getSenseCode());
               }
               else if (name.equals("confidence")) {
                   double conf = 0;
                   try {
                       conf = Double.parseDouble(attributes.getValue(i).trim());
                   } catch (NumberFormatException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   }
                   sense.setConfidence(conf);
               }
               senseTags.add(sense);
           }
       }
       else {
            //////
       }
    }//--startElement


    public void endElement(String uri, String localName, String qName)
            throws SAXException {
            if (qName.equals("term")) {
                if ((pos.length()==0) || ((kafTerm.pos.equals(pos)) || (kafTerm.pos.startsWith(pos)))) {
                    kafTerm.setSenseTags(senseTags);
                    kafTermList.add(kafTerm);
                }
            }
            else if (qName.equals("span")) {
                kafTerm.setSpans(spans);
                spans = new ArrayList<String>();
            }
/*
            else if (qName.equals("sense")) {
                kafTerm.setSenseTags(senseTags);
                senseTags = new ArrayList();
            }
*/
    }
    ///    <senseAlt><sense sensecode="01124768-a" confidence="0.454265"/><sense sensecode="02080577-a" confidence="0.545735"/></senseAlt></term>

    public void characters(char ch[], int start, int length)
            throws SAXException {
        value += new String(ch, start, length);
    }
    
    public String getLanguage()
    {
    	if (startElement == null)
    		return null;
    	int idx = startElement.indexOf("lang=");
    	if (idx < 0)
    		return "en";
    	idx += 6;
    	return startElement.substring(idx, startElement.indexOf("\"", idx));
    }
    
    public List<KafTerm> getKafTerms()
    {
    	return kafTermList;
    }

    public ArrayList<KafTerm> getNamedEntities (String type) {
        ArrayList<KafTerm> entities = new ArrayList<KafTerm>();
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            if (kt.getNetype().equals(type)) {
                entities.add(kt);
            }
        }
        return entities;
    }
    
    public KafTerm getTermId (String lemma) {
        KafTerm term = null;
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            if (kt.getLemma().equalsIgnoreCase(lemma)) {
                return kt;
            }
        }
        return term;
    }

    public KafTerm getTerm (String tid) {
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            if (kt.getTid().equals(tid)) {
                return kt;
            }
        }
        return null;
    }


    public void pageTermSerialization (FileOutputStream fos) {
        ////ktu.spans contains a list of all the wf ids that are part of the ktu
        String str = null;
        try {
            str = "<?xml version=\"1.0\"?>\n" + startElement;
            str += "<terms>\n";
            fos.write(str.getBytes());
            for (int i = 0; i < this.kafTermList.size(); i++) {
                KafTerm kaf  = (KafTerm) kafTermList.get(i);
                str = kaf.toString();
                fos.write(str.getBytes());
            }
            str = "</terms>\n";
            str += "</KAF>\n";
            fos.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void pageEntitySerialization (String netype, FileOutputStream fos) {
        ////ktu.spans contains a list of all the wf ids that are part of the ktu
        String str = null;
        try {
            str = "<?xml version=\"1.0\"?>\n" + startElement;
            str += "<terms>\n";
            fos.write(str.getBytes());
            for (int i = 0; i < this.kafTermList.size(); i++) {
                KafTerm kaf  = (KafTerm) kafTermList.get(i);
                if (kaf.netype.equals(netype)) {
                    str = kaf.toString();
                    fos.write(str.getBytes());
                }
            }
            str = "</terms>\n";
            str += "</KAF>\n";
            fos.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void pageArrayListSerialization (ArrayList<KafTerm> terms, FileOutputStream fos) {
        String str = null;
        try {
            str = "<?xml version=\"1.0\"?>\n" + startElement;
            str += "<terms>\n";
            fos.write(str.getBytes());
            for (int i = 0; i < terms.size(); i++) {
                    KafTerm kaf  = (KafTerm) terms.get(i);
                    str = kaf.toString();
                    fos.write(str.getBytes());
            }
            str = "</terms>\n";
            str += "</KAF>\n";
            fos.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public HashMap<String,ArrayList<KafTerm>> getYearsWithReferences() {
        HashMap<String,ArrayList<KafTerm>> bestData = new HashMap<String,ArrayList<KafTerm>>();

        //// First we create a map from each year to all ocurrences
        //// This represents the ambiguity of the dates
        for (int i = 0; i < kafTermList.size(); i++) {
            KafTerm kafTerm = (KafTerm) kafTermList.get(i);
            if (bestData.containsKey(kafTerm.getParent())) {
                ArrayList<KafTerm> termList = bestData.get(kafTerm.getParent());
                termList.add(kafTerm);
                bestData.put(kafTerm.getParent(), termList);
            }
            else {
                ArrayList<KafTerm> termList = new ArrayList<KafTerm>();
                termList.add(kafTerm);
                bestData.put(kafTerm.getParent(), termList);
            }
        }
        return bestData;
    }



}