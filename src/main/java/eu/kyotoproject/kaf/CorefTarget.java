package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/5/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CorefTarget {
        private String id;
        private String head;
        private String tokenString;

        public CorefTarget() {
            this.head = "";
            this.id = "";
            this.tokenString = "";
        }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Target{" +
                    "head='" + head + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        public Element toXML(Document xmldoc)
        {
            Element root = xmldoc.createElement("target");
            if (id != null)
                root.setAttribute("id", id);
            if (head != null)
                if (!head.isEmpty()) root.setAttribute("head", head);
            if (tokenString.length()>0) {
                Comment comment = xmldoc.createComment(tokenString);
                root.appendChild(comment);
            }
            return root;
        }
}
