package uk.co.compendiumdev.twine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alan on 12/04/2017.
 */
public class PassageLink {
    private final String raw;
    private boolean parsedLink=false;
    private String linkText;
    private String linkName;

    public PassageLink(String line) {
        this.raw = line;
    }

    public String getLinkText() {
        if(!parsedLink){
            parseLink();
        }
        return linkText;
    }

    private void parseLink() {
        if(parsedLink){
            return;
        }

        Pattern linkMatch = Pattern.compile("\\[\\[(.*)\\|(.*)\\]\\]");
        Matcher links = linkMatch.matcher(this.raw);
         links.find();
        linkText = links.group(1);
        linkName = links.group(2);
        parsedLink = true;
    }

    public String getLinkName() {
        return linkName.replaceAll(" ", "-").toLowerCase();
    }
}
