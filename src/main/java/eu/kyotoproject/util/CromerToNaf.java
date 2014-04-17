package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafWordForm;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by piek on 4/16/14.
 */
public class CromerToNaf {
    /*
    <token t_id="1" sentence="0" number="0">http</token>
<token t_id="2" sentence="0" number="1">:</token>
<token t_id="3" sentence="0" number="2">/</token>
     */


    static public void main (String[] args) {
        String input = "";
        String extension = "";
        String lang = "en";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--input") && args.length > (i + 1)) {
                input = args[i + 1];
            } else if (arg.equalsIgnoreCase("--extension") && args.length > (i + 1)) {
                extension = args[i + 1];
            } else if (arg.equalsIgnoreCase("--language") && args.length > (i + 1)) {
                lang = args[i + 1];
            }
        }

        if (!input.isEmpty()) {
            File file = new File(input);
            if (file.isDirectory()) {
                ArrayList<File> files = FileProcessor.makeRecursiveFileArrayList(input, extension);
                for (int i = 0; i < files.size(); i++) {
                    File nextFile = files.get(i);
                    convert(nextFile, lang);
                }
            }
            else {
                convert (file, lang);
            }
        }
    }

    static public void convert (File pathToCromerExportFile, String lang) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        //String pathToCromerExportFile = args[0];
       // pathToCromerExportFile = "/Users/piek/Desktop/ECBplus1_1plus_accesshollywood.txt.xml";

        try {
            FileInputStream fis = new FileInputStream(pathToCromerExportFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            String rawText = "";
            while (in.ready()&&(inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                if (inputLine.indexOf("</token>") > -1) {
                    try {
                        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(inputLine.getBytes("UTF-8")));
                        if (doc != null) {
                           // System.out.println("inputLine = " + inputLine);
                            Node node = doc.getFirstChild();
                            KafWordForm kafWordForm = new KafWordForm();
                            NamedNodeMap attributes = node.getAttributes();
                            for (int i = 0; i < attributes.getLength(); i++) {
                                Node att = attributes.item(i);
                               // System.out.println("att.getNodeName() = " + att.getNodeName());
                               // System.out.println("att.getN = " + att.getNodeValue());
                                if (att.getNodeName().equalsIgnoreCase("t_id")) {
                                    kafWordForm.setWid(att.getNodeValue());
                                }
                                else if (att.getNodeName().equalsIgnoreCase("sentence")) {
                                    kafWordForm.setSent(att.getNodeValue());
                                }
                            }
                            if (!rawText.isEmpty()) {
                                rawText+=" ";
                            }
                            String wf = node.getTextContent();
                            kafWordForm.setWf(wf);
                            kafWordForm.setCharOffset(new Integer(rawText.length()).toString());
                            kafWordForm.setCharLength(new Integer(wf.length()).toString());
                            rawText += wf;
                            kafSaxParser.kafWordFormList.add(kafWordForm);
                        }
                    } catch (Exception e) {
                        //  e.printStackTrace();
                    }
                }
            }
            kafSaxParser.getKafMetaData().setLanguage(lang);
            kafSaxParser.getKafMetaData().setPublicId(pathToCromerExportFile.getName());
            kafSaxParser.rawText = rawText;
            FileOutputStream fos = new FileOutputStream(pathToCromerExportFile+".naf");
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
