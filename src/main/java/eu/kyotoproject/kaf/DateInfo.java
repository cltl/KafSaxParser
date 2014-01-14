package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 *
 *     KafSaxParser is free software: you can redistribute it and/or modify
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
public class DateInfo {
    //            <dateInfo dateISO="1948" lemma="1948"></dateInfo>
    private String dateISO;
    private String lemma;

    public DateInfo() {
        this.dateISO = "";
        this.lemma = "";
    }

    public String getDateISO() {
        return dateISO;
    }

    public void setDateISO(String dateISO) {
        this.dateISO = dateISO;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
    public String toKaf()
    {
        StringBuffer sb = new StringBuffer("\t\t\t<dateInfo lemma=\"");
        sb.append(lemma);
        sb.append("\" dateISO=\"" + dateISO);
        sb.append("\" />\n");
        return sb.toString();
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("dateInfo");
        if (lemma != null)
            root.setAttribute("lemma", lemma);
        if (dateISO != null)
            root.setAttribute("dateISO", dateISO);
  	  return root;
    }


}
