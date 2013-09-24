package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/2/12
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class KafOpinion {

    /*
<opinions>
<opinion oid="1">
	<opinion_holder type="SW">
		<span><target id="t_8"/></span>
	</opinion_holder>
	<opinion_target>
		<span><target id="t_9"/></span>
	</opinion_target>
	<opinion_expression>
		<span> <target id="t_10"/> <target id="t_11"/></span>
	</opinion_expression>
</opinion>
<opinion oid="2">
	<opinion_holder type="AC">
		<span><target id="t_1"/></span>
	</opinion_holder>
	<opinion_target>
		<span><target id="t_3"/></span>
	</opinion_target>
	<opinion_expression>
		<span><target id="t_4"/><target id="t_5"/></span>
	</opinion_expression>
	</opinion>
</opinions>
     */

    private String opinionId;
    private String opinionHolderType;
    private String overlap_props;
    private String overlap_ents;
    private String tokenStringOpinionExpression;
    private String tokenStringOpinionHolder;
    private String tokenStringOpinionTarget;

    private KafTermSentiment opinionSentiment;
    private ArrayList<String> spansOpinionHolder;
    private ArrayList<String> spansOpinionTarget;
    private ArrayList<String> spansOpinionExpression;

    public KafOpinion() {
        this.opinionId = "";
        this.opinionHolderType = "";
        this.overlap_ents = "";
        this.overlap_props = "";
        this.tokenStringOpinionExpression = "";
        this.tokenStringOpinionHolder = "";
        this.tokenStringOpinionTarget = "";
        this.opinionSentiment = new KafTermSentiment();
        this.spansOpinionExpression = new ArrayList<String>();
        this.spansOpinionHolder = new ArrayList<String>();
        this.spansOpinionTarget = new ArrayList<String>();
    }

    public String getTokenStringOpinionExpression() {
        return tokenStringOpinionExpression;
    }

    public void setTokenStringOpinionExpression(String tokenStringOpinionExpression) {
        this.tokenStringOpinionExpression = tokenStringOpinionExpression;
    }

    public String getTokenStringOpinionHolder() {
        return tokenStringOpinionHolder;
    }

    public void setTokenStringOpinionHolder(String tokenStringOpinionHolder) {
        this.tokenStringOpinionHolder = tokenStringOpinionHolder;
    }

    public String getTokenStringOpinionTarget() {
        return tokenStringOpinionTarget;
    }

    public void setTokenStringOpinionTarget(String tokenStringOpinionTarget) {
        this.tokenStringOpinionTarget = tokenStringOpinionTarget;
    }

    public String getOverlap_ents() {
        return overlap_ents;
    }

    public void setOverlap_ents(String overlap_ents) {
        this.overlap_ents = overlap_ents;
    }

    public String getOverlap_props() {
        return overlap_props;
    }

    public void setOverlap_props(String overlap_props) {
        this.overlap_props = overlap_props;
    }

    public String getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(String opinionId) {
        this.opinionId = opinionId;
    }

    public String getOpinionHolderType() {
        return opinionHolderType;
    }

    public void setOpinionHolderType(String opinionHolderType) {
        this.opinionHolderType = opinionHolderType;
    }

    public KafTermSentiment getOpinionSentiment() {
        return opinionSentiment;
    }

    public void setOpinionSentiment(KafTermSentiment opinionSentiment) {
        this.opinionSentiment = opinionSentiment;
    }

    public ArrayList<String> getSpansOpinionExpression() {
        return spansOpinionExpression;
    }

    public void setSpansOpinionExpression(ArrayList<String> spansOpinionExpression) {
        this.spansOpinionExpression = spansOpinionExpression;
    }

    public void addSpansOpinionExpression(String span) {
        if (!this.spansOpinionExpression.contains(span)) {
            this.spansOpinionExpression.add(span);
        }
    }

    public ArrayList<String> getSpansOpinionHolder() {
        return spansOpinionHolder;
    }

    public void setSpansOpinionHolder(ArrayList<String> spansOpinionHolder) {
        this.spansOpinionHolder = spansOpinionHolder;
    }

    public void addSpansOpinionHolder(String span) {
        if (!this.spansOpinionHolder.contains(span)) {
            this.spansOpinionHolder.add(span);
        }
    }

    public ArrayList<String> getSpansOpinionTarget() {
        return spansOpinionTarget;
    }

    public void setSpansOpinionTarget(ArrayList<String> spansOpinionTarget) {
        this.spansOpinionTarget = spansOpinionTarget;
    }

    public void addSpansOpinionTarget(String span) {
        if (!this.spansOpinionTarget.contains(span)) {
            this.spansOpinionTarget.add(span);
        }
    }

    /*
   <OPINION oid="1">
       <opinion_holder type="SW/AC" >
       <span><target id="w2.12"/></span>
           <span target id="t1.1"></span>
       </opinion_holder>
       <opinion_target
           <span> <id>t1.6</id><id>t1.7</id><id>t1.8</id></span>
       </opinion_target>
       <opinion_expression
           polarity="negative"
           strength="strong"
           subjectivity="subjectivity"
           sentiment_semantic_type="evaluation"
           sentiment_product_feature=>
           <span> <id>t1.3</id><id>t1.4</id></span>
        </opinion_expression>
    </OPINION>
    */

    public void setTokenStrings (KafSaxParser parser) {
        tokenStringOpinionExpression = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(parser, spansOpinionExpression);
        tokenStringOpinionHolder = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(parser, spansOpinionHolder);
        tokenStringOpinionTarget = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(parser, spansOpinionTarget);
    }

    public String toString () {
        String str = "<opinion";
        str += " oid=\""+opinionId+"\"";
        if (overlap_ents.length()>0) {
            str+= " overlap_ents=\""+overlap_ents+"\"";
        }
        if (overlap_props.length()>0) {
            str+= " overlap_props=\""+overlap_props+"\"";
        }
        str += ">\n";

        str+= "\t<opinion_holder type=\""+opinionHolderType+"\"/>\n";
        if (this.spansOpinionHolder.size()>0) {
            if (tokenStringOpinionHolder.length()>0) {
                str += "<!-- " + tokenStringOpinionHolder+ " -->\n";
            }
            str += "\t\t<span>\n";
            for (int i = 0; i < this.spansOpinionHolder.size(); i++) {
                str += "\t\t\t<target id=\""+spansOpinionHolder.get(i)+"\"/>\n";
            }
            str += "\t\t</span>\n";
        }
        str+= "\t</opinion_holder>\n";

        str+= "\t<opinion_target>\n";
        if (this.spansOpinionTarget.size()>0) {
            if (tokenStringOpinionTarget.length()>0) {
                str += "<!-- " + tokenStringOpinionTarget+ " -->\n";
            }
            str += "\t\t<span>\n";
            for (int i = 0; i < this.spansOpinionTarget.size(); i++) {
                str += "\t\t\t<target id=\""+spansOpinionTarget.get(i)+"\"/>\n";
            }
            str += "\t\t</span>\n";
        }
        str+= "\t</opinion_target>\n";

        str+= "\t<opinion_expression";
        if (!this.opinionSentiment.getPolarity().isEmpty())
            str+= " polarity=\""+this.opinionSentiment.getPolarity()+"\"";
        if (!this.opinionSentiment.getStrength().isEmpty())
            str+= " strength=\""+this.opinionSentiment.getStrength()+"\"";
        if (!this.opinionSentiment.getSubjectivity().isEmpty())
            str+= " subjectivity=\""+this.opinionSentiment.getSubjectivity()+"\"";
        if (!this.opinionSentiment.getSentiment_semantic_type().isEmpty())
            str+= " sentiment_semantic_type=\""+this.opinionSentiment.getSentiment_semantic_type()+"\"";
        if (!this.opinionSentiment.getSentiment_product_feature().isEmpty())
            str+= " sentiment_product_feature=\""+this.opinionSentiment.getSentiment_product_feature()+"\"";
        str +=">\n";
        if (this.spansOpinionExpression.size()>0) {
            if (tokenStringOpinionExpression.length()>0) {
                str += "<!-- " + tokenStringOpinionExpression+ " -->\n";
            }
            str += "\t\t<span>\n";
            for (int i = 0; i < this.spansOpinionExpression.size(); i++) {
                str += "\t\t\t<target id=\""+spansOpinionExpression.get(i)+"\"/>\n";
            }
            str += "\t\t</span>\n";
        }
        str+= "\t</opinion_expression>\n";
        str += "</opinion>\n";
        return str;
    }
    static public String toTableHeaderString () {
        String str = "opinion"
                +"\tpolarity"
                +"\tstrength"
                +"\topinion_expression"
                +"\topinion_holder"
                +"\topinion_target";
        return str;
    }

    public String toTableValueString () {
        String str = this.opinionId;
        str+= "\t"+this.opinionSentiment.getPolarity();
        str+= "\t"+this.opinionSentiment.getStrength();
        str+= "\t" + tokenStringOpinionExpression;
        str+= "\t"+tokenStringOpinionHolder;
        str+= "\t"+tokenStringOpinionTarget;
        return str;
    }

    public String toTableString () {
            String str = "opinion\t"+this.opinionId;
            str+= "\tpolarity\t"+this.opinionSentiment.getPolarity();
            str+= "\tstrength\t"+this.opinionSentiment.getStrength();
            str+= "\topinion_expression\t" + tokenStringOpinionExpression;
            str+= "\topinion_holder\t"+tokenStringOpinionHolder;
            str+= "\topinion_target\t"+tokenStringOpinionTarget;
            return str;
   }

    public Element toXML(Document xmldoc)
    {   Element span = null;
        Element root = xmldoc.createElement("opinion" +
                "");
        root.setAttribute("oid", opinionId);
        if (overlap_ents.length()>0) {
            root.setAttribute("overlap_ents", overlap_ents);
        }
        if (overlap_props.length()>0) {
            root.setAttribute("overlap_props", overlap_props);
        }

        Element opinion_holder = xmldoc.createElement("opinion_holder");
        opinion_holder.setAttribute("type", opinionHolderType);
        if (spansOpinionHolder.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionHolder.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionHolder);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionHolder.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionHolder.get(i));
                span.appendChild(target);
            }
            opinion_holder.appendChild(span);
        }
        root.appendChild(opinion_holder);

        Element opinion_target = xmldoc.createElement("opinion_target");
        if (spansOpinionTarget.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionTarget.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionTarget);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionTarget.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionTarget.get(i));
                span.appendChild(target);
            }
            opinion_target.appendChild(span);
        }
        root.appendChild(opinion_target);

        Element opinion_expression = xmldoc.createElement("opinion_expression");
        if (!this.opinionSentiment.getPolarity().isEmpty())
            opinion_expression.setAttribute("polarity", this.opinionSentiment.getPolarity());
        if (!this.opinionSentiment.getResource().isEmpty())
            opinion_expression.setAttribute("resource", this.opinionSentiment.getResource());
        if (!this.opinionSentiment.getFactual().isEmpty())
            opinion_expression.setAttribute("factual", this.opinionSentiment.getFactual());
        if (!this.opinionSentiment.getStrength().isEmpty())
            opinion_expression.setAttribute("strength", this.opinionSentiment.getStrength());
        if (!this.opinionSentiment.getSubjectivity().isEmpty())
            opinion_expression.setAttribute("subjectivity", this.opinionSentiment.getSubjectivity());
        if (!this.opinionSentiment.getSentiment_semantic_type().isEmpty())
            opinion_expression.setAttribute("semantic_type", this.opinionSentiment.getSentiment_semantic_type());
        if (!this.opinionSentiment.getSentiment_product_feature().isEmpty())
            opinion_expression.setAttribute("sentiment_product_feature", this.opinionSentiment.getSentiment_product_feature());
        if (spansOpinionExpression.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionExpression.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionExpression);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionExpression.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionExpression.get(i));
                span.appendChild(target);
            }
            opinion_expression.appendChild(span);
        }
        root.appendChild(opinion_expression);

        return root;
    }

    public Element toNafXML(Document xmldoc)
    {   Element span = null;
        Element root = xmldoc.createElement("opinion" +
                "");
        root.setAttribute("id", opinionId);
        if (overlap_ents.length()>0) {
            root.setAttribute("overlap_ents", overlap_ents);
        }
        if (overlap_props.length()>0) {
            root.setAttribute("overlap_props", overlap_props);
        }

        Element opinion_holder = xmldoc.createElement("opinion_holder");
        opinion_holder.setAttribute("type", opinionHolderType);
        if (spansOpinionHolder.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionHolder.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionHolder);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionHolder.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionHolder.get(i));
                span.appendChild(target);
            }
            opinion_holder.appendChild(span);
        }
        root.appendChild(opinion_holder);

        Element opinion_target = xmldoc.createElement("opinion_target");
        if (spansOpinionTarget.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionTarget.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionTarget);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionTarget.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionTarget.get(i));
                span.appendChild(target);
            }
            opinion_target.appendChild(span);
        }
        root.appendChild(opinion_target);

        Element opinion_expression = xmldoc.createElement("opinion_expression");
        if (!this.opinionSentiment.getPolarity().isEmpty())
            opinion_expression.setAttribute("polarity", this.opinionSentiment.getPolarity());
        if (!this.opinionSentiment.getResource().isEmpty())
            opinion_expression.setAttribute("resource", this.opinionSentiment.getResource());
        if (!this.opinionSentiment.getFactual().isEmpty())
            opinion_expression.setAttribute("factual", this.opinionSentiment.getFactual());
        if (!this.opinionSentiment.getStrength().isEmpty())
            opinion_expression.setAttribute("strength", this.opinionSentiment.getStrength());
        if (!this.opinionSentiment.getSubjectivity().isEmpty())
            opinion_expression.setAttribute("subjectivity", this.opinionSentiment.getSubjectivity());
        if (!this.opinionSentiment.getSentiment_semantic_type().isEmpty())
            opinion_expression.setAttribute("semantic_type", this.opinionSentiment.getSentiment_semantic_type());
        if (!this.opinionSentiment.getSentiment_product_feature().isEmpty())
            opinion_expression.setAttribute("sentiment_product_feature", this.opinionSentiment.getSentiment_product_feature());
        if (spansOpinionExpression.size()>0) {
            span = xmldoc.createElement("span");
            if (tokenStringOpinionExpression.length()>0) {
                Comment comment = xmldoc.createComment(tokenStringOpinionExpression);
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spansOpinionExpression.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spansOpinionExpression.get(i));
                span.appendChild(target);
            }
            opinion_expression.appendChild(span);
        }
        root.appendChild(opinion_expression);

        return root;
    }

    static public void main (String [] args) {
        KafOpinion kafOpinion = new KafOpinion();
        kafOpinion.setOpinionId("21");
        kafOpinion.setOverlap_ents("e1");
        kafOpinion.setOverlap_props("p1");
        kafOpinion.setOpinionHolderType("SW");
        ArrayList<String> spans = new ArrayList<String>(); spans.add("1"); spans.add("2");
        kafOpinion.setSpansOpinionHolder(spans);
        kafOpinion.setSpansOpinionTarget(spans);
        kafOpinion.setSpansOpinionExpression(spans);
        kafOpinion.getOpinionSentiment().setPolarity("5");
        kafOpinion.getOpinionSentiment().setResource("lex");
        kafOpinion.getOpinionSentiment().setStrength("2");
        kafOpinion.getOpinionSentiment().setSentiment_product_feature("lens");
        kafOpinion.getOpinionSentiment().setSentiment_semantic_type("camera");
        kafOpinion.getOpinionSentiment().setSubjectivity("subjective");
        System.out.println("kafOpinion =\n " + kafOpinion.toString());
    }
}
