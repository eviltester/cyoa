package uk.co.compendiumdev.twine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Alan on 12/04/2017.
 */
public class TwineSugarCubeReader {
    private final File filename;

    public TwineSugarCubeReader(File filename) {
        this.filename = filename;
    }

    public void loadInto(TwineStory story) throws IOException {


        if(!filename.exists()){
            throw new FileNotFoundException("ERROR: Could not find file:" + filename.getAbsolutePath());
        }

        Document doc = Jsoup.parse(filename, "UTF-8", "http://example.com/");

        doc.outputSettings().prettyPrint(false);

        Elements elements = doc.getElementsByTag("tw-passagedata");






        for(Element element : elements) {

            boolean ignorePassage=false;

            TwinePassage passage = new TwinePassage();
            passage.setId(element.attributes().get("pid"));
            passage.setName(element.attributes().get("name"));

            String text = "";
            for(TextNode node : element.textNodes()){
                text = text + node + "\n\n";
            }

            passage.setBody(text);


            if(passage.getName().contentEquals("Start")){
                passage.setAsStartPassage();
            }

            if(passage.getName().contentEquals("StoryTitle")){
                story.setTitle(passage.getDescriptionText());
                ignorePassage = true;
            }

            // StoryAuthor
            if(passage.getName().contentEquals("StoryAuthor")){
                story.setAuthor(passage.getDescriptionText());
                ignorePassage = true;
            }

            //StorySubtitle
            if(passage.getName().contentEquals("StorySubtitle")){
                story.setSubTitle(passage.getDescriptionText());
                ignorePassage = true;
            }

            if(!ignorePassage) {
                story.addPassages(passage);
            }

        }
    }
}
