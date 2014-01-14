package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafChunk;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.KafWordForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
public class TextSearch {

    public static String CHUNK = "NP";
    public static String CHUNK2 = "PP";
    static HashMap<String, ArrayList<String>> map;

    static boolean checkPhrase (ArrayList<KafWordForm> kafWordFormList, String [] phrases, int w) {
        if ((phrases.length+w)>kafWordFormList.size()) {
            System.out.println("too long phrases.length = " + phrases.length);
           return false;
        }
        else {
            KafWordForm wf = (KafWordForm) kafWordFormList.get(w);
            if (wf.getWf().equals(phrases[0])) {
                System.out.println("wf = " + wf);
                for (int i = 1; i < phrases.length; i++) {
                    wf = (KafWordForm) kafWordFormList.get(w+i);
                    System.out.println("wf = " + wf.getWf());
                    if (!wf.getWf().equals(phrases[i])) {
                        return false;
                    }
               }
            }
            else {
                //System.out.println("No match for phrases & wf at w = " + phrases[0]+":" +wf.getWf()+":"+w);
                return false;
            }
        }
        return true;
    }

    static String getPhraseWithContext (String file, String [] words) {
           String strNam = "";
           KafSaxParser parser = new KafSaxParser();
           parser.parseFile(file);
           System.out.println("parser.kafWordFormList.size() = " + parser.kafWordFormList.size());
           System.out.println("parser.kafTermList.size() = " + parser.kafTermList.size());
           System.out.println("parser.kafChunkList.size() = " + parser.kafChunkList.size());
           //int npId = 0;
           for (int w=0; w<parser.kafWordFormList.size();w++) {
               KafWordForm wf = (KafWordForm) parser.kafWordFormList.get(w);
               if (checkPhrase(parser.kafWordFormList, words, w)) {
                   System.out.println("We have a match for wf.getWf() = " + wf.getWf());
                   if (parser.WordFormToTerm.containsKey(wf.getWid())) {
                       //// The wordform is alo represented by a term, we get the term ID
                       String termId = (String) parser.WordFormToTerm.get(wf.getWid());
                       KafTerm term = parser.getTerm(termId);
                       if (term==null) {
                           System.out.println("TERM == null");
                       }
                       else {
                           if (parser.TermToChunk.containsKey(termId)) {
                               /// The term is part of a chunk, we get the chunk
                               String bestChunkId = "";
                               int maxSpanSize = 0;
                               ArrayList<String> chunkIds =parser.TermToChunk.get(termId);
                               for (int i = 0; i < chunkIds.size(); i++) {
                                  /// WE ITERATE OF THE CHUNK IDS AND GET THE BEST, THAT IS THE CHUNK WITH THE WIDEST SPAN
                                  String chunkId   = chunkIds.get(i);
                                  KafChunk chunk   = parser.getChunks(chunkId);
                                  if (chunk==null) {
                                     /// Could not find the chunk
                                      System.out.println("CHUNK IS NULL");
                                  }
                                  else {
                                      if (chunk.getSpans().size()>maxSpanSize) {
                                          maxSpanSize=chunk.getSpans().size();
                                          bestChunkId = chunkId;
                                      }
/*
                                         if ((chunk.getPhrase().equals(CHUNK)) ||
                                             (chunk.getPhrase().equals(CHUNK2))) {
                                             if (chunk.getSpans().size()>maxSpanSize) {
                                                 maxSpanSize=chunk.getSpans().size();
                                                 bestChunkId = chunkId;
                                             }
                                         }
                                         else {
                                           /// THIS IS A CHUNK WE DO NOT WANT
                                         }
*/
                                  }
                               }
                               if (bestChunkId.length()>0) {
                                  KafChunk chunk   = parser.getChunks(bestChunkId);
                                  if (chunk==null) {
                                     /// Could not find the chunk
                                     System.out.println("bestChunkId = " + bestChunkId);
                                  }
                                  else {
                                     // if (chunk.getPhrase().equals(CHUNK)) {
                                            /// WE FOUND A VALID CHUNK FOR AN NP
                                          //// NEXT LOOP TAKES THE FIRST CHUNK
                                                //// USE NP ID COUNTER AS IDENTIFIER
                                          //strNam += "\n<NP id=\""+npId+"\" cid=\""+chunk.getCid()+"\""+" head=\""+chunk.getHead()+"\""+" phrase=\""+chunk.getPhrase()+"\">\n";
                                          String headId = chunk.getHead();
                                          System.out.println("headId = " + headId);
                                          String headLemma = "";
                                          String phrase = "";
                                          ArrayList<String> spans = chunk.getSpans();
                                          if (spans.size()>0) {
                                              for (int i = 0; i < spans.size(); i++) {
                                                  String s = spans.get(i);
                                                  System.out.println("s = " + s);
                                                  term = parser.getTerm(s);
                                                  if (term!=null) {
                                                      if (s.equals(headId)) {
                                                         headLemma = term.getLemma();
                                                      }
                                                      ArrayList<String> wfSpan = term.getSpans();
                                                      /// PRE CONTEXT
                                                      if (i==0) {
                                                          strNam += "[";
                                                          if (wfSpan.size()>0) {
                                                             String s1 = wfSpan.get(0);
                                                             int rank = parser.getWordFormRank(s1);
                                                             int start = rank-10;
                                                             if (start<0) {
                                                                 start = 0;
                                                             }
                                                             for (int r=start; r<rank-1;r++) {
                                                                 wf = (KafWordForm) parser.kafWordFormList.get(r);
                                                                 strNam += " "+wf.getWf();
                                                             }
                                                              strNam += "]";
                                                          }
                                                      }
                                                      for (int j = 0; j < wfSpan.size(); j++) {
                                                          String s1 = (String) wfSpan.get(j);
                                                          wf = parser.getWordForm(s1);
                                                          strNam += " "+wf.getWf();
                                                          phrase += " "+ wf.getWf();
                                                      }
                                                      ///// POST CONTEXT
                                                      if (i==spans.size()-1) {
                                                          strNam += " [";
                                                          if (wfSpan.size()>0) {
                                                             String s1 = (String) wfSpan.get(0);
                                                             int rank = parser.getWordFormRank(s1);
                                                             int end = rank+10;
                                                             if (end>parser.kafWordFormList.size()) {
                                                                 end = parser.kafWordFormList.size();
                                                             }
                                                             for (int r=rank+1; r<end;r++) {
                                                                 wf = (KafWordForm) parser.kafWordFormList.get(r);
                                                                 strNam += " "+wf.getWf();
                                                             }
                                                              strNam += "]";
                                                          }
                                                      }

                                                  }
                                              }
                                              if (map.containsKey(headLemma)) {
                                                  ArrayList<String> phrases = map.get(headLemma);
                                                  phrases.add(phrase);
                                                  map.put(headLemma, phrases);
                                              }
                                              else {
                                                  ArrayList<String> phrases = new ArrayList<String>();
                                                  phrases.add(phrase);
                                                  map.put(headLemma, phrases);
                                              }
                                              strNam += "\n";
                                          }
                            /*         }
                                     else {
                                          /// INVALID CHUNK, WE DO NOTHING
                                          System.out.println("WRONG CHUNK TYPE");
                                     }*/
                                  }
                               }
                               else {
                                   //// NONE OF THE CHUNKS HAS A SPAN LARGER THAN 0
                                   System.out.println("NO GOOD CHUNK");
                               }
                           }
                           else {
                               /// Put the word in an RP
                               System.out.println("cannot find termId = " + termId);
                           }
                       }
                   }
                   else {
                      System.out.println("could not find term for wf = " + wf.getWid());
                   }
               }
               else {
                   //System.out.println("not search term wf = " + wf.getWf());
               }
           }
           return strNam;
    }

