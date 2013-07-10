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
public class XmlCharacterConversion {

    static final String illegalChars = "<>&\"";

    static public void fixKafXml (String content, FileOutputStream fos) {
        try {
            int previousPos = 0;
            int idx_s = content.indexOf(" <text>");
            int idx_e = content.indexOf(" </text>");
            if ((idx_s>-1) && (idx_e>idx_s)) {
                String wordForms = content.substring(idx_s, idx_e);
                fos.write(content.substring(previousPos, idx_s).getBytes());
                cdataWordForms(wordForms, fos);
                //System.out.println("cleanContent = " + cleanContent);
                previousPos = idx_e;
            }
            idx_s = content.indexOf(" <terms>");
            idx_e = content.indexOf(" </terms>");
            if ((idx_s>-1) && (idx_e>idx_s)) {
                String terms = content.substring(idx_s, idx_e);
                fos.write(content.substring(previousPos, idx_s).getBytes());
                convertLemmas(terms, fos);
                //System.out.println("cleanContent = " + cleanContent);
                previousPos = idx_e;
            }
            fos.write(content.substring(previousPos, content.length()).getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    static public void cdataWordForms (String content, FileOutputStream fos) throws IOException {
        //<wf wid="w812" sent="84" para="1">http://www.otevrena-veda.cz/ov/index.php?site=ov_en&p=index</wf>
      //  int wfCount = 0;
        int previousPos = 0;
        int idx_s = -1;
        int idx_e = content.indexOf("</wf>");
            while (idx_e>-1) {
                 idx_s = content.lastIndexOf(">", idx_e);
                 if ((idx_s>-1) && (idx_e>idx_s)) {

                     fos.write(content.substring(previousPos, idx_s+1).getBytes());
                     fos.write("<![CDATA[".getBytes());
                     fos.write(content.substring(idx_s+1, idx_e).getBytes());
                     fos.write("]]>".getBytes());
                     previousPos = idx_e;
                }
                idx_s = content.indexOf("<wf ", idx_e);
                if (idx_s>-1) {
                    idx_e = content.indexOf("</wf>", idx_s);
                }
                else {
                    idx_e = -1;
                }
            }
            fos.write(content.substring(previousPos, content.length()).getBytes());
    }

    static public void convertLemmas(String content, FileOutputStream fos) throws IOException {
        //    <term tid="t1940" type="open" lemma="EU-R & D-project FRAP" pos="n">
       // int lemmaCount = 0;
        int previousPos = 0;
        int idx_s = content.indexOf("lemma=\"");
        int idx_e = -1;
        String lemma = "";
        int attributeLength = 7;
        if (idx_s ==-1) {
            idx_s = content.indexOf("lemma =\"");
            attributeLength = 8;
        }
        if (idx_s ==-1) {
            idx_s = content.indexOf("lemma = \"");
            attributeLength = 9;
        }
        if (idx_s ==-1) {
            idx_s = content.indexOf("lemma= \"");
            attributeLength = 8;
        }
        while (idx_s>-1) {
             idx_e = content.indexOf("\" pos", idx_s+attributeLength);
             if (idx_e>-1) {

                lemma = content.substring(idx_s+attributeLength, idx_e);
                //System.out.println("lemma = " + lemma);
                if (hasIllegalChar(lemma)) {
                    lemma = replaceXmlChar(lemma);
                    fos.write(content.substring(previousPos, idx_s+attributeLength).getBytes());
                    fos.write(lemma.getBytes());
                    previousPos = idx_e;
                }
                // System.out.println("lemma = " + lemma);
            }
            idx_s = content.indexOf("lemma=\"", idx_e);
            attributeLength = 7;
            if (idx_s ==-1) {
                idx_s = content.indexOf("lemma =\"", idx_e);
                attributeLength = 8;
            }
            if (idx_s ==-1) {
                idx_s = content.indexOf("lemma = \"", idx_e);
                attributeLength = 9;
            }
            if (idx_s ==-1) {
                idx_s = content.indexOf("lemma= \"", idx_e);
                attributeLength = 8;
            }
        }
        fos.write(content.substring(previousPos, content.length()).getBytes());
    }

    static boolean hasIllegalChar (String htmlWord) {
        for (int i=0; i<htmlWord.length();i++) {
            char c = htmlWord.charAt(i);
            if (illegalChars.indexOf(c)!=-1) {
               return true;
            }
        }
        return false;
    }

    static public String replaceXmlChar (String word) {
            String htmlWord = word;
            htmlWord = htmlWord.replaceAll("<", "&lt;");
            htmlWord = htmlWord.replaceAll(">", "&gt;");
            htmlWord = htmlWord.replaceAll("&", "&amp;");
            htmlWord = htmlWord.replaceAll("\"", "&quot;");
        return htmlWord;
    }

    static public String replaceXmlCharKyoto (String word) {
            String htmlWord = word;
            //htmlWord = htmlWord.replaceAll("<", "&lt;");
            //htmlWord = htmlWord.replaceAll(">", "&gt;");
            //htmlWord = htmlWord.replaceAll("&", "&amp;");
            //htmlWord = htmlWord.replaceAll("\"", "&quot;");
            htmlWord = htmlWord.replaceAll("<", "[kyoto:lt]");
            htmlWord = htmlWord.replaceAll(">", "[kyoto:gt]");
            htmlWord = htmlWord.replaceAll("&", "[kyoto:amp]");
            htmlWord = htmlWord.replaceAll("\"", "[kyoto:qt]");
/*
            htmlWord = htmlWord.replaceAll("Ç", "&Ccedil;");
            htmlWord = htmlWord.replaceAll("ç", "&ccdil;");
            htmlWord = htmlWord.replaceAll("Ñ", "&Ntilde;");
            htmlWord = htmlWord.replaceAll("ñ", "&ntilde;");
            htmlWord = htmlWord.replaceAll("Þ", "&THORN;");
            htmlWord = htmlWord.replaceAll("þ", "&thorn;");
            htmlWord = htmlWord.replaceAll("Ý", "&Yacute;");
            htmlWord = htmlWord.replaceAll("ý", "&yacute;");
            htmlWord = htmlWord.replaceAll("ÿ", "&yuml;");
            htmlWord = htmlWord.replaceAll("ß", "&szlig;");
            htmlWord = htmlWord.replaceAll("Æ", "&AElig;");
            htmlWord = htmlWord.replaceAll("Á", "&Aacute;");
            htmlWord = htmlWord.replaceAll("Â", "&Acirc;");
            htmlWord = htmlWord.replaceAll("À", "&Agrave;");
            htmlWord = htmlWord.replaceAll("Å", "&Aring;");
            htmlWord = htmlWord.replaceAll("Ã", "&Atilde;");
            htmlWord = htmlWord.replaceAll("Ä", "&Auml;");
            htmlWord = htmlWord.replaceAll("æ", "&aelig;");
            htmlWord = htmlWord.replaceAll("á", "&aacute;");
            htmlWord = htmlWord.replaceAll("â", "&acirc;");
            htmlWord = htmlWord.replaceAll("à", "&agrave;");
            htmlWord = htmlWord.replaceAll("å", "&aring;");
            htmlWord = htmlWord.replaceAll("ã", "&atilde;");
            htmlWord = htmlWord.replaceAll("ä", "&auml;");
            htmlWord = htmlWord.replaceAll("Ð", "&ETH;");
            htmlWord = htmlWord.replaceAll("É", "&Eacute;");
            htmlWord = htmlWord.replaceAll("Ê", "&Ecirc;");
            htmlWord = htmlWord.replaceAll("È", "&Egrave;");
            htmlWord = htmlWord.replaceAll("Ë", "&Euml;");
            htmlWord = htmlWord.replaceAll("ð", "&eth;");
            htmlWord = htmlWord.replaceAll("é", "&eacute;");
            htmlWord = htmlWord.replaceAll("ê", "&ecirc;");
            htmlWord = htmlWord.replaceAll("è", "&egrave;");
            htmlWord = htmlWord.replaceAll("ë", "&euml;");
            htmlWord = htmlWord.replaceAll("Í", "&Iacute;");
            htmlWord = htmlWord.replaceAll("Î", "&Icirc;");
            htmlWord = htmlWord.replaceAll("Ì", "&Igrave;");
            htmlWord = htmlWord.replaceAll("Ï", "&Iuml;");
            htmlWord = htmlWord.replaceAll("í", "&iacute;");
            htmlWord = htmlWord.replaceAll("î", "&icirc;");
            htmlWord = htmlWord.replaceAll("ì", "&igrave;");
            htmlWord = htmlWord.replaceAll("ï", "&iuml;");
            htmlWord = htmlWord.replaceAll("Ó", "&Oacute;");
            htmlWord = htmlWord.replaceAll("Ô", "&Ocirc;");
            htmlWord = htmlWord.replaceAll("Ò", "&Ograve;");
            htmlWord = htmlWord.replaceAll("Ø", "&Oslash;");
            htmlWord = htmlWord.replaceAll("Õ", "&Otilde;");
            htmlWord = htmlWord.replaceAll("Ö", "&Ouml;");
            htmlWord = htmlWord.replaceAll("ó", "&oacute;");
            htmlWord = htmlWord.replaceAll("ô", "&ocirc;");
            htmlWord = htmlWord.replaceAll("ò", "&ograve;");
            htmlWord = htmlWord.replaceAll("ø", "&oslash;");
            htmlWord = htmlWord.replaceAll("õ", "&otilde;");
            htmlWord = htmlWord.replaceAll("ö", "&ouml;");
            htmlWord = htmlWord.replaceAll("Ú", "&Uacute;");
            htmlWord = htmlWord.replaceAll("Û", "&Ucirc;");
            htmlWord = htmlWord.replaceAll("Ù", "&Ugrave;");
            htmlWord = htmlWord.replaceAll("Ü", "&Uuml;");
            htmlWord = htmlWord.replaceAll("ú", "&uacute;");
            htmlWord = htmlWord.replaceAll("û", "&ucirc;");
            htmlWord = htmlWord.replaceAll("ù", "&ugrave;");
            htmlWord = htmlWord.replaceAll("ü", "&uuml;");
            htmlWord = htmlWord.replaceAll("®", "&reg;");
            htmlWord = htmlWord.replaceAll("±", "&plusmn;");
            htmlWord = htmlWord.replaceAll("µ", "&micro;");
            htmlWord = htmlWord.replaceAll("¶", "&para;");
            htmlWord = htmlWord.replaceAll("·", "&middot;");
            htmlWord = htmlWord.replaceAll("¢", "&cent;");
            htmlWord = htmlWord.replaceAll("£", "&pound;");
            htmlWord = htmlWord.replaceAll("¥", "&yen;");
            htmlWord = htmlWord.replaceAll("¼", "&frac14;");
            htmlWord = htmlWord.replaceAll("½", "&frac12;");
            htmlWord = htmlWord.replaceAll("¾", "&frac34;");
            htmlWord = htmlWord.replaceAll("¹", "&sup1;");
            htmlWord = htmlWord.replaceAll("²", "&sup2;");
            htmlWord = htmlWord.replaceAll("³", "&sup3;");
            htmlWord = htmlWord.replaceAll("¿", "&iquest;");
            htmlWord = htmlWord.replaceAll("°", "&deg;");
            htmlWord = htmlWord.replaceAll("¦", "&brvbar;");
            htmlWord = htmlWord.replaceAll("§", "&sect;");
            htmlWord = htmlWord.replaceAll("«", "&laquo;");
            htmlWord = htmlWord.replaceAll("»", "&raquo;");
*/
        return htmlWord;
    }

}
