package eu.kyotoproject.util;

import java.io.*;

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
public class UtfWriter {

    private static void writeUtf8ToFile(File file,
        boolean append, String data) throws IOException {
      final byte[] utf8_bom = { (byte) 0xEF, (byte) 0xBB,
          (byte) 0xBF };

      boolean exists = file.isFile();
      OutputStream out = new FileOutputStream(file, append);
      try {
        if (!exists) {
          // then this is a new file
          // write the BOM marker
          out.write(utf8_bom);
        }

        Writer writer = new OutputStreamWriter(out, "UTF-8");
        try {
          writer.write(data);
        } finally {
          writer.close();
        }
      } finally {
        out.close();
      }
    }

    public static void main(String[] args) {
      String chinese = "\u4E0A\u6D77";
      File file = new File("chinese.txt");
      boolean append = true;
      try {
        writeUtf8ToFile(file, append, chinese);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }



    
}