    static public void main (String[] args) {
        try {
            map = new HashMap<String, ArrayList<String>>();
            String databasePath = args[0];
            String [] words = args[1].split(" ");
            String outFile = databasePath+"/"+args[1]+"_kafsearch"+".out";
            String outFile2 = databasePath+"/"+args[1]+"_kafsearch_table"+".out";
            FileOutputStream fos = new FileOutputStream(outFile);
            FileOutputStream fos2 = new FileOutputStream(outFile2);
            String extension = args[2];
/*
            String [] words = new String[1];
            words[0] = args[1];
*/
            String [] docFolders = FileProcessor.makeFlatDirList(databasePath);
            for (int i = 0; i < docFolders.length; i++) {
                String docFolder = docFolders[i];
                System.out.println("docFolder = " + docFolder);
                String [] kafFiles = FileProcessor.makeFlatFileList(docFolder, extension);
                for (int j = 0; j < kafFiles.length; j++) {
                    String kafFile = kafFiles[j];
                    if (!(new File(kafFile).getName().startsWith("out"))) {
                        System.out.println("kafFile = " + kafFile);
                        String str = getPhraseWithContext(kafFile, words);
                        fos.write(str.getBytes());
                    }
                }
            }

            for (String key : map.keySet()) {
                fos2.write((key+"#\n").getBytes());
                ArrayList<String> phrases = map.get(key);
                for (int i = 0; i < phrases.size(); i++) {
                    String s = (String) phrases.get(i);
                    fos2.write(("\t"+s+"\n").getBytes());
                }
            }
            fos.close();
            fos2.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
