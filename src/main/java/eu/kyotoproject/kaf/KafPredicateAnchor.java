package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by piek on 03/07/15.
 */
public class KafPredicateAnchor {

    /**
     * <temporalRelations>
      <!-- anchor pr11 predicate -->
      < predicateAnchor id="an1"
              anchorTime="" beginPoint="tmx5" endPoint="tmx0">
         <span><target id="pr11"></span>
      </predicateAnchor>
     </temporalRelations>
     */

    private String id;
    private String anchorTime;
    private String beginPoint;
    private String endPoint;
    private ArrayList<String> spans;
    private String tokenString;

    public KafPredicateAnchor() {
        this.anchorTime = "";
        this.beginPoint = "";
        this.endPoint = "";
        this.id = "";
        this.tokenString = "";
        this.spans = new ArrayList<String>();
    }

    public String getAnchorTime() {
        return anchorTime;
    }

    public void setAnchorTime(String anchorTime) {
        this.anchorTime = anchorTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Element toNafXML(Document xmldoc)
    {
        /*
        anchorTime="" beginPoint="tmx5" endPoint="tmx0">
         <span><target id="pr11"></span>
         */
        Element root = xmldoc.createElement("predicateAnchor");
        if (!id.isEmpty()) root.setAttribute("is", id);
        if (!beginPoint.isEmpty()) root.setAttribute("beginPoint", beginPoint);
        if (!endPoint.isEmpty()) root.setAttribute("endPoint", endPoint);
        if (!anchorTime.isEmpty()) root.setAttribute("anchorTime", anchorTime);
        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            if (tokenString.trim().length() > 0) {
                Comment comment = xmldoc.createComment(tokenString.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spans.size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spans.get(i));
                span.appendChild(target);
            }
            root.appendChild(span);
        }
        return root;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

/* SPANS are predicates so we need to change this.
    public void setTokenString(KafSaxParser kafSaxParser) {
        this.setTokenString(AddTokensAsCommentsToSpans.getTokenString(kafSaxParser, this.getSpans()));
    }
*/


}
