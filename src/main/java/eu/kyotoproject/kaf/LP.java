package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by piek on 4/4/14.
 */
public class LP {
    private String name, version, timestamp, beginTimestamp, endTimestamp;

    public LP(String name, String version, String timestamp, String beginTimestamp, String endTimestamp)
    {
        this.name = name;
        this.version = version;
        this.timestamp = timestamp;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public void setEndTimeStamp (String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public void setBeginTimeStamp (String beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public Element toXML(Document xmldoc)
    {
        Element lp = xmldoc.createElement("lp");
        if (name != null)
            lp.setAttribute("name", name);
        if (version != null)
            lp.setAttribute("version", version);
        if (timestamp != null)
            lp.setAttribute("timestamp", timestamp);
        if (beginTimestamp != null)
            lp.setAttribute("beginTimestamp", beginTimestamp);
        if (endTimestamp != null)
            lp.setAttribute("endTimestamp", endTimestamp);
        return lp;
    }
}
