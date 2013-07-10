package  eu.kyotoproject.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
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
public class QueryUrl {

        static public void getPage (String outputFilePath, String urlString) {
            try {
                 File outFile = new File(outputFilePath);
                 FileOutputStream outWriter = new FileOutputStream(outFile);
                 System.out.println("urlString:"+urlString);
                 System.out.println("outputFilePath:"+outputFilePath);
                 URL url = new URL (urlString);
                 InputStream in = url.openStream();
                 byte [] buffer = new byte[1000];
            //     int fill = 0;
                 while ((/*fill =*/ in.read(buffer))>0) {
    //                 dataString += (String) new String(buffer, 0, fill);
                     outWriter.write(buffer);
                 }
                 outWriter.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        static public String getContent (String urlString) {
            String str = "";
            try {
                 URL url = new URL (urlString);
                 InputStream in = url.openStream();
                 byte [] buffer = new byte[1000];
                 int fill = 0;
                 while ((fill = in.read(buffer))>0) {
                     str += (String) new String(buffer, 0, fill);
                 }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }
}
