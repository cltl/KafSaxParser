package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 2/16/12
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafEventISI {
    /*
            <isi-event eventid="33" epistemic_status="" memberOf="" inReporting="" coreference="" event_type="" subevent_of="" start="1984">
            <span>
                <target id="t1120"/>
            </span>
        </isi-event>
     */
    
    private String eventid = "";
    private String epistemic_status = "";
    private String memberOf = "";
    private String inReporting = "";
    private String coreference = "";
    private String event_type = "";
    private String subevent_of = "";
    private String start = "";
    ArrayList<String> spans;


    public KafEventISI() {
        this.eventid = "";
        this.epistemic_status = "";
        this.memberOf = "";
        this.inReporting = "";
        this.coreference = "";
        this.event_type = "";
        this.subevent_of = "";
        this.start = "";
        this.spans = new ArrayList<String>();
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEpistemic_status() {
        return epistemic_status;
    }

    public void setEpistemic_status(String epistemic_status) {
        this.epistemic_status = epistemic_status;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getInReporting() {
        return inReporting;
    }

    public void setInReporting(String inReporting) {
        this.inReporting = inReporting;
    }

    public String getCoreference() {
        return coreference;
    }

    public void setCoreference(String coreference) {
        this.coreference = coreference;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getSubevent_of() {
        return subevent_of;
    }

    public void setSubevent_of(String subevent_of) {
        this.subevent_of = subevent_of;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpans(String span) {
        this.spans.add(span);
    }

    public String toString() {
        String str = "<isi-event eventid=\""+eventid+"\"";
        if (epistemic_status.length()>0) {
            str += " epistemic_status=\""+epistemic_status+"\"";
        }
        if (memberOf.length()>0) {
            str += " memberOf=\""+memberOf+"\"";
        }
        if (inReporting.length()>0) {
            str += " inReporting=\""+inReporting+"\"";
        }
        if (coreference.length()>0) {
            str += " coreference=\""+coreference+"\"";
        }
        if (this.event_type.length()>0) {
            str += " event_type=\""+this.event_type+"\"";
        }
        if (this.subevent_of.length()>0) {
            str += " subevent_of=\""+this.subevent_of+"\"";
        }
        if (this.start.length()>0) {
            str += " start=\""+this.start+"\"";
        }
        str += ">\n";
        str += "<span>\n";
        for (int i = 0; i < this.spans.size(); i++) {
            str += "\t<target id=\""+spans.get(i)+"\"/>\n";
        }
        str += "</span>\n";
        str += "</isi-event>\n";
        return str;
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("isi-event");
        root.setAttribute("eventid", eventid);
        if (epistemic_status.length()>0) {
            root.setAttribute("epistemic_status", epistemic_status);
        }
        if (memberOf.length()>0) {
            root.setAttribute("memberOf", memberOf);
        }
        if (inReporting.length()>0) {
            root.setAttribute("inReporting", inReporting);
        }
        if (coreference.length()>0) {
            root.setAttribute("coreference", coreference);
        }
        if (event_type.length()>0) {
            root.setAttribute("event_type", event_type);
        }
        if (subevent_of.length()>0) {
            root.setAttribute("subevent_of", subevent_of);
        }
        if (start.length()>0) {
            root.setAttribute("start", start);
        }

        Element span = xmldoc.createElement("span");
        for (int i = 0; i < this.spans.size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", spans.get(i));
            span.appendChild(target);
        }
        root.appendChild(span);
        return root;
    }
}
