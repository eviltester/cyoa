import org.junit.Test;
import uk.co.compendiumdev.pandocifier.PandocifierOutput;
import uk.co.compendiumdev.twine.TwineStory;
import uk.co.compendiumdev.twine.TwineSugarCubeReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alan on 12/04/2017.
 */
public class TwineSugarcubeTest {

    @Test
    public void convert() throws IOException {

        // todo use with original twine 1 file
        String path = "D:\\Users\\Alan\\Documents\\Documents\\Compendium Developments\\cyoa\\twine\\zombies_aaargh\\twine2\\" + "testTwineArchive.html";

        // use with twine 2 exported using https://twinery.org/forum/discussion/2279/twine-1-to-twine-2-quick-question
        //path = "D:\\Users\\Alan\\Documents\\Documents\\Compendium Developments\\cyoa\\twine\\zombies_aaargh\\twine2\\" + "zombies_aaargh_twine2export.html";
        
        File inputFile = new File(path);

        TwineSugarCubeReader reader = new TwineSugarCubeReader(inputFile);
        TwineStory story = new TwineStory();

        reader.loadInto(story);

        //story.debugPassageOutput();

        story.reorder();

        // output files
        // TODO: output StoryTitle, StoryAuthor, StorySubTitle
        // TODO: make output of the name of the passage optional e.g. output.options(OUTPUT_PASSAGE_NAME_TITLE,false);
        // TODO: make output of the titles file optional addTitleDetailsToContents e.g. output.options(OUTPUT_TITLES_PAGE,false);
        PandocifierOutput output = new PandocifierOutput(inputFile.getParent(),"\\pandocifier");
        output.writeStoryAsPandocifierOutput(story);
        
    }
}
