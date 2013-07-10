package eu.kyotoproject.util;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
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
public class DetectEncoding {
    private static final byte[] BOM_UTF8 = { (byte) 0xEF,
          (byte) 0xBB, (byte) 0xBF };
      private static final byte[] BOM_UTF16BE = { (byte) 0xFE,
          (byte) 0xFF };
      private static final byte[] BOM_UTF16LE = { (byte) 0xFF,
          (byte) 0xFE };

      private static final Map<byte[], String> ENCODING_SIGNATURES;
      static {
        // not bothering with UTF-32
        ENCODING_SIGNATURES = new HashMap<byte[], String>();
        ENCODING_SIGNATURES.put(BOM_UTF8, "UTF-8");
        ENCODING_SIGNATURES.put(BOM_UTF16BE,
            "UnicodeBigUnmarked");
        ENCODING_SIGNATURES.put(BOM_UTF16LE,
            "UnicodeLittleUnmarked");
        // http://java.sun.com/javase/6/docs/technotes/guides/intl/encoding.doc.html
      }

      public static int afterBom (InputStream in)
              throws IOException {
            for (byte[] bom : ENCODING_SIGNATURES.keySet()) {
              byte[] signature = new byte[bom.length];
              in.mark(bom.length);
              if (in.read(signature) == bom.length
                  && Arrays.equals(bom, signature)) {
                ENCODING_SIGNATURES.get(bom);
                return bom.length;
              }
              in.reset();
            }
            return 0;
      }

      private static String readTextGuessEncoding(InputStream in)
          throws IOException {
        String encoding = null;
        for (byte[] bom : ENCODING_SIGNATURES.keySet()) {
          byte[] signature = new byte[bom.length];
          in.mark(bom.length);
          if (in.read(signature) == bom.length
              && Arrays.equals(bom, signature)) {
            encoding = ENCODING_SIGNATURES.get(bom);
            break;
          }
          in.reset();
        }

        if (encoding == null) {
          // then use system default
          encoding = System.getProperty("file.encoding");
        }

        StringBuilder data = new StringBuilder();
        char[] buffer = new char[1024];
        Reader reader = new InputStreamReader(in, encoding);
        try {
          while (true) {
            int r = reader.read(buffer);
            if (r < 0) {
              break;
            }
            data.append(buffer, 0, r);
          }
        } finally {
          reader.close();
        }
        return data.toString();
      }

    public static byte [] readTextToByteWithoutBom(File f)
        throws IOException {
      byte [] buffer = null;
      String encoding = null;
      InputStream in = new FileInputStream(f);
      for (byte[] bom : ENCODING_SIGNATURES.keySet()) {
        byte[] signature = new byte[bom.length];
        in.mark(bom.length);
        if (in.read(signature) == bom.length
            && Arrays.equals(bom, signature)) {
          encoding = ENCODING_SIGNATURES.get(bom);
          break;
        }
        in.close();
      }
      DataInputStream din = new DataInputStream(new FileInputStream(f));

      if (encoding == null) {
        // then use system default
        encoding = System.getProperty("file.encoding");
        buffer = new byte[(int) f.length()];
        din.readFully(buffer);
        din.close();
      }
      else {
          din.read();
          din.read();
          din.read();
          buffer = new byte[(int) f.length()-3];
          //din.mark(encoding.length());
          din.readFully(buffer);
          //din.readFully(buffer, encoding.length(), (int)f.length());
/*
          din.skip(encoding.length());
          din.readFully(buffer);
*/
          din.close();
      }
        System.out.println("buffer = " + new String(buffer));
      return buffer;
    }

      /**
       * Reads a text file using Windows-style guessing about encoding.
       */
      public static String readText(File file)
          throws IOException {
        InputStream in = new FileInputStream(file);
        try {
          InputStream bufferedIn = new BufferedInputStream(in);
          try {
            return readTextGuessEncoding(bufferedIn);
          } finally {
            bufferedIn.close();
          }
        } finally {
          in.close();
        }
      }

      public static void main(String[] args) {
        File file = new File("chinese.txt");
        try {
          String data = readText(file);
          System.out.println(data);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

}
