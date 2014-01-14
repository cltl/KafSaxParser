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
public class WsdJob {

    //./kaf_annotate_senses.pl -m posMapping.txt -M dwn10.bin -W v10_cdb_id.xml.lex KAF/1.semaf >  KAF/1.kaf

    /*
kyoto1/nl/106/cache_nl
10.kaf
11.kaf
12.kaf
13.kaf
14.kaf
15.kaf
16.kaf
17.kaf
18.kaf

     */

    static public void main (String[] args) {
        String filePath = args[0];
        if (new File(filePath).exists() ) {
            try {
                FileOutputStream fos = new FileOutputStream (filePath+"2");
                FileInputStream fis = new FileInputStream(filePath);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                String path = "";
                String str = "";
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        if ((inputLine.indexOf("cache_")!=-1) && (!inputLine.endsWith(".kaf"))) {
                           path = inputLine +"/";
                        }
                        if ((inputLine.endsWith(".kaf")) && (inputLine.indexOf("cache_")==-1)) {
                           str = "./kaf_annotate_senses.pl -m posMapping.txt -M dwn10.bin -W v10_cdb_id.xml.lex ";
                           str += path+inputLine+" "+path+inputLine+".wsd"+"\n";
                           fos.write(str.getBytes());
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
