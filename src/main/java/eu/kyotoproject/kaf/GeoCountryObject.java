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
public class GeoCountryObject {
    protected String cId;
    protected ArrayList<KafReference> kafReferences = new ArrayList<KafReference>();
    protected ArrayList<KafSense> externalReferences;

    GeoInfoCountry countryInfo;

    public GeoCountryObject() {
        this.cId = "";
        this.externalReferences = new ArrayList<KafSense>();
        this.kafReferences = new ArrayList<KafReference>();
        this.countryInfo = new GeoInfoCountry();
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }

    public ArrayList<KafReference> getKafReferences() {
        return kafReferences;
    }

    public void setKafReferences(ArrayList<KafReference> kafReferences) {
        this.kafReferences = kafReferences;
    }

    public GeoInfoCountry getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(GeoInfoCountry countryInfo) {
        this.countryInfo = countryInfo;
    }

    public String toKaf () {
        StringBuffer sb = new StringBuffer("\t\t<country lid=\"");
        sb.append(cId);
        sb.append("\">\n\t\t\t<kafReferences>\n");
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t\t\t</kafReferences>\n");
        sb.append(countryInfo.toKaf());
        sb.append("\t</country>\n");
        return sb.toString();

    }
    /**
     <country capital="Copenhagen" continent="EU" countryCode="DK" countryName="Denmark" east="15.1588354110718" fname="country" north="57.7484245300293" population="5484000" south="54.5623817443848" west="8.07560920715332">
     <span id="t221"/>
     </country>
     */
    public String toEventFormat () {
        StringBuffer sb = new StringBuffer(countryInfo.toElement());
        for (KafReference ref : kafReferences)
            sb.append(ref.toKaf());
        sb.append("\t</country>\n");
        return sb.toString();

    }

    public Element toEventXML(Document xmldoc)
    {
  	  Element root = countryInfo.toXML(xmldoc);
      for (KafReference ref : kafReferences) {
          //Element kafReference = ref.toXML(xmldoc);
          Element kafReference = ref.toSpan(xmldoc);
          root.appendChild(kafReference);
      }
  	  return root;
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("country");
 	  root.setAttribute("lid", cId);
  	  Element kafRefs = xmldoc.createElement("kafReferences");
      for (KafReference ref : kafReferences) {
          Element kafReference = ref.toXML(xmldoc);
          kafRefs.appendChild(kafReference);
      }
      root.appendChild(kafRefs);
      Element exRefs = xmldoc.createElement("externalReferences");
      for (KafSense sense : externalReferences) {
          Element exRefXml = sense.toXML(xmldoc);
          exRefs.appendChild(exRefXml);
      }
        root.appendChild(exRefs);
      Element placeXml = this.countryInfo.toXML(xmldoc);
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


        </place>
     */

}
