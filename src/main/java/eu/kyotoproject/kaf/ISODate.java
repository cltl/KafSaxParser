package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
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
public class ISODate {

    /*
            <date did="d4">
            <kafReferences>
                <kafReference pageId="0">
                    <span id="t16.9"></span>
                </kafReference>
            </kafReferences>
            <dateInfo dateISO="1948" lemma="1948"></dateInfo>
        </date>
     */
    private String did;
    private DateInfo dateInfo;
    private ArrayList<KafReference> kafReferences = new ArrayList<KafReference>();



    public ISODate()
    {
        this.did = "";
        this.dateInfo = new DateInfo();
    }


    public void setDid(String idx)
    {
        did = idx;
    }

    public String getDid()
    {
        return did;
    }


    public ArrayList<KafReference> getKafReferences() {
        return kafReferences;
    }

    public void setKafReferences(ArrayList<KafReference> kafReferences) {
        this.kafReferences = kafReferences;
    }

    public void addKafReference(KafReference kafReference) {
        this.kafReferences.add(kafReference);
    }

    public void addKafReference(KafTerm term, String cid, String docId, String pageId)
    {
        KafReference ref = new KafReference(docId, pageId, term.getTid());
        ref.setCid(cid);
        kafReferences.add(ref);
    }

    public DateInfo getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(DateInfo dateInfo) {
        this.dateInfo = dateInfo;
    }

    /**	Returns the kaf-representation of this date and its kaf-references, e.g.:
     *	<date did="d1">
     *		<kafReferences>
     *			<kafReference docId="3423" pageId="6">
     *				<span cid="c13" tid="t113" />
     *			</kafReference>
     *		</kafReferences>
     *		<dateInfo lemma="February 11, 2005" iso="2005-11-02" />
     *	</date>
     * @return KAF String
     */
    public String toKaf()
    {
        StringBuffer sb = new StringBuffer("\t\t<date did=\"");
        sb.append(did);
        sb.append("\">\n\t\t\t<kafReferences>\n");
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t\t\t</kafReferences>\n");
        sb.append(dateInfo.toKaf());
        sb.append("\t\t</date>\n");
        return sb.toString();
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("date");
 	  root.setAttribute("did", did);
  	  Element kafRefs = xmldoc.createElement("kafReferences");
      for (KafReference ref : kafReferences) {
          Element kafReference = ref.toXML(xmldoc);
          kafRefs.appendChild(kafReference);
      }
      root.appendChild(kafRefs);
      Element dateInfoXml = this.dateInfo.toXML(xmldoc);
      root.appendChild(dateInfoXml);
  	  return root;
    }

    public Element toEventXML(Document xmldoc)
    {
  	  Element root = this.dateInfo.toXML(xmldoc);
      for (KafReference ref : kafReferences) {
          //Element kafReference = ref.toXML(xmldoc);
          Element kafReference = ref.toSpan(xmldoc);
          root.appendChild(kafReference);
      }
  	  return root;
    }


    /**	Returns the event-representation of this date and its kaf-references, e.g.:       /**
     <dateInfo dateISO="2000" lemma="2000">
     <span id="t181"/>
     </dateInfo>

     *	<date did="d1">
     *		<kafReferences>
     *			<kafReference docId="3423" pageId="6">
     *				<span cid="c13" tid="t113" />
     *			</kafReference>
     *		</kafReferences>
     *		<dateInfo lemma="February 11, 2005" iso="2005-11-02" />
     *	</date>
     * @return KAF String
     */
    public String toEventFormat()
    {

        StringBuffer sb = new StringBuffer("\t\t\t<dateInfo lemma=\"");
        sb.append(dateInfo.getLemma());
        sb.append("\" dateISO=\"" + dateInfo.getDateISO());
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t\t</dateInfo>\n");
        return sb.toString();
    }


}
