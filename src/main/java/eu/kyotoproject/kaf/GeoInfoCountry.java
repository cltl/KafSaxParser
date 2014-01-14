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
public class GeoInfoCountry {
    private String capital, continent, countryCode, countryName, east, west, north, south, fname, population;


    public GeoInfoCountry() {
        this.capital = "";
        this.continent = "";
        this.countryCode = "";
        this.countryName = "";
        this.east = "";
        this.west = "";
        this.north = "";
        this.south = "";
        this.fname = "";
        this.population = "";
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
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

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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
        StringBuffer str = new StringBuffer("\t\t\t<geoInfo>\n\t\t\t\t<country");
   //   if (geonameId != null)
        //   	str.append(" id=\""+this.geonameId+"\"");
        if (countryCode != null)
            str.append(" countryCode=\"" + this.countryCode + "\"");
        if (countryName != null)
            str.append(" countryName=\"" + this.countryName + "\"");
        if (fname != null)
            str.append(" fname=\"" + fname + "\"");
        if ((population != null))
            str.append(" population=\""+this.population+"\"");
        if (capital != null)
            str.append(" capital=\"" + this.capital + "\"");
        if (continent != null)
            str.append(" continent=\"" + this.continent + "\"");
        if (west != null)
            str.append(" west=\"" + this.west + "\"");
        if (north != null)
            str.append(" north=\""+this.north+"\"");
        if (east != null)
            str.append(" east=\"" + this.east + "\"");
        if (south != null)
            str.append(" south=\"" + this.south + "\"");
        str.append(" />\n\t\t\t</geoInfo>\n");
        return str.toString();
    }

    /**	Returns a start Element of this GeoDatObject
    *
    */
    public String toElement () {
        StringBuffer str = new StringBuffer("<country");
         if (countryCode != null)
            str.append(" countryCode=\"" + this.countryCode + "\"");
        if (countryName != null)
            str.append(" countryName=\"" + this.countryName + "\"");
        if (fname != null)
            str.append(" fname=\"" + fname + "\"");
        if ((population != null))
            str.append(" population=\""+this.population+"\"");
        if (capital != null)
            str.append(" capital=\"" + this.capital + "\"");
        if (continent != null)
            str.append(" continent=\"" + this.continent + "\"");
        if (west != null)
            str.append(" west=\"" + this.west + "\"");
        if (north != null)
            str.append(" north=\""+this.north+"\"");
        if (east != null)
            str.append(" east=\"" + this.east + "\"");
        if (south != null)
            str.append(" south=\"" + this.south + "\"");
        str.append(">\n");
        return str.toString();
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("geoInfo");
        if (capital != null)
            root.setAttribute("capital", capital);
        if (countryCode != null)
            root.setAttribute("countryCode", countryCode);
        if (countryName != null)
            root.setAttribute("countryName", countryName);
        if ((population != null))
            root.setAttribute("population", population);
        if ((continent != null))
            root.setAttribute("continent", continent);
        if (west != null)
            root.setAttribute("west", west);
        if (east != null)
            root.setAttribute("east", east);
        if (north != null)
            root.setAttribute("north", north);
        if (south != null)
            root.setAttribute("south", south);
        if (fname != null)
            root.setAttribute("fname", fname);
  	  return root;
    }

    /*
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
