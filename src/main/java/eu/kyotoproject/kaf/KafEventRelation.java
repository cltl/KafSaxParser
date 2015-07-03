package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by piek on 10/14/14.
 */
public class KafEventRelation {


    /**
    <temporalRelations>
     <!--BEFORE(coevent6, coevent7)-->
     <tlink from="coevent6" to="coevent7" relType="BEFORE" fromType="event" toType="event" id="tlink0"/>
     <!--BEFORE(coevent73, coevent72)-->
     <tlink from="coevent73" to="coevent72" relType="BEFORE" fromType="event" toType="event" id="tlink1"/>



     <causalRelations>
     <!--(coevent3, coevent5)-->
     <clink from="coevent3" to="coevent5" relType="" id="clink0"/>
     <!--(coevent3, coevent6)-->
     <clink from="coevent3" to="coevent6" relType="" id="clink1"/>


     <temporalRelations>
     <!--BEFORE(pr4, tmx0)-->
     <tlink id="tlink0" from="pr4" to="tmx0" fromType="event" toType="timex" relType="BEFORE"/>
     <!--BEFORE(pr13, tmx0)-->
     <tlink id="tlink1" from="pr13" to="tmx0" fromType="event" toType="timex" relType="BEFORE"/>
     <!--BEFORE(pr16, tmx0)-->
     <tlink id="tlink2" from="pr16" to="tmx0" fromType="event" toType="timex" relType="BEFORE"/>
     <!--IS_INCLUDED(pr1, tmx0)-->
     <tlink id="tlink3" from="pr1" to="tmx0" fromType="event" toType="timex" relType="IS_INCLUDED"/>
     <!--BEFORE(pr9, tmx0)-->
     <tlink id="tlink4" from="pr9" to="tmx0" fromType="event" toType="timex" relType="BEFORE"/>
     </temporalRelations>

     */


    private String from;
    private String to;
    private String relType;
    private String id;
    private String fromType;
    private String toType;
    private String tokenString;

    public KafEventRelation() {
        this.from = "";
        this.to = "";
        this.relType = "";
        this.id = "";
        this.fromType = "";
        this.toType = "";
        this.tokenString = "";
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public Element toNafXML(Document xmldoc, String linkType)
    {
        Element root = xmldoc.createElement(linkType);
        if (!id.isEmpty()) root.setAttribute("id", id);
        if (!from.isEmpty()) root.setAttribute("from", from);
        if (!fromType.isEmpty()) root.setAttribute("fromType", fromType);
        if (!to.isEmpty()) root.setAttribute("to", to);
        if (!toType.isEmpty()) root.setAttribute("toType", toType);
        if (!relType.isEmpty()) root.setAttribute("relType", relType);
        if (!tokenString.isEmpty()) {
            Comment comment = xmldoc.createComment(tokenString.trim());
            root.appendChild(comment);
        }
        return root;
    }
}
