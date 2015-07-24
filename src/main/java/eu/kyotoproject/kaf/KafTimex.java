package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by kyoto on 12/10/13.
 */
public class KafTimex {

    /**
     * <!ATTLIST timex3
     id ID #REQUIRED
     type CDATA #REQUIRED
     beginPoint IDREF #IMPLIED
     endPoint IDREF #IMPLIED
     quant CDATA #IMPLIED
     freq  CDATA #IMPLIED
     functionInDocument CDATA #IMPLIED
     temporalFunction CDATA #IMPLIED
     value CDATA #IMPLIED
     valueFromFunction CDATA #IMPLIED
     mod CDATA #IMPLIED
     anchorTimeID IDREF #IMPLIED>
     */

    /**
     * <timeExpressions>
     <timex3 id="tmx1" type="DURATION">
     <span>
     <target id="w9"/>
     <target id="w10"/>
     </span>
     </timex3>
     <timex3 id="tmx2" type="DURATION">
     <span>
     <target id="w46"/>
     <target id="w47"/>
     </span>
     </timex3>
     </timeExpressions>
     */

    static public final String typeDURATION = "DURATION";
    static public final String typeDATE = "DATE";
    static public final String typeTIME = "TIME";
    static public final String functionInDocumentCreationTime = "CREATION_TIME";

    private String id;
    private String type;
    private String value;
    private String tokenString;
    private String functionInDocument;
    private String anchorTimeID;
    private String beginPoint;
    private String endPoint;

    private ArrayList<String> spans;

    public KafTimex() {
        this.id = "";
        this.type = "";
        this.value = "";
        this.functionInDocument = "";
        this.anchorTimeID = "";
        this.beginPoint = "";
        this.endPoint = "";
        this.tokenString = "";
        this.spans = new ArrayList<String>();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getAnchorTimeID() {
        return anchorTimeID;
    }

    public void setAnchorTimeID(String anchorTimeID) {
        this.anchorTimeID = anchorTimeID;
    }

    public String getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(String beginPoint) {
        this.beginPoint = beginPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getFunctionInDocument() {
        return functionInDocument;
    }

    public void setFunctionInDocument(String functionInDocument) {
        this.functionInDocument = functionInDocument;
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("timex3");

        if (!this.getId().isEmpty())  root.setAttribute("id", this.getId());

        if (!this.getType().isEmpty()) root.setAttribute("type", this.getType());

        if (!this.getValue().isEmpty()) root.setAttribute("value", this.getValue());
        if (!this.getFunctionInDocument().isEmpty()) root.setAttribute("functionInDocument", this.getFunctionInDocument());
        if (!this.getAnchorTimeID().isEmpty()) root.setAttribute("anchorTimeID", this.getAnchorTimeID());
        if (!this.getBeginPoint().isEmpty()) root.setAttribute("beginPoint", this.getBeginPoint());
        if (!this.getEndPoint().isEmpty()) root.setAttribute("endPoint", this.getEndPoint());

        if (this.getTokenString().length()>0) {
            Comment comment = xmldoc.createComment(this.getTokenString());
            root.appendChild(comment);
        }
        if (this.getSpans().size()>0) {
            Element spanElement = xmldoc.createElement("span");
            for (int i = 0; i < this.getSpans().size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.getSpans().get(i));
                spanElement.appendChild(target);
            }
            root.appendChild(spanElement);
        }
        return root;
    }

}
