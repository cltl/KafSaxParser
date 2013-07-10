package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafWordForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
public class AlignKafFiles {


    static public class Pair {
        int p1 = 0;
        int p2 = 0;

        public Pair () {
            p1 = -1;
            p2 = -1;
        }

        public int getP1() {
            return p1;
        }

        public void setP1(int p1) {
            this.p1 = p1;
        }

        public int getP2() {
            return p2;
        }

        public void setP2(int p2) {
            this.p2 = p2;
        }

        public void switchPos () {
            int p = p1;
            p1 = p2;
            p2 = p;
        }
    }
    static public int countChar (String str, String a) {
        int cnt = 0;
        if (str.length()>0) {
           // System.out.println("str = " + str);
            int pos = str.indexOf(a);
            while (pos>-1) {
                pos = str.indexOf(a, pos+a.length());
              //  System.out.println("pos = " + pos);
                cnt++;
            }
        }
      //  System.out.println("cnt = " + cnt);
        return cnt;
    }

    static public Pair lookForward (ArrayList<KafWordForm> forms1,ArrayList<KafWordForm> forms2, int pos1, int pos2) {
        boolean debug = false;
        int window = 20;
        Pair pair1 = new Pair();
        Pair pair2 = new Pair();
        String page = forms1.get(pos1).getPage();
        for (int i = pos1; i < forms1.size(); i++) {
            KafWordForm wf1 = forms1.get(i);
            if (!wf1.getPage().equals(page)) {
                break;
            }
            if (wf1.getWid().equals("w3157")) {
                debug = true;
            }
            if (debug) {
                System.out.println("wf1.getWf() = " + wf1.getWf());
                System.out.println("wf1.getWid() = " + wf1.getWid());
            }
            for (int j = pos2; j < forms2.size(); j++) {
                KafWordForm wf2 = forms2.get(j);
                if (wf2.getPage().equals(page)) {
                    if (wf1.getWf().equals(wf2.getWf())) {
                        if (debug) {
                           System.out.println("GOTYA!!!");
                           System.out.println("wf2.getWf() = " + wf2.getWf());
                           System.out.println("wf2.getWid() = " + wf2.getWid());
                        }
                       pair1.setP1(i);
                       pair1.setP2(j);
                       break;
                    }
                }
                else {
                    break;
                }
            }
            if (pair1.getP1()>-1) {
                break;
            }
        }
        for (int i = pos2; i < forms2.size(); i++) {
            KafWordForm wf2 = forms2.get(i);
            if (!wf2.getPage().equals(page)) {
               break;
            }
            if (debug) {
                System.out.println("wf2.getWf() = " + wf2.getWf());
                System.out.println("wf2.getWid() = " + wf2.getWid());
            }
            for (int j = pos1; j < forms1.size(); j++) {
                KafWordForm wf1 = forms1.get(j);
                if (!wf1.getPage().equals(page)) {
                    break;
                }
                if (wf1.getWf().equals(wf2.getWf())) {
                    if (debug) {
                       System.out.println("GOTYA!!!");
                       System.out.println("wf1.getWf() = " + wf1.getWf());
                       System.out.println("wf1.getWid() = " + wf1.getWid());
                    }
                    pair2.setP1(j);
                    pair2.setP2(i);
                   break;
                }
            }
            if (pair2.getP1()>-1) {
                break;
            }
        }
        if ((pair1.getP1()>-1) && (pair1.getP2()>-1)) {
            if (pair1.getP2()<=pair2.getP2()) {
                return pair1;
            }
            else {
                if ((pair2.getP1()>-1) && (pair2.getP2()>-1)) {
                    return pair2;
                }
                else {
                    return pair1;
                }
            }
        }
        else {
            return pair2;
        }
    }

