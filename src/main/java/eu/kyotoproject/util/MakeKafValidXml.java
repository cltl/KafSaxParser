package eu.kyotoproject.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class MakeKafValidXml {

    static public void main (String [] args) {
        String content = "";
        String databaseFolder = args[0];
        String lg = args[1];
        String extensionKafFile = args[2];
        String [] docFolders = FileProcessor.makeFlatDirList(databaseFolder);
        FileOutputStream fixkaffos;
        for (int i = 0; i < docFolders.length; i++) {
            String docFolder = docFolders[i];
            String docId = new File(docFolder).getName();
            try {
                if (Integer.parseInt(docId)==-1) {
                   continue;
                }
            } catch (NumberFormatException e) {
                /// NOT A VALID DOCID
                //System.out.println("docId = " + docId);
                continue;
            }
/*
            if (!docId.equals("287")) {
               continue;
            }
*/
            System.out.println("docFolder = " + docFolder);
            String kafFilePath = docFolder+"/"+docId+extensionKafFile;
            String cacheFolder = docFolder+"/"+"cache_"+lg;
            if (new File (kafFilePath).exists()) {
                //// DOCUMENT LEVEL NOT A WEBSITE
                try {
                    //// FIX KAF FILE AT DOCUMENT LEVEL
                    //// WE RENAME KAF DOCUMENT WITH WRONG EXTENSION
/*
                    if ((kafFilePath.endsWith(extensionKafFile)) &&
                        (!kafFilePath.endsWith(".kaf")))  {
                        String pageName = new File (kafFilePath).getName();
                        String skaf = docFolder+"/"+pageName.substring(0, pageName.indexOf("."))+".kaf";
                        File newKafFile = new File (skaf);
                        new File (kafFilePath).renameTo(newKafFile);
                        kafFilePath = skaf;
                    }
*/
                    // RENAME THE ORIGINAL KAF
                    File kafFile = new File (kafFilePath);
                    File kafFileBu = new File (kafFilePath +".bu");
                    if (kafFileBu.exists()) {
                        content = FileProcessor.ReadFileToString(kafFilePath +".bu");
                    }
                    else {
                        content = FileProcessor.ReadFileToString(kafFilePath);
                        kafFile.renameTo(kafFileBu);
                    }
                    //// WRITE NEW CONTENT TO KAF
                    fixkaffos = new FileOutputStream (kafFilePath);
                    XmlCharacterConversion.fixKafXml(content, fixkaffos);
                    fixkaffos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (new File(cacheFolder).exists()) {
                    //// WE REMOVE PAGE KAFS SINCE THEY NEED TO BE REGENERATED AFTER THIS FIX
                    String [] pageFile = FileProcessor.makeFlatFileList(cacheFolder, ".kaf");
                    for (int j = 0; j < pageFile.length; j++) {
                        String s = pageFile[j];
                        File newKafFile = new File (s);
                        newKafFile.delete();
                    }
                }
            }
            else {
                ///// WEB PAGES
                if (new File(cacheFolder).exists()) {
                    //// WE RENAME WEB PAGES WITH WRONG EXTENSTION
/*
                    String [] pageFile = FileProcessor.makeFlatFileList(cacheFolder);
                    for (int j = 0; j < pageFile.length; j++) {
                        String s = pageFile[j];
                        String pageName = new File (s).getName();
                        if ((pageName.endsWith(extensionKafFile)) &&
                            (!pageName.endsWith(".kaf")))  {
                            //System.out.println("pageName = " + pageName);
                            String skaf = cacheFolder+"/"+pageName.substring(0, pageName.indexOf("."))+".kaf";
                            File newKafFile = new File (skaf);
                            new File (s).renameTo(newKafFile);
                        }
                        else {
                        }
                    }
*/
                    //// Page kaf files
                    String [] pageFile = FileProcessor.makeFlatFileList(cacheFolder, ".kaf");
                    for (int j = 0; j < pageFile.length; j++) {
                        String s = pageFile[j];
                        //// FIX KAF FILE
                        try {
                            File kafFile = new File (s);
                            File kafFileBu = new File (s +".bu");
                            if (kafFileBu.exists()) {
                                content = FileProcessor.ReadFileToString(s +".bu");
                            }
                            else {
                                content = FileProcessor.ReadFileToString(s);
                                kafFile.renameTo(kafFileBu);
                            }

                            fixkaffos = new FileOutputStream (s);
                            XmlCharacterConversion.fixKafXml(content, fixkaffos);
                            fixkaffos.close();
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        }
    }
}
