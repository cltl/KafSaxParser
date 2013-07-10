package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 23-sep-2008
 * Time: 18:01:46
 * To change this template use File | Settings | File Templates.
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class KafDep {
    /*
    <deps><dep from="tid53897.8" to="tid53897.13" rfunc="mod"/>
    <dep from="tid53897.25" to="tid53897.26" rfunc="none"/>
    <dep from="tid53897.32" to="tid53897.33" rfunc="none"/>
     */
    String from;
    String to;
    String rfunc;

    public String toString() {
        String str = "<dep from=\""+from+"\" to=\""+to+"\" rfunc=\""+rfunc+"\"/>\n";
        return str;
    }

    public KafDep() {
        from = "";
        to = "";
        rfunc = "";
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("dep");
 	  root.setAttribute("from", from);
  	  root.setAttribute("to", to);
  	  root.setAttribute("rfunc", rfunc);
  	  return root;
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

    public String getRfunc() {
        return rfunc;
    }

    public void setRfunc(String rfunc) {
        this.rfunc = rfunc;
    }
}