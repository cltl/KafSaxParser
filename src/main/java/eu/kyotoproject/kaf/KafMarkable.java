package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by piek on 26/06/15.
 */
public class KafMarkable {
    /**
     * \section{Markable layer}
     \label{sec:markable-layer}

     NAF includes a ``markable'' layer, whose purpose is to group tokens and
     attach information to them. The layer is repsesented under a
     \texttt{<markables>} element. This element has the following attribute:
     \begin{itemize}
     \item \texttt{source}: the source (or purpose) of the layer.
     \end{itemize}

     There can be many \texttt{<markable>} layers within NAF, and ideally each
     one will have a different \texttt{source} attribute.

     The \texttt{<markable>} layer comprises one or more \texttt{<mark>}
     elements, one per token group. The \texttt{<mark>} element has one mandatory
     attribute:
     \begin{itemize}
     \item \texttt{id} (\textbf{required}): unique identifier starting with the
     prefix ``m''.
     \end{itemize}

     Apart from this, \texttt{<mark>} element has the same attributes and
     sub-elements as the \texttt{<term>} element described in section
     \ref{sec:terms}\footnote{Except that \texttt{<mark>} elements do not contain
     component sub-elements.}.

     Below is an example of a markable layer produced by a tool which links token
     groups to DBpedia entities:

     \begin{Verbatim}[fontsize=\small]
     <markables source="DBpedia">
     <!--Football Championship Subdivision-->
     <mark id="m42" lemma="Football Championship Subdivision">
     <span>
     <target id="w128"/>
     <target id="w129"/>
     <target id="w130"/>
     </span>
     <externalReferences>
     <externalRef resource="spotlight"
     reference="http://dbpedia.org/resource/Division_I_(NCAA)"
     confidence="1.0"/>
     </externalReferences>
     </mark>
     </markables>
     \end{Verbatim}


     <markables>
     <!--Football Championship Subdivision-->
     <mark id="m42" lemma="Football Championship Subdivision" source="DBpedia">
     <span>
     <target id="w20"/>
     <target id="w21"/>
     <target id="w22"/>
     </span>
     <externalReferences>
     <externalRef resource="spotlight"
     reference="http://dbpedia.org/resource/Division_I_(NCAA)"
     confidence="1.0"/>
     </externalReferences>
     </mark>
     </markables>
     */

    private String id;
    private String lemma;
    private String source;
    private String tokenString = "";
    private ArrayList<String> spans;
    private ArrayList<KafSense> externalReferences;

    public KafMarkable() {
        this.externalReferences = new ArrayList<KafSense>();
        this.id = "";
        this.lemma = "";
        this.source = "";
        this.spans = new ArrayList<String>();
        this.tokenString = "";
    }

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }
     public void addExternalReferences(KafSense externalReference) {
            this.externalReferences.add(externalReference);
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpans(String span) {
        this.spans.add(span);
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("mark");
        root.setAttribute("id", id);
        if (!lemma.isEmpty()) root.setAttribute("lemma", lemma);
        if (!source.isEmpty()) root.setAttribute("source", source);
        if (this.getSpans().size()>0) {
            Element span = xmldoc.createElement("span");
            if (tokenString.trim().length() > 0) {
                Comment comment = xmldoc.createComment(tokenString.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < this.spans.size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spans.get(i));
                span.appendChild(target);
            }
            root.appendChild(span);
        }
        if (this.getExternalReferences().size()>0) {
            Element externalReferences = xmldoc.createElement("externalReferences");
            for (int i = 0; i < this.getExternalReferences().size(); i++) {
                KafSense kafSense =  this.getExternalReferences().get(i);
                externalReferences.appendChild(kafSense.toXML(xmldoc));
            }
            root.appendChild(externalReferences);
        }
        return root;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public void setTokenString(KafSaxParser kafSaxParser) {
        this.setTokenString(AddTokensAsCommentsToSpans.getTokenString(kafSaxParser, this.getSpans()));
    }

}
