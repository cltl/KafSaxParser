package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by piek on 30/11/2016.
 */
public class KafEmotion implements Serializable {

    private int count;
    private String emotion;
    private String strength;
    private ArrayList<String> spans;
    private String tokenString;

    public KafEmotion() {
        this.count = 0;
        this.emotion = "";
        this.strength = "";
        this.spans = new ArrayList<String>();
        this.tokenString = "";
    }

    public void setTokenString (KafSaxParser kafSaxParser) {
        this.tokenString = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser,
                this.getSpans());
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpan(String span) {
        if (!spans.contains(span)) this.spans.add(span);
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }


    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("emotion");
        if (count>0) root.setAttribute("score", new Integer(count).toString());
        if (!emotion.isEmpty()) root.setAttribute("emotion", emotion);
        if (!strength.isEmpty()) root.setAttribute("strength", strength.toString());


        if (spans.size()>0) {
            Element span = xmldoc.createElement("span");
            if (!tokenString.isEmpty()) {
                Comment comment = xmldoc.createComment(tokenString.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < spans.size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.spans.get(i));
                span.appendChild(target);
            }
            root.appendChild(span);
        }
        return root;
    }
}
