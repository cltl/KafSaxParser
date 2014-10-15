package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by piek on 4/4/14.
 */
public class LP {
    private String hostname, name, version, timestamp, beginTimestamp, endTimestamp;

    public LP(String name, String version, String timestamp, String beginTimestamp, String endTimestamp, String hostname)
    {
        this.name = name;
        this.version = version;
        this.timestamp = timestamp;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(String beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
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
        if (hostname != null)
            lp.setAttribute("hostname", hostname);
        return lp;
    }
}
