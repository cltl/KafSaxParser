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
public class CopyPdfDocuments {

    static public void main (String[] args) {
        String sourceDocs = args[0];
        String targetDocs = args[1];
        String [] docFolders = FileProcessor.makeFlatDirList(sourceDocs);
        for (int i = 0; i < docFolders.length; i++) {
            String docFolder = docFolders[i];
            String docName = new File(docFolder).getName();
            String [] docFiles = FileProcessor.makeFlatFileList(docFolder, ".pdf");
            if (docFiles.length>0) {
                String docFolderTargetPath = targetDocs+"/"+docName;
                System.out.println("docFolderTargetPath = " + docFolderTargetPath);
                File docFolderTarget = new File (docFolderTargetPath);
                if (!docFolderTarget.exists()) {
                    docFolderTarget.mkdir();
                }
                File targetHtmlFile = new File(docFolderTargetPath+"/"+docName+".html");
                File targetHtmlFileBu = new File (docFolderTargetPath+"/"+docName+".html.bu");
                if (targetHtmlFile.exists()) {
                    targetHtmlFile.renameTo(targetHtmlFileBu);
                }
                for (int j = 0; j < docFiles.length; j++) {
                    String docFilePath = docFiles[j];
                    Copier.copyFileToFolderAndRename(docFilePath, docFolderTargetPath, docName+".pdf");
                }
            }
        }
    }
}
