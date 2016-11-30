package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by piek on 30/11/2016.
 */
public class KafEmotionSet implements Serializable {


    private ArrayList<KafEmotion> kafEmotionArrayList;
    private String id;
    private String emotion;
    private String strength;
    private String sentenceId;

    public KafEmotionSet() {
        this.kafEmotionArrayList = new ArrayList<KafEmotion>();
        this.id = "";
        this.emotion = "";
        this.strength = "";
        this.sentenceId = "";
    }

    public ArrayList<KafEmotion> getKafEmotionArrayList() {
        return kafEmotionArrayList;
    }

    public void setKafEmotionArrayList(ArrayList<KafEmotion> kafEmotionArrayList) {
        this.kafEmotionArrayList = kafEmotionArrayList;
    }

    public void addKafEmotionArrayList(KafEmotion kafEmotion) {
        this.kafEmotionArrayList.add(kafEmotion);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("emotionSet");
        if (!id.isEmpty()) root.setAttribute("id", id);
        if (!emotion.isEmpty()) root.setAttribute("emotion", emotion);
        if (!strength.isEmpty()) root.setAttribute("strength", strength.toString());
        if (!sentenceId.isEmpty()) root.setAttribute("sentence", sentenceId.toString());


        if (kafEmotionArrayList.size()>0) {
            for (int i = 0; i < kafEmotionArrayList.size(); i++) {
                KafEmotion kafEmotion = kafEmotionArrayList.get(i);
                root.appendChild(kafEmotion.toNafXML(xmldoc));
            }
        }
        return root;
    }
}
