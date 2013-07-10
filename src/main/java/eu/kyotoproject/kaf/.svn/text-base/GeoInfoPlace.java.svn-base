package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 1:01 PM
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
public class GeoInfoPlace {
    private String name, latitude, longitude, timezone, fname, countryCode, countryName, population;


    public GeoInfoPlace() {
        this.name = "";
        this.latitude = "";
        this.longitude = "";
        this.timezone = "";
        this.fname = "";
        this.countryCode = "";
        this.countryName ="";
        this.population = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    /**	Returns a kaf representation of this GeoDatObject
    *
    */
    public String toKaf () {
        StringBuffer str = new StringBuffer("\t\t\t<geoInfo>\n\t\t\t\t<place");
     //   if (geonameId != null)
     //   	str.append(" id=\""+this.geonameId+"\"");
        if (name != null)
            str.append(" name=\""+this.name+"\"");
        if (countryCode != null)
            str.append(" countryCode=\""+this.countryCode+"\"");
        if (countryName != null)
            str.append(" countryName=\"" + this.countryName + "\"");
        if ((population != null))
            str.append(" population=\""+this.population+"\"");
        if (latitude != null)
            str.append(" latitude=\""+this.latitude+"\"");
        if (longitude != null)
            str.append(" longitude=\""+this.longitude+"\"");
        if (fname != null)
            str.append(" fname=\"" + this.fname + "\"");
        if (timezone != null)
            str.append(" timezone=\"" + this.timezone + "\"");
        str.append(" />\n\t\t\t</geoInfo>\n");
        return str.toString();
    }

    /**	Returns the element of this GeoDatObject
    *
    */
    public String toElement () {
        StringBuffer str = new StringBuffer("<place");
        if (name != null)
            str.append(" name=\""+this.name+"\"");
        if (countryCode != null)
            str.append(" countryCode=\""+this.countryCode+"\"");
        if (countryName != null)
            str.append(" countryName=\"" + this.countryName + "\"");
        if ((population != null))
            str.append(" population=\""+this.population+"\"");
        if (latitude != null)
            str.append(" latitude=\""+this.latitude+"\"");
        if (longitude != null)
            str.append(" longitude=\""+this.longitude+"\"");
        if (fname != null)
            str.append(" fname=\"" + this.fname + "\"");
        if (timezone != null)
            str.append(" timezone=\"" + this.timezone + "\"");
        str.append(">\n");
        return str.toString();
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("geoInfo");
        if (name != null)
            root.setAttribute("name", name);
        if (countryCode != null)
            root.setAttribute("countryCode", countryCode);
        if (countryName != null)
            root.setAttribute("countryName", countryName);
        if ((population != null))
            root.setAttribute("population", population);
        if (latitude != null)
            root.setAttribute("latitude", latitude);
        if (longitude != null)
            root.setAttribute("longitude", longitude);
        if (fname != null)
            root.setAttribute("fname", fname);
        if (timezone != null)
            root.setAttribute("timezone", timezone);
  	  return root;
    }

    /*
            <geoInfo>
                <place countryCode="US" countryName="United States"
                fname="seat of a first-order administrative division"
                latitude="40.2737002"
                longitude="-76.8844179"
                name="Harrisburg" population="47840"
                timezone="America/New_York"/>
            </geoInfo>


     */


}
