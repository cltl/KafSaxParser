package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/5/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CorefTarget {
        private String id;
        private double score;
        private String head;
        private String lemma;
        private String subsumer; //lowestcommonsubsumer
        private String pos;
        private String tokenString;

        public CorefTarget() {
            this.head = "";
            this.id = "";
            this.score = 0;
            this.lemma = "";
            this.subsumer = "";
            this.pos = "";
            this.tokenString = "";
        }


    public String getSubsumer() {
        return subsumer;
    }

    public void setSubsumer(String subsumer) {
        this.subsumer = subsumer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setLemmaAndPos (KafSaxParser kafSaxParser) {
        KafTerm kafTerm = kafSaxParser.getTerm(this.id);
        if (kafTerm!=null) {
            this.lemma = kafTerm.getLemma();
            this.pos = kafTerm.getPos();
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
                    ", score='" + score + '\'' +
                    '}';
        }

        public Element toXML(Document xmldoc)
        {
            Element root = xmldoc.createElement("target");
            if (id != null)
                root.setAttribute("id", id);
           /* if (score > 0)
                root.setAttribute("score", new Double(score).toString());*/
            if (head != null)
                if (!head.isEmpty()) root.setAttribute("head", head);
            return root;
        }

}
