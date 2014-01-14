package eu.kyotoproject.util;

import eu.kyotoproject.kaf.*;

import java.io.*;
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
public class ReduceKafToSelectedSentences {

    static public ArrayList<String> readSentenceIds (String fileName) {
        ArrayList<String> ids = new ArrayList<String>();
        if (new File(fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        String [] fields = inputLine.split("\t");
                        String id = fields[fields.length-1];
                       // System.out.println("id = " + id);
                        ids.add(id);
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ids;
    }

    static public void main (String[] args) {
        String kafFile = args[0];
        String sentenceIdFile = args[1];
        KafSaxParser parser = new KafSaxParser();
        parser.parseFile(kafFile);
        ArrayList<String> sentenceIds = readSentenceIds(sentenceIdFile);
        try {
            FileOutputStream fos = new FileOutputStream(kafFile+".reduced.kaf");
            ArrayList<KafWordForm> reducedWfs = new ArrayList<KafWordForm>();
            ArrayList<KafTerm> reducedTerms = new ArrayList<KafTerm>();
            ArrayList<KafChunk> reducedChunks = new ArrayList<KafChunk>();
            ArrayList<KafDep> reducedDeps = new ArrayList<KafDep>();
            for (int i = 0; i < sentenceIds.size(); i++) {
                String s = sentenceIds.get(i);
               // System.out.println("s = " + s);
                ArrayList<String> wfs = parser.SentenceToWord.get(s);
                if (wfs==null) {
                  //  System.out.println("Cannot find words for sentence:"+s);
                    continue;
                }
                for (int j = 0; j < wfs.size(); j++) {
                    String wfId = wfs.get(j);
                    KafWordForm wf = parser.getWordForm(wfId);
                    boolean hasWordForm = false;
                    for (int k = 0; k < reducedWfs.size(); k++) {
                        KafWordForm kafWordForm = reducedWfs.get(k);
                        if (kafWordForm.getWid().equals(wf.getWid())) {
                            hasWordForm = true;
                            break;
                        }
                    }
                    if (!hasWordForm) {
                        reducedWfs.add(wf);
                        if (parser.WordFormToTerm.containsKey(wf.getWid())) {
                            String termId = parser.WordFormToTerm.get(wf.getWid());
                           // System.out.println("termId = " + termId);
                            boolean  hasTerm = false;
                            for (int k = 0; k < reducedTerms.size(); k++) {
                                KafTerm kafTerm = reducedTerms.get(k);
                                if (kafTerm.getTid().equals(termId)) {
                                   hasTerm = true;
                                   break;
                                }
                            }
                            if (!hasTerm) {
                                KafTerm term = parser.getTerm(termId);
                                reducedTerms.add(term);
                                ArrayList<String> cids = parser.TermToChunk.get(termId);
                                if (cids!=null) {
                                    boolean hasChunk = false;
                                    for (int k = 0; k < cids.size(); k++) {
                                        String cid = cids.get(k);
                                        for (int l = 0; l < reducedChunks.size(); l++) {
                                            KafChunk kafChunk = reducedChunks.get(l);
                                            if (kafChunk.getCid().equals(cid)) {
                                                hasChunk = true;
                                                break;
                                            }
                                        }
                                        if (!hasChunk) {
                                            KafChunk chunk = parser.getChunks(cid);
                                            reducedChunks.add(chunk);
                                        }
                                    }
                                }
                                ArrayList<KafDep> deps = parser.TermToDeps.get(termId);
                                boolean hasDep = false;
                                if (deps!=null) {
                                    for (int k = 0; k < deps.size(); k++) {
                                        KafDep kafDep = deps.get(k);
                                        for (int l = 0; l < reducedDeps.size(); l++) {
                                            KafDep dep = reducedDeps.get(l);
                                            if ((kafDep.getFrom().equals(dep.getFrom())) &&
                                                (kafDep.getTo().equals(dep.getTo())) &&
                                                (kafDep.getRfunc().equals(dep.getRfunc()))) {
                                                hasDep = true;
                                                break;
                                            }
                                        }
                                        if (!hasDep) {
                                            reducedDeps.add(kafDep);
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            System.out.println("not term for wf = " + wf);
                        }
                    }
                }
            }
            parser.kafChunkList = reducedChunks;
            parser.kafDepList = reducedDeps;
            parser.kafTermList = reducedTerms;
            parser.kafWordFormList = reducedWfs;
            parser.writeKafToFile(fos);
            //parser.serialization(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
