package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 9/10/13
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafEventComponent {

    private String componentType;
    private String id;
    private String synsetId;
    private double synsetConfidence;
    private String sentenceId;
    private String elementName;
    private String lemma;
    private String pos;
    private ArrayList<CorefTarget> spans;
    private ArrayList<KafSense> externalReferences;
    private String tokenString;

    public KafEventComponent() {
        this.elementName = "";
        this.tokenString = "";
        this.lemma = "";
        this.pos = "";
        this.componentType = "";
        this.id = "";
        this.synsetConfidence = 0;
        this.synsetId = "";
        this.sentenceId = "";
        this.spans = new ArrayList<CorefTarget>();
        this.externalReferences = new ArrayList<KafSense>();
    }


    public void setTokenStrings (KafSaxParser parser) {
        ArrayList<String> corefTokens = AddTokensAsCommentsToSpans.convertCorefTargetsToTokenSpan(parser, spans);
        tokenString = AddTokensAsCommentsToSpans.getTokenString(parser, corefTokens);
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSynsetConfidence() {
        return synsetConfidence;
    }

    public void setSynsetConfidence(double synsetConfidence) {
        this.synsetConfidence = synsetConfidence;
    }

    public String getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(String synsetId) {
        this.synsetId = synsetId;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sid) {
        this.sentenceId = sid;
    }

    public ArrayList<CorefTarget> getSpans() {
        return spans;
    }

    public ArrayList<String> getSpanIds() {
        ArrayList<String> spanIds = new ArrayList<String>();
        for (int i = 0; i < spans.size(); i++) {
            CorefTarget corefTarget = spans.get(i);
            spanIds.add(corefTarget.getId());
        }
        return spanIds;
    }

    public void setSpans(ArrayList<CorefTarget> spans) {
        this.spans = spans;
    }

    public void addSpan(CorefTarget span) {
            this.spans.add(span);
    }

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }

    public void addExternalReferences(KafSense externalReference) {
        this.externalReferences.add(externalReference);
    }
}
