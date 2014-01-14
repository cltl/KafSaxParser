package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 1:14 PM
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
public class GeoPlaceObject {

    protected String pId;
    protected ArrayList<KafReference> kafReferences = new ArrayList<KafReference>();
    protected ArrayList<KafSense> externalReferences;
    GeoInfoPlace placeInfo;

    public GeoPlaceObject() {
        this.pId = "";
        this.kafReferences = new ArrayList<KafReference>();
        this.externalReferences = new ArrayList<KafSense>();
        this.placeInfo = new GeoInfoPlace();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public ArrayList<KafReference> getKafReferences() {
        return kafReferences;
    }

    public void setKafReferences(ArrayList<KafReference> kafReferences) {
        this.kafReferences = kafReferences;
    }

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }

    public ArrayList<String> getSpans() {
        ArrayList<String> spans = new ArrayList<String>();
        for (int i = 0; i < kafReferences.size(); i++) {
            KafReference kafReference = kafReferences.get(i);
            spans.add(kafReference.getTerm());
        }
        return spans;
    }

    public GeoInfoPlace getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(GeoInfoPlace placeInfo) {
        this.placeInfo = placeInfo;
    }

    public String toKaf () {
        StringBuffer sb = new StringBuffer("\t\t<place lid=\"");
        sb.append(pId);
        sb.append("\">\n\t\t\t<kafReferences>\n");
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t\t\t</kafReferences>\n");
        sb.append(placeInfo.toKaf());
        sb.append("\t</place>\n");
        return sb.toString();

    }

    /**
     <place countryCode="FR" countryName="France" fname="populated place" latitude="50.05" longitude="1.4166667" name="Eu" population="8425" timezone="Europe/Paris">
     <span id="t9419"/>
     </place>
     */
    public String toEventFormat () {
        StringBuffer sb = new StringBuffer(placeInfo.toElement());
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t</place>\n");
        return sb.toString();

    }

    public Element toEventXML(Document xmldoc)
    {
  	  Element root = placeInfo.toXML(xmldoc);
      for (KafReference ref : kafReferences) {
          //Element kafReference = ref.toXML(xmldoc);
          Element kafReference = ref.toSpan(xmldoc);
          root.appendChild(kafReference);
      }
  	  return root;
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("place");
 	  root.setAttribute("lid", pId);
  	  Element kafRefs = xmldoc.createElement("kafReferences");
      for (KafReference ref : kafReferences) {
          Element kafReference = ref.toXML(xmldoc);
          kafRefs.appendChild(kafReference);
      }
      root.appendChild(kafRefs);
      Element placeXml = this.placeInfo.toXML(xmldoc);
      root.appendChild(placeXml);
  	  return root;
    }
    public Element toNafXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("place");
 	  root.setAttribute("id", pId);
  	  Element kafRefs = xmldoc.createElement("kafReferences");
      for (KafReference ref : kafReferences) {
          Element kafReference = ref.toXML(xmldoc);
          kafRefs.appendChild(kafReference);
      }
      root.appendChild(kafRefs);
      Element placeXml = this.placeInfo.toXML(xmldoc);
      root.appendChild(placeXml);
  	  return root;
    }

    /*
        <place lid="l31">
            <kafReferences>
                <kafReference pageId="36">
                    <span id="t12381"/>
                </kafReference>
            </kafReferences>
            <externalReferences>
                <externalRef confidence="0.35" reference="5192726" resource="GeoNames"/>
                <externalRef reference="eng-30-08518505-n" resource="wn30g">
                    <externalRef confidence="1.0" reference="eng-30-08497294-n"  reftype="baseConcept" resource="wn30g"/>
                    <externalRef confidence="1.0" reference="Kyoto#area__country-eng-3.0-08497294-n" reftype="sc_subClassOf" resource="ontology">
                        <externalRef reftype="SubClassOf" reference="Kyoto#area__country-eng-3.0-08497294-n" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="CommonSenseMapping.owl#locative-role"/>
                        <externalRef reftype="SubClassOf" reference="ExtendedDnS.owl#role" status="implied"/>
                        <externalRef reftype="ExtendedDnS.owl#defined-by" reference="ExtendedDnS.owl#description" status="implied"/>
                        <externalRef reftype="ExtendedDnS.owl#specialized-by" reference="ExtendedDnS.owl#role" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="ExtendedDnS.owl#concept" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#part" reference="ExtendedDnS.owl#concept" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="ExtendedDnS.owl#non-agentive-social-object" status="implied"/>
                        <externalRef reftype="ExtendedDnS.owl#refined-by" reference="ExtendedDnS.owl#concept" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="ExtendedDnS.owl#social-object" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="DOLCE-Lite.owl#non-physical-object" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#generically-dependent-on" reference="ExtendedDnS.owl#communication-event" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="DOLCE-Lite.owl#non-physical-endurant" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#generically-dependent-on" reference="DOLCE-Lite.owl#physical-endurant" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#has-quality" reference="DOLCE-Lite.owl#abstract-quality" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="DOLCE-Lite.owl#endurant" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#specific-constant-constituent" reference="DOLCE-Lite.owl#endurant" status="implied"/>
                        <externalRef reftype="DOLCE-Lite.owl#participant-in" reference="DOLCE-Lite.owl#perdurant" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="DOLCE-Lite.owl#spatio-temporal-particular" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="DOLCE-Lite.owl#particular" status="implied"/>
                    </externalRef>
                </externalRef>
            </externalReferences>
            <geoInfo>
                <place countryCode="US" countryName="United States"
                fname="seat of a first-order administrative division"
                latitude="40.2737002"
                longitude="-76.8844179" n
                ame="Harrisburg" population="47840"
                timezone="America/New_York"/>
            </geoInfo>

        </place>
           <geoInfo>
                <country
                capital="Ottawa"
                continent="NA"
                countryCode="CA"
                countryName="Canada"
                east="-52.6362838745117"
                fname="country"
                north="83.1106338500977"
                population="33679000"
                south="41.6759757995605"
                west="-141.000015258789"/>
            </geoInfo>

     */
}
