package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * Created by piek on 26/06/15.
 */
public class KafTopic implements Serializable {

    /*

    The \texttt{<topic>} element has the following attributes:
\begin{itemize}
\item \texttt{source} (optional): A reference to the entity
 responsible for creating the annotation.
\item \texttt{method} (optional): The name of the method used to
 create the annotation. This attribute is usually used in conjunction
 with the \texttt{source} attribute.
\item \texttt{confidence} (optional): this attribute is optional but
 its presence is highly recommended. It gives a value of
 ``confidence'' for the annotation. The confidence value can be in
 fact almost anything (similarity score, the value of the margin on a
 SVM based classification, etc), as long as it can be used to sort
 all annotations sharing the \texttt{source} and \texttt{method}
 attributes.
\item \texttt{uri} (optional): if the topic is a resource
 from an external reference, an URI to this resource. For instace, it
 could be a URI pointing to a Wikipedia category page, etc.
\end{itemize}

The content of the \texttt{<topic>} element is a string with the
topic.

    <topics>
 <topic source="newsreader"
	 confidence="0.8"
	 method="textclassification-svm"
	 URI="http://en.wikipedia.org/wiki/Category:Tower_mills">
  Tower Mills</topic>
 <topic confidence="0.2"
	 source="newsreader"
	 method="textclassification-svm"
	 URI="http://en.wikipedia.org/wiki/Category:Windmills_in_Somerset">
  Windmills in Somerset</topic>
</topics>


  <topics>
  <topic source="newsreader"
	 confidence="0.8"
	 method="textclassification-svm"
	 uri="http://en.wikipedia.org/wiki/Category:Iraq">Iraq</topic>
  <topic confidence="0.6"
	 source="newsreader"
	 method="textclassification-svm"
	 uri="http://en.wikipedia.org/wiki/Category:War">War</topic>
  </topics>
     */

    private String source;
    private String method;
    private double confidence;
    private String uri;
    private String topic;

    public KafTopic() {
        this.confidence = 0;
        this.method = "";
        this.source = "";
        this.topic = "";
        this.uri = "";
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("topic");
        if (!uri.isEmpty()) root.setAttribute("uri", uri);
        if (!source.isEmpty()) root.setAttribute("source", source);
        if (!method.isEmpty()) root.setAttribute("method", method);
        if (confidence>0) {
            root.setAttribute("confidence", String.valueOf(new Double(confidence)));
        }
        if (!topic.isEmpty()) {
            Node text = xmldoc.createTextNode(topic);
            root.appendChild(text);
        }

        return root;
    }
    public String toString()
    {
        String str = "<topic";
        if (!uri.isEmpty()) str += " uri\"" +uri+"\"";
        if (!source.isEmpty()) str += " source\"" +source+"\"";
        if (!method.isEmpty())
            str += " method\"" +method+"\"";
        if (confidence>0) {
            str += " confidence\"" +confidence+"\"";
        }
        str += "/>";

        return str;
    }

}
