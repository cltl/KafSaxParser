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

    private String id;
    private String type;
    private String value;
    private String tokenString;
    private ArrayList<String> spans;

    public KafTimex() {
        this.id = "";
        this.type = "";
        this.value = "";
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

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("timex3");

        if (!this.getId().isEmpty())
            root.setAttribute("id", this.getId());

        if (!this.getType().isEmpty())
            root.setAttribute("type", this.getType());

        if (!this.getValue().isEmpty())
            root.setAttribute("value", this.getValue());

        if (this.getTokenString().length()>0) {
            Comment comment = xmldoc.createComment(this.getTokenString());
            root.appendChild(comment);
        }

        Element spanElement = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            spanElement.appendChild(target);
        }
        root.appendChild(spanElement);

        return root;
    }

}
