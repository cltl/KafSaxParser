package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NafFromText {

    static public void main (String[] args) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        // String textFolder = "/Users/piek/Desktop/DigHum-2018/4775434/OBO_XML_7-2/ordinarysAccounts/text";
        //  String nafFolderpath = "/Users/piek/Desktop/DigHum-2018/4775434/OBO_XML_7-2/ordinarysAccounts/naf";

        String textFolder = "/Users/piek/Desktop/DigHum-2018/4775434/OBO_XML_7-2/sessionsPapers/text/18century";
        String nafFolderpath = "/Users/piek/Desktop/DigHum-2018/4775434/OBO_XML_7-2/sessionsPapers/naf18/";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--text-folder") && args.length > (i + 1)) {
                textFolder = args[i + 1];
            } else if (arg.equalsIgnoreCase("--naf-folder") && args.length > (i + 1)) {
                nafFolderpath = args[i + 1];
            }
        }
        File nafFolder = new File(nafFolderpath);
        if (!nafFolder.exists()) {
            nafFolder.mkdir();
        }
        if (nafFolder.exists()) {
            ArrayList<File> txtFiles = FileProcessor.makeFlatFileList(new File(textFolder), ".txt");
            for (int i = 0; i < txtFiles.size(); i++) {
                File txtFile = txtFiles.get(i);
                System.out.println("txtFile.getName() = " + txtFile.getName());
                String contents = null;
                try {
                    contents = new String(Files.readAllBytes(Paths.get(txtFile.getAbsolutePath())));
                    if (contents != null) {
                        String date = txtFile.getName().substring(0, txtFile.getName().indexOf("_"));
                        kafSaxParser.init();
                        kafSaxParser.getKafMetaData().setCreationtime(date);
                        kafSaxParser.getKafMetaData().setUrl(txtFile.getName());
                        kafSaxParser.getKafMetaData().setLanguage("en");
                        kafSaxParser.rawText = contents;
                        String nafFile = nafFolderpath + "/" + txtFile.getName() + ".naf";
                        OutputStream fos = new FileOutputStream(nafFile);
                        kafSaxParser.writeNafToStream(fos);
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println("nafFolder = " + nafFolder.exists());
            System.out.println("nafFolder.getAbsolutePath() = " + nafFolder.getAbsolutePath());
        }
    }

    
}
