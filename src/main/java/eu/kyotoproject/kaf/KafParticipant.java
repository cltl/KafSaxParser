package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 9/10/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafParticipant extends KafEventComponent{

    private String role;

    public KafParticipant() {
        role = "";
        this.setComponentType("participant");
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement(this.getComponentType());
        if (this.getId() != null)
            root.setAttribute("id", this.getId());

        if (!this.getSynsetId().isEmpty()) {
            root.setAttribute("synsetId", this.getSynsetId());
            root.setAttribute("synsetConfidence", new Double(this.getSynsetConfidence()).toString());
        }

        if (!this.getReferenceType().isEmpty()) {
            root.setAttribute("referenceType", this.getReferenceType());
        }

        if (!role.isEmpty()) {
            root.setAttribute("role", this.role);
        }

        Element span = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            span.appendChild(target);
        }
        root.appendChild(span);
        return root;
    }

    public Element toSrlKafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("role");

        Element role = xmldoc.createElement("Role");
        if (this.getId() != null)
            role.setAttribute("about", this.getId());

        if (this.getRole() != null)
            role.setAttribute("semRole", this.getRole());

        if (!this.getSynsetId().isEmpty()) {
            Element synsetUri = xmldoc.createElement("uri");
            synsetUri.setAttribute("resource", this.getSynsetId());
            synsetUri.setAttribute("confidence", new Double(this.getSynsetConfidence()).toString());
            role.appendChild(synsetUri);
        }

        if (!this.getReferenceType().isEmpty()) {
            Element conceptUri = xmldoc.createElement("uri");
            conceptUri.setAttribute("resource", this.getReferenceType());
            role.appendChild(conceptUri);
        }


        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("resource", this.getSpans().get(i));
            if (this.getTokenString().length()>0) {
                Comment comment = xmldoc.createComment(this.getTokenString());
                target.appendChild(comment);
            }
            role.appendChild(target);
        }
        root.appendChild(role);

        return root;
    }

    public Element toSrlNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("role");

        Element role = xmldoc.createElement("Role");
        if (this.getId() != null)
            role.setAttribute("rdf:about", this.getId());

        if (this.getRole() != null)
            role.setAttribute("naf:semRole", this.getRole());

        if (!this.getSynsetId().isEmpty()) {
            Element synsetUri = xmldoc.createElement("naf:uri");
            synsetUri.setAttribute("rdf:resource", this.getSynsetId());
            synsetUri.setAttribute("confidence", new Double(this.getSynsetConfidence()).toString());
            role.appendChild(synsetUri);
        }

        if (!this.getReferenceType().isEmpty()) {
            Element conceptUri = xmldoc.createElement("naf:uri");
            conceptUri.setAttribute("rdf:resource", this.getReferenceType());
            role.appendChild(conceptUri);
        }


        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("rdf:resource", this.getSpans().get(i));
            if (this.getTokenString().length()>0) {
                Comment comment = xmldoc.createComment(this.getTokenString());
                target.appendChild(comment);
            }
            role.appendChild(target);
        }
        root.appendChild(role);

        return root;
    }
}
