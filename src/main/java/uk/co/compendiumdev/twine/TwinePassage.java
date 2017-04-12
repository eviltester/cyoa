package uk.co.compendiumdev.twine;


import java.util.ArrayList;
import java.util.List;

public class TwinePassage {
    private String id;
    private String name;
    private String body;
    boolean parsedLinks;
    private String descriptionText;
    private List<PassageLink> passageLinks;
    private boolean startPassage=false;
    private String order;

    public TwinePassage(){
        parsedLinks = false;
        body = "";
        id = "";
        name = "";

        passageLinks = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getNameAsLink() {
        return name.replaceAll(" ", "-").toLowerCase();
    }

    public String getDescriptionText() {

        if(!parsedLinks){
            parseForLinks();
        }
        return descriptionText;
    }

    private void parseForLinks() {

        String [] lines = body.split("\\n");

        descriptionText = "";
        for(String line : lines){
            if(line.trim().startsWith("[")){
                // process link
                passageLinks.add(new PassageLink(line));
            }else{
                if(line.trim().length()>0){
                    descriptionText = descriptionText + "\n\n" + line.trim();
                }
            }
        }

        parsedLinks = true;

    }

    public String getId() {
        return id;
    }

    public List<PassageLink> getLinks() {
        return passageLinks;
    }


    public void setAsStartPassage() {
        startPassage = true;
    }

    public boolean isStartPassage() {
        return startPassage;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
