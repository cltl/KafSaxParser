package eu.kyotoproject.util;

import java.io.File;

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
public class RenameSynthemaPages {

    static public void main (String [] args) {
        String databaseFolder = args[0];
        String lg = args[1];
        String extensionKafFile = args[2];
        String [] docFolders = FileProcessor.makeFlatDirList(databaseFolder);
        for (int i = 0; i < docFolders.length; i++) {
            String docFolder = docFolders[i];
            System.out.println("docFolder = " + docFolder);
            String docId = new File(docFolder).getName();
            String kafDocFile = docFolder+"/"+docId+extensionKafFile;
            if (!new File (kafDocFile).exists()) {
                String cacheFolder = docFolder+"/"+"cache_"+lg;
                if (new File(cacheFolder).exists()) {
                    String outKafFilePath = cacheFolder+"/"+"out_"+docId+".kaf";
                    String syntaxKafFilePath = cacheFolder+"/"+docId+".kaf";
                    String renumberedKafFilePath = cacheFolder+"/"+"1.kaf";
                    String renumberedSyntaxKafFilePath = cacheFolder+"/"+"1.kaf.syntax";
                    File outKafFile = new File(outKafFilePath);
                    File syntaxKafFile = new File(syntaxKafFilePath);
                    if ((outKafFile.exists()) && (syntaxKafFile.exists())) {
                        syntaxKafFile.renameTo(new File(renumberedSyntaxKafFilePath));
                        System.out.println("created syntaxKafFilePath = " + syntaxKafFilePath);
                        outKafFile.renameTo(new File (renumberedKafFilePath));
                        System.out.println("renumberedKafFilePath = " + renumberedKafFilePath);
                    }
                    else {
                        if (outKafFile.exists()) {
                            outKafFile.renameTo(new File (renumberedKafFilePath));
                            System.out.println("No syntax: renumberedKafFilePath = " + renumberedKafFilePath);
                        }
                        if (syntaxKafFile.exists()) {
                            syntaxKafFile.renameTo(new File (renumberedKafFilePath));
                            System.out.println("No sentax: renumberedKafFilePath = " + renumberedKafFilePath);
                        }
                    }
                    //// WE CLEAR THE CACHE
                    String [] pageFile = FileProcessor.makeFlatFileList(cacheFolder);
                    for (int j = 0; j < pageFile.length; j++) {
                        String s = pageFile[j];
                            if ((!s.endsWith(".pdf")) && (!s.endsWith(".kaf")) && (!s.endsWith(".syntax"))) {
                               new File (s).delete();
                            }
                    }
                }
            }
            else {
                //// NOT A WEBSITE
                System.out.println("NOT A WEBSITE");
                String cacheFolder = docFolder+"/"+"cache_"+lg;
                if (new File(cacheFolder).exists()) {
                    String [] pageFile = FileProcessor.makeFlatFileList(cacheFolder);
                    for (int j = 0; j < pageFile.length; j++) {
                            String s = pageFile[j];

                            if ((!s.endsWith(".pdf"))) {
                               new File (s).delete();
                            }
                    }
                }
            }
        }
    }
}
