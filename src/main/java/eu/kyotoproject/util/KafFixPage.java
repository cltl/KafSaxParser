package eu.kyotoproject.util;

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
public class KafFixPage {

    static public void main (String [] args) {
        //boolean DEBUG = false;
        String databaseFolder = args[0];
        String cache = args[1];
        String [] docFolders = FileProcessor.makeFlatDirList(databaseFolder);
        for (int i = 0; i < docFolders.length; i++) {
            String docFolder = docFolders[i];
            String docCache = docFolder+"/"+cache;
            String [] kafPages = FileProcessor.makeRecursiveFileList(docCache, ".kaf");
            final String strStart = "<?xml version=\"1.0\"?>\n";
            final String strEnd = "</xml>\n";
            for (int j = 0; j < kafPages.length; j++) {
                String kafPage = kafPages[j];
                String kafContent = FileProcessor.ReadFileToString(kafPage, "UTF-8");
                kafContent = kafContent.replaceFirst("lang\"en\"doc\"", "lang=\"en\" doc=\"");
                kafContent = kafContent.replaceFirst("<tunits>\n<tunits>", "<tunits>\n</tunits>");
                kafContent = kafContent.replaceFirst("<terms>\n<chunks>", "</terms>\n<chunks>");
                kafContent = kafContent.replaceFirst("<chunks>\n<deps>", "</chunks>\n<deps>");
                kafContent = kafContent.replaceFirst("<deps>\n<tunits>", "</deps>\n<tunits>");
                kafContent = strStart +kafContent+strEnd;
                try {
                    FileOutputStream fos = new FileOutputStream (kafPage);
                    fos.write(kafContent.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            break;
        }
    }
}
