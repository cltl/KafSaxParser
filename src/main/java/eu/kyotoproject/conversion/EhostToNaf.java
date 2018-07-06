package eu.kyotoproject.conversion;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.util.FileProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by piek on 4/16/14.
 */
public class EhostToNaf {
    /*<?xml version="1.0" encoding="UTF-8"?>
    <annotations textSource="21st-Century-Wire_20170627T181355.txt">
        <annotation>
            <mention id="EHOST_Instance_6872" />
            <annotator id="01">Isa</annotator>
            <span start="563" end="570" />
            <spannedText>the CDC</spannedText>
            <creationDate>Tue Jul 25 18:41:08 CEST 2017</creationDate>
        </annotation>
        <stringSlotMention id="EHOST_Instance_7168">
            <mentionSlot id="Source_type" />
            <stringSlotMentionValue value="Explicit" />
        </stringSlotMention>
     */


    static public void main (String[] args) {
        String eHostInput = "/Users/piek/Desktop/VACCINATION/annotations/saved/";
        String textInput = "/Users/piek/Desktop/VACCINATION/annotations/corpus/";
        ArrayList<File> eHostFiles = FileProcessor.makeRecursiveFileArrayList(new File(eHostInput));
        ArrayList<File> textFiles = FileProcessor.makeRecursiveFileArrayList(new File(textInput));
        for (int i = 0; i < textFiles.size(); i++) {
            File textFile = textFiles.get(i);
            int idx = textFile.getName().lastIndexOf(".");
            String base = textFile.getName().substring(0, idx);
            for (int j = 0; j < eHostFiles.size(); j++) {
                File eHostFile = eHostFiles.get(j);
                if (eHostFile.getName().startsWith(base)) {
                    convert(textFile, eHostFile, "en");
                }
            }
        }
    }

    static String readTextFile (File textFile) {
        String content ="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(textFile));

            String st;
            while ((st = br.readLine()) != null) {
              content += st + "\nl";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    static public void convert (File textFile, File eHostFile, String lang) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        try {
            String rawText = readTextFile(textFile);
            EhostParser ehostParser = new EhostParser();
            ehostParser.parseFile(eHostFile);
            ArrayList<EhostAnnotation> annotations = ehostParser.annotations;
            kafSaxParser.getKafMetaData().setLanguage(lang);
            kafSaxParser.getKafMetaData().setPublicId(eHostFile.getName());
            kafSaxParser.rawText = rawText;
            FileOutputStream fos = new FileOutputStream(eHostFile.getAbsoluteFile()+".naf");
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class EhostAnnotation {
        private String start;
        private String end;
        private String text;

        public EhostAnnotation(String start, String end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    public static class EhostParser extends DefaultHandler {

        private String value = "";
        private String start = "";
        private String end = "";
        private String spannedText = "";
        public ArrayList<EhostAnnotation> annotations = new ArrayList<EhostAnnotation>();
        EhostAnnotation ehostAnnotation = null;

        public boolean parseFile(File file)  {
            try
                    {
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        factory.setValidating(false);
                        SAXParser parser = factory.newSAXParser();
                        parser.parse(file, this);
                        return true;
                    }
                    catch (FactoryConfigurationError factoryConfigurationError)
                    {
                        factoryConfigurationError.printStackTrace();
                    }
                    catch (ParserConfigurationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (SAXException e)
                    {
                        //System.out.println("last value = " + previousvalue);
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                       // e.printStackTrace();
                    }
                    return false;
        }

        public void startElement(String uri, String localName,
                                     String qName, Attributes attributes)
                    throws SAXException {
            value = "";
            if ((qName.equalsIgnoreCase("annotation"))) {
                start = "";
                end = "";
                spannedText = "";
                ehostAnnotation = null;
            }
            else if ((qName.equalsIgnoreCase("span"))) {
                start = attributes.getValue("start");
                end = attributes.getValue("end");
            }
        }


        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if ((qName.equalsIgnoreCase("spannedText"))) {
                spannedText = value;
            }
            else if ((qName.equalsIgnoreCase("annotation"))) {
                ehostAnnotation = new EhostAnnotation(start,end, spannedText);
                annotations.add(ehostAnnotation);
            }

        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            value += new String(ch, start, length);
        }
    }
}
