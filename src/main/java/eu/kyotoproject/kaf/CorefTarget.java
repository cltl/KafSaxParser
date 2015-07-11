package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/5/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CorefTarget {
        private String id;
        private String head;
        private String lemma;
        private String pos;
        private String tokenString;
        private String sentenceId;

        public CorefTarget() {
            this.head = "";
            this.id = "";
            this.lemma = "";
            this.pos = "";
            this.tokenString = "";
            this.sentenceId = "";
        }


    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public void setLemmaAndPos (KafSaxParser kafSaxParser) {
        KafTerm kafTerm = kafSaxParser.getTerm(this.id);
        if (kafTerm!=null) {
            this.lemma = kafTerm.getLemma();
            this.pos = kafTerm.getPos();
        }
    }
    public void setSentenceId (KafSaxParser kafSaxParser) {
            KafTerm kafTerm = kafSaxParser.getTerm(this.id);
            if (kafTerm!=null) {
                this.sentenceId = kafSaxParser.getSentenceId(kafTerm);
            }
        }

    public String getLemma() {
        return lemma;
    }


    public String getPos() {
        return pos;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Target{" +
                    "head='" + head + '\'' +
                    ", id='" + id + '\'' +
                    ", phrase='" + tokenString + '\'' +
                    ", lemma='" + lemma + '\'' +
                    ", pos='" + pos + '\'' +
                    ", sentence='" + sentenceId + '\'' +
                    '}';
        }

        public Element toXML(Document xmldoc)
        {
            Element root = xmldoc.createElement("target");
            if (id != null)
                root.setAttribute("id", id);
            if (head != null)
                if (!head.isEmpty()) root.setAttribute("head", head);
            return root;
        }


    public boolean insideOneOfSpans (ArrayList<ArrayList<CorefTarget>> setsOfSpans) {
        ArrayList<CorefTarget> singleton = new ArrayList<CorefTarget>();
        singleton.add(this);
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            if (hasIntersection(corefTargets, singleton)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOneOfSpans (ArrayList<ArrayList<CorefTarget>> setsOfSpans) {
        ArrayList<CorefTarget> singleton = new ArrayList<CorefTarget>();
        singleton.add(this);
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            if (hasSpan(corefTargets, singleton)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpan (ArrayList<CorefTarget> corefTargets) {
        ArrayList<CorefTarget> singleton = new ArrayList<CorefTarget>();
        singleton.add(this);
            if (hasSpan(corefTargets, singleton)) {
                return true;
            }
        return false;
    }

   static  public int overlapSetOfSpans (ArrayList<CorefTarget> spansA, ArrayList<CorefTarget> spansB) {
        int nMatches = 0;
        for (int j = 0; j < spansA.size(); j++) {
            CorefTarget corefTarget = spansA.get(j);
            for (int k = 0; k < spansB.size(); k++) {
                CorefTarget target = spansB.get(k);
                if (target.getId().equals(corefTarget.getId())) {
                    nMatches++;
                }
            }
        }
        return nMatches;
    }


   static  public boolean hasSpan (ArrayList<CorefTarget> corefTargets, ArrayList<CorefTarget> spans) {
        int overlap = overlapSetOfSpans(corefTargets, spans);
        if (overlap==corefTargets.size() && (overlap==spans.size())) {
            /// exact match
            return true;
        }
        return false;
    }

    static public boolean hasIntersection (ArrayList<CorefTarget> corefTargets, ArrayList<CorefTarget> spans) {
        int overlap = overlapSetOfSpans(corefTargets, spans);
        if (overlap>0) {
            /// exact match
            return true;
        }
        return false;
    }

}
