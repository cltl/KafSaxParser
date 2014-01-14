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
            root.setAttribute("rid", this.getId());

        if (!this.getSynsetId().isEmpty()) {
            root.setAttribute("synsetId", this.getSynsetId());
            root.setAttribute("synsetConfidence", new Double(this.getSynsetConfidence()).toString());
        }

        if (this.getExternalReferences().size()>0) {
            Element externalRefs = xmldoc.createElement("externalRefs");
            for (int i = 0; i < this.getExternalReferences().size(); i++) {
                KafSense kafSense = this.getExternalReferences().get(i);
                Element kafElement = kafSense.toXML(xmldoc);
                externalRefs.appendChild(kafElement);
            }
            root.appendChild(externalRefs);
        }


        if (!role.isEmpty()) {
            root.setAttribute("role", this.role);
        }

        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            for (int i = 0; i < this.getSpans().size(); i++)
            {
                CorefTarget corefTarget = this.getSpans().get(i);
                span.appendChild(corefTarget.toXML(xmldoc));
            }
            root.appendChild(span);
        }


        return root;
    }

    public Element toSrlKafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("roles");

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


        if (this.getExternalReferences().size()>0) {
            Element externalRefs = xmldoc.createElement("externalRefs");
            for (int i = 0; i < this.getExternalReferences().size(); i++) {
                KafSense kafSense = this.getExternalReferences().get(i);
                Element kafElement = kafSense.toXML(xmldoc);
                externalRefs.appendChild(kafElement);
            }
            role.appendChild(externalRefs);
        }

        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            for (int i = 0; i < this.getSpans().size(); i++)
            {
                CorefTarget corefTarget = this.getSpans().get(i);
                span.appendChild(corefTarget.toXML(xmldoc));
            }
            root.appendChild(span);
        }
        /*
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("resource", this.getSpans().get(i));
            if (this.getTokenString().length()>0) {
                Comment comment = xmldoc.createComment(this.getTokenString());
                target.appendChild(comment);
            }
            role.appendChild(target);
        }*/

        root.appendChild(role);

        return root;
    }


    /*
         <role rid="docId:_pr1r1" semRole="A1">
   <!-- of -->
   <span><target id="docId:_t2"/></span>
     </role>

    */
    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("role");

        if (!getId().isEmpty())
            root.setAttribute("id", this.getId());


/*
        if (!this.getSentenceId().isEmpty()) {
            root.setAttribute("sentence", this.getSentenceId());
        }
*/

        if (!this.getRole().isEmpty())
            root.setAttribute("semRole", this.getRole());

        /*if (!this.getSynsetId().isEmpty()) {
            root.setAttribute("uri", this.getSynsetId());
            root.setAttribute("confidence", new Double(this.getSynsetConfidence()).toString());
        }*/

        if (this.getTokenString().length()>0) {
            Comment comment = xmldoc.createComment(this.getTokenString());
            root.appendChild(comment);
        }

        if (this.getExternalReferences().size()>0) {
            Element externalRefs = xmldoc.createElement("externalReferences");
            for (int i = 0; i < this.getExternalReferences().size(); i++) {
                KafSense kafSense = this.getExternalReferences().get(i);
                Element kafElement = kafSense.toXML(xmldoc);
                externalRefs.appendChild(kafElement);
            }
            root.appendChild(externalRefs);
        }

        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            for (int i = 0; i < this.getSpans().size(); i++)
            {
                CorefTarget corefTarget = this.getSpans().get(i);
                span.appendChild(corefTarget.toXML(xmldoc));
            }
            root.appendChild(span);
        }

/*        Element spanElement = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            spanElement.appendChild(target);
        }
        root.appendChild(spanElement);*/

        return root;
    }


}
