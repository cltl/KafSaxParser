package eu.kyotoproject.kaf;

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
    private String referenceType;
    private String elementName;
    private String lemma;
    private String pos;
    private ArrayList<String> spans;
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
        this.referenceType = "";
        this.spans = new ArrayList<String>();
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

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String type) {
        this.referenceType = type;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpan(String span) {
            this.spans.add(span);
    }

}
