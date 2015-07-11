package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 9/10/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafEvent extends KafEventComponent{


    private ArrayList<KafParticipant> participants;

    public KafEvent() {
        this.participants = new ArrayList<KafParticipant>();
        this.setComponentType("event");
    }

    public ArrayList<KafParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<KafParticipant> participants) {
        this.participants = participants;
    }

    public void addParticipant(KafParticipant participant) {
        this.participants.add(participant);
    }

    public void addTermDataToSpans (KafSaxParser kafSaxParser) {
        for (int i = 0; i < this.getSpans().size(); i++) {
            CorefTarget corefTarget = this.getSpans().get(i);
            corefTarget.setLemmaAndPos(kafSaxParser);
            corefTarget.setSentenceId(kafSaxParser);
        }
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement(this.getComponentType());
        if (!this.getId().isEmpty())
            root.setAttribute("id", this.getId());

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

        if (!this.getLemma().isEmpty()) {
            root.setAttribute("lemma", this.getLemma());
        }

        if (!this.getPos().isEmpty()) {
            root.setAttribute("pos", this.getPos());
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

/*        Element span = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            span.appendChild(target);
        }
        root.appendChild(span);*/

        if (participants.size()>0) {
            for (int i = 0; i < participants.size(); i++) {
                KafParticipant kafParticipant = participants.get(i);
                Element participantElement = kafParticipant.toXML(xmldoc);
                root.appendChild(participantElement);
            }
        }

        return root;
    }

    /*
      <srl>
    <!-- 1 follower.01 : A1[2 of] -->
    <predicate prid="docId:_pr1" uri="URI_OF_follower.01">
      <span><target id="docId:_t1"/></span>
      <role rid="docId:_pr1r1" semRole="A1">
	<!-- of -->
	<span><target id="docId:_t2"/></span>
      </role>
    </predicate>
    <!-- 5 clash.01 : A0[1 followers] A1[6 with] AM-LOC[9 in] -->

     */

    public Element toNafXML(Document xmldoc)
    {

        Element predicate = xmldoc.createElement("predicate");
        if (!this.getId().isEmpty())
            predicate.setAttribute("id", this.getId());
/*
        if (!this.getSynsetId().isEmpty()) {
            predicate.setAttribute("uri", this.getSynsetId());
            predicate.setAttribute("confidence", new Double(this.getSynsetConfidence()).toString());
        }
*/
/*
        if (!this.getSentenceId().isEmpty()) {
            predicate.setAttribute("sentence", this.getSentenceId());
        }
*/

        if (this.getTokenString().length()>0) {
            Comment comment = xmldoc.createComment(this.getTokenString());
            predicate.appendChild(comment);
        }

        if (this.getExternalReferences().size()>0) {
            Element externalRefs = xmldoc.createElement("externalReferences");
            for (int i = 0; i < this.getExternalReferences().size(); i++) {
                KafSense kafSense = this.getExternalReferences().get(i);
                Element kafElement = kafSense.toXML(xmldoc);
                externalRefs.appendChild(kafElement);
            }
            predicate.appendChild(externalRefs);
        }


        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            for (int i = 0; i < this.getSpans().size(); i++)
            {
                CorefTarget corefTarget = this.getSpans().get(i);
                span.appendChild(corefTarget.toXML(xmldoc));
            }
            predicate.appendChild(span);
        }

/*
        Element spanElement = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            spanElement.appendChild(target);
        }
        predicate.appendChild(spanElement);
*/

        if (participants.size()>0) {
            for (int i = 0; i < participants.size(); i++) {
                KafParticipant kafParticipant = participants.get(i);
                Element participantElement = kafParticipant.toNafXML(xmldoc);
                predicate.appendChild(participantElement);
            }
        }
        return predicate;
    }



    /// NAF representation
    /*
        <srl>
      <!-- 5 clash.01 : A0[1 followers] A1[6 with] AM-LOC[9 in] -->
      <Predicate rdf:about="&docId;pr2">
        <naf:uri rdf:resource="URI_OF_clash.01"/>
        <target rdf:resource="&docId;t5"/>
        <role>
          <Role rdf:about="&docId;pr2r1" naf:semRole="A0">
            <!-- followers -->
            <target rdf:resource="&docId;t1"/>
          </Role>
        </role>
        <role>
          <Role rdf:about="&docId;pr2r2" naf:semRole="A1">
            <!-- with -->
            <target rdf:resource="&docId;t6"/>
          </Role>
        </role>
        <role>
          <Role rdf:about="&docId;pr2r3" naf:semRole="AM-LOC">
            <!-- in -->
            <target rdf:resource="&docId;t9"/>
          </Role>
        </role>
      </Predicate>
    </srl>
     */

}
