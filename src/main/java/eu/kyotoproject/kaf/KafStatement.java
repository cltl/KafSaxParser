package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by piek on 26/06/15.
 */
public class KafStatement {

    /*    <attribution>
    <!-- They said Hilton Hotel Paris was a nightmare. -->
    <statement id="a1">
    <statement_target>
    <span>
    <target id="t3">
    <target id="t4">
    <target id="t5">
    <target id="t6">
    <target id="t7">
    </span>
    </statement_target>
    <statement_source>
    <span>
    <target id="t1">
    </span>
    </statement_source>
    <statement_cue>
    <span>
    <target id="t2">
    </span>
    </statement_cue>
    </statement>
    </attribution>
*/
    private String id;
    private ArrayList<String> statementTargetSpans;
    private ArrayList<String> statementCueSpans;
    private ArrayList<String> statementSourceSpans;
    private String tokenStringCue;
    private String tokenStringTarget;
    private String tokenStringSource;

    public KafStatement() {
        this.id = "";
        this.statementCueSpans = new ArrayList<String>();
        this.statementSourceSpans = new ArrayList<String>();
        this.statementTargetSpans = new ArrayList<String>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getStatementCueSpans() {
        return statementCueSpans;
    }

    public void setStatementCueSpans(ArrayList<String> statementCueSpans) {
        this.statementCueSpans = statementCueSpans;
    }

    public void addStatementCueSpan(String statementCueSpan) {
        if (!this.statementCueSpans.contains(statementCueSpan)) {
            this.statementCueSpans.add(statementCueSpan);
        }
    }


    public ArrayList<String> getStatementSourceSpans() {
        return statementSourceSpans;
    }

    public void setStatementSourceSpans(ArrayList<String> statementSourceSpans) {
        this.statementSourceSpans = statementSourceSpans;
    }

    public void addStatementSourceSpan(String statementSourceSpan) {
        if (!this.statementSourceSpans.contains(statementSourceSpan)) {
            this.statementSourceSpans.add(statementSourceSpan);
        }
    }

    public ArrayList<String> getStatementTargetSpans() {
        return statementTargetSpans;
    }

    public void setStatementTargetSpans(ArrayList<String> statementTargetSpans) {
        this.statementTargetSpans = statementTargetSpans;
    }

    public void addStatementTargetSpan(String statementTargetSpan) {
        if (!this.statementTargetSpans.contains(statementTargetSpan)) {
            this.statementTargetSpans.add(statementTargetSpan);
        }
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("statement");
        root.setAttribute("id", id);
        if (this.getStatementCueSpans().size()>0) {
            Element cue = xmldoc.createElement("statement_cue");
            Element span = xmldoc.createElement("span");
            if (tokenStringCue.trim().length() > 0) {
                Comment comment = xmldoc.createComment(tokenStringCue.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < this.statementCueSpans.size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.statementCueSpans.get(i));
                span.appendChild(target);
            }
            cue.appendChild(span);
            root.appendChild(cue);
        }
        if (this.getStatementSourceSpans().size()>0) {
            Element source = xmldoc.createElement("statement_source");
            Element span = xmldoc.createElement("span");
            if (tokenStringSource.trim().length() > 0) {
                Comment comment = xmldoc.createComment(tokenStringSource.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < this.getStatementSourceSpans().size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.getStatementSourceSpans().get(i));
                span.appendChild(target);
            }
            source.appendChild(span);
            root.appendChild(source);
        }
        if (this.getStatementTargetSpans().size()>0) {
            Element targetStatement = xmldoc.createElement("statement_cue");
            Element span = xmldoc.createElement("span");
            if (tokenStringTarget.trim().length() > 0) {
                Comment comment = xmldoc.createComment(tokenStringTarget.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < this.getStatementTargetSpans().size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.getStatementTargetSpans().get(i));
                span.appendChild(target);
            }
            targetStatement.appendChild(span);
            root.appendChild(targetStatement);
        }
        return root;
    }

    public String getTokenCueString() {
        return tokenStringCue;
    }
    public String getTokenTargetString() {
        return tokenStringTarget;
    }
    public String getTokenSourceString() {
        return tokenStringSource;
    }

    public void setTokenStringCue(String tokenStringCue) {
        this.tokenStringCue = tokenStringCue;
    }

    public void setTokenStringSource(String tokenStringSource) {
        this.tokenStringSource = tokenStringSource;
    }

    public void setTokenStringTarget(String tokenStringTarget) {
        this.tokenStringTarget = tokenStringTarget;
    }

    public void setTokenString(KafSaxParser kafSaxParser) {
        this.setTokenStringCue(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser,
                this.getStatementCueSpans()));
        this.setTokenStringSource(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser,
                this.getStatementSourceSpans()));
        this.setTokenStringTarget(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser,
                this.getStatementTargetSpans()));
    }


}