    static public void main (String [] args) {
        try {
            String kaf1 = args[0];
            String kaf2 = args[1];
            FileOutputStream fos = new FileOutputStream(new File(kaf1).getParent()+"/"+"table.txt");
            KafSaxParser parser1 = new KafSaxParser();
            KafSaxParser parser2 = new KafSaxParser();
            parser1.parseFile(kaf1);
            parser2.parseFile(kaf2);
            int pos1 = 0;
            int pos2 = 0;
            int nTokens1 = 0;
            int nTokens2 = 0;
            String str = "";
            while ((pos1<parser1.kafWordFormList.size()) &&
                   (pos2<parser2.kafWordFormList.size())) {
                KafWordForm wf1 = parser1.kafWordFormList.get(pos1);
                KafWordForm wf2 = parser2.kafWordFormList.get(pos2);
                if (wf1.getWf().equals(wf2.getWf())) {
                    /// match
                    str = wf1.toSimpleString()+"\t"+wf2.toSimpleString()+"\n";
                    fos.write(str.getBytes());
                    nTokens1++;
                    nTokens2++;
                }
                else {

                    Pair pair = lookForward(parser1.kafWordFormList, parser2.kafWordFormList, pos1, pos2);

                    if (pair.getP1()==-1) {
                            System.out.println("Out of window scope pair");
                            System.out.println("wf1.getWf() = " + wf1.getWf());
                            System.out.println("wf1.getWid() = " + wf1.getWid());
                            System.out.println("wf2.getWf() = " + wf2.getWf());
                            System.out.println("wf2.getWid() = " + wf2.getWid());
                            break;
                    }
                    else {
                        for (int i = pos1; i < pair.getP1(); i++) {
                            KafWordForm next1 = parser1.kafWordFormList.get(i);
                            str = next1.toSimpleString()+"\tVOID"+"\n";
                            fos.write(str.getBytes());
                            nTokens1++;
                        }
                        for (int i = pos1; i < pair.getP2(); i++) {
                            KafWordForm next2 = parser2.kafWordFormList.get(i);
                            str = "VOID\t"+next2.toSimpleString()+"\n";
                            fos.write(str.getBytes());
                            nTokens2++;
                        }
                        KafWordForm next1 = parser1.kafWordFormList.get(pair.getP1());
                        KafWordForm next2 = parser2.kafWordFormList.get(pair.getP2());
                        str = next1.toSimpleString()+"\t"+next2.toSimpleString()+"\n";
                        fos.write(str.getBytes());
                        pos1 = pair.getP1();
                        pos2 = pair.getP2();
                        nTokens1++;
                        nTokens2++;
                    }

/*
                    int forward = 1;
                    String buf1 = "";
                    String buf2 = "";
                    while (((pos1+forward)<parser1.kafWordFormList.size()) &&
                          ((pos2+forward)<parser2.kafWordFormList.size())) {
                        KafWordForm next1 = parser1.kafWordFormList.get(pos1+forward);
                        if (next1.getWf().equals(wf2.getWf())) {
                           /// match
                            nTokens1++; nTokens1++;
                            nTokens2++;
                            nTokens1 += countChar(buf1, "<wf");
                            str = wf1.toSimpleString()+"\tVOID"+"\n";
                            str += buf1+next1.toSimpleString()+"\t"+wf2.toSimpleString()+"\n";
                            fos.write(str.getBytes());
                            pos1+=forward;
                            break;
                        }
                        else {

                            buf1 += next1.toSimpleString()+"\tVOID"+"\n";
                            KafWordForm next2 = parser2.kafWordFormList.get(pos2+forward);
                            if (next2.getWf().equals(wf1.getWf())) {
                                /// match
                                nTokens1++;
                                nTokens2++; nTokens2++;
                                nTokens2 += countChar(buf2, "<wf");
                                str = buf2+"VOID\t"+wf2.toSimpleString()+"\n";
                                str += wf1.toSimpleString()+"\t"+next2.toSimpleString()+"\n";
                                fos.write(str.getBytes());
                                pos2+=forward;
                                break;
                            }
                            else {
                                buf2 += "VOID\t"+next2.toSimpleString()+"\n";
                                forward++;
                                if (forward==3) {
                                    System.out.println("buf1 = " + buf1);
                                    System.out.println("buf2 = " + buf2);
                                    nTokens1+=forward+1;
                                    nTokens2+=forward+1;
                                    str = wf1.toSimpleString()+"\tVOID"+"\n";
                                    str += buf1;
                                    str += buf2+"VOID\t"+wf2.toSimpleString()+"\n";
                                    str += "VOID\t"+next2.toSimpleString()+"\n";
                                    fos.write(str.getBytes());
                                    pos1+=forward;
                                    pos2+=forward;
                                    break;
                                }
                            }
                        }
                    }
*/
                }
                pos1++;
                pos2++;
            }
            fos.close();
            System.out.println("parser1 = " + parser1.kafWordFormList.size());
            System.out.println("nTokens1 = " + nTokens1);
            System.out.println("parser2 = " + parser2.kafWordFormList.size());
            System.out.println("nTokens2 = " + nTokens2);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
