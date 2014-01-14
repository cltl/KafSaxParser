package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/2/12
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class KafTermSentiment {


    /*
    <term tid="t2.10" lemma="nice" pos="G.adj" type="open">
    <sentiment resource="VUA_polarityLexicon_word"
    polarity="positive" strength="average"
    subjectivity="subjective"
    sentiment_semantic_type="behaviour/trait"
    sentiment_product_feature="" />
    <span> <id>t2.10</id></span>
    </term>
     */
    private String resource;
    private String polarity;
    private String strength;
    private String subjectivity;
    private String sentiment_modifier;
    private String factual;
    private String sentiment_semantic_type;
    private String sentiment_product_feature;


    public KafTermSentiment() {
        this.polarity = "";
        this.resource = "";
        this.sentiment_modifier = "";
        this.sentiment_product_feature = "";
        this.sentiment_semantic_type = "";
        this.strength = "";
        this.factual = "";
        this.subjectivity = "";
    }

    public String getSentiment_modifier() {
        return sentiment_modifier;
    }

    public void setSentiment_modifier(String sentiment_modifier) {
        this.sentiment_modifier = sentiment_modifier;
    }

    public String getFactual() {
        return factual;
    }

    public void setFactual(String factual) {
        this.factual = factual;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSentiment_product_feature() {
        return sentiment_product_feature;
    }

    public void setSentiment_product_feature(String sentiment_product_feature) {
        this.sentiment_product_feature = sentiment_product_feature;
    }

    public String getSentiment_semantic_type() {
        return sentiment_semantic_type;
    }

    public void setSentiment_semantic_type(String sentiment_semantic_type) {
        this.sentiment_semantic_type = sentiment_semantic_type;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(String subjectivity) {
        this.subjectivity = subjectivity;
    }

    public boolean hasValue () {
        if (!this.resource.isEmpty())  return true;
        if (!this.polarity.isEmpty())  return true;
        if (!this.strength.isEmpty())  return true;
        if (!this.factual.isEmpty())  return true;
        if (!this.subjectivity.isEmpty())  return true;
        if (!this.resource.isEmpty())  return true;
        if (!this.sentiment_semantic_type.isEmpty())  return true;
        if (!this.sentiment_product_feature.isEmpty())  return true;
        return false;
    }
    /*
   <term tid="t2.10" lemma="nice" pos="G.adj" type="open">
   <sentiment resource="VUA_polarityLexicon_word"
   polarity="positive" strength="average"
   subjectivity="subjective"
   sentiment_semantic_type="behaviour/trait"
   sentiment_product_feature="" />
   <span> <id>t2.10</id></span>

   <span><target id="w2.12"/></span>
   </term>
    */
    public String toString () {
        String str = "<sentiment";
        str += " resource=\""+resource+"\"";
        str += " polarity=\""+polarity+"\"";
        if (strength.length()>0) {
            str += " strength=\""+strength+"\"";
        }
        if (sentiment_modifier.length()>0) {
            str += " sentiment_modifier=\""+sentiment_modifier+"\"";
        }
        if (sentiment_semantic_type.length()>0) {
            str += " semantic_type=\""+sentiment_semantic_type+"\"";
        }
        if (factual.length()>0) {
            str += " factual=\""+factual+"\"";
        }
        if (subjectivity.length()>0) {
            str += " subjectivity=\""+subjectivity+"\"";
        }
        if (sentiment_product_feature.length()>0) {
            str += " sentiment_product_feature=\""+sentiment_product_feature+"\"";
        }

        str += "/>\n";
        return str;
    }

    public String toTripleLabel () {
        String str = "";
        str += polarity;
        str += "#";
        str += strength;
        str += "#";
        str += sentiment_semantic_type;
        str += "#";
        str += factual;
        str += "#";
        str += subjectivity;
        str += "#";
        str += sentiment_modifier;
        str += "#";
        str += sentiment_product_feature;
        return str;
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("sentiment");
        if (polarity.length()>0) {
            root.setAttribute("polarity", polarity);
        }
        if (resource.length()>0) {
            root.setAttribute("resource", resource);
        }
        if (sentiment_semantic_type.length()>0) {
            root.setAttribute("semantic_type", sentiment_semantic_type);
        }
        if (subjectivity.length()>0) {
            root.setAttribute("subjectivity", subjectivity);
        }
        if (sentiment_modifier.length()>0) {
            root.setAttribute("sentiment_modifier", sentiment_modifier);
        }
        if (resource.length()>0) {
            root.setAttribute("resource", resource);
        }
        if (factual.length()>0) {
            root.setAttribute("factual", factual);
        }
        if (sentiment_product_feature.length()>0) {
            root.setAttribute("sentiment_product_feature", sentiment_product_feature);
        }
        return root;
    }

    static public void main (String args[]) {
        KafTermSentiment kafTermSentiment = new KafTermSentiment();
        kafTermSentiment.setPolarity("5");
        kafTermSentiment.setResource("lex");
        kafTermSentiment.setStrength("2");
        kafTermSentiment.setSentiment_modifier("intensifier");
        kafTermSentiment.setFactual("true");
        kafTermSentiment.setSentiment_product_feature("lens");
        kafTermSentiment.setSentiment_semantic_type("camera");
        kafTermSentiment.setSubjectivity("subjective");
        System.out.println("kafTermSentiment = " + kafTermSentiment.toString());
    }
}
