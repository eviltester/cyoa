package uk.co.compendiumdev.pandocifier;

import uk.co.compendiumdev.twine.PassageLink;
import uk.co.compendiumdev.twine.TwinePassage;
import uk.co.compendiumdev.twine.TwineStory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PandocifierOutput {

    private final String outputSubFolder;
    private final File parentFolder;


    public PandocifierOutput(String folder, String outputSubFolder) {

         parentFolder = new File(folder);
         this.outputSubFolder = outputSubFolder;

    }

    public void writeStoryAsPandocifierOutput(TwineStory story) throws IOException {

        Path pandocify = new File(parentFolder,"\\" + this.outputSubFolder).toPath();
        Files.createDirectories(pandocify);


        outputTitleDetails(pandocify, story);
        boolean addTitleDetailsToContents = true;




        for(TwinePassage passage : story.passages()){

            File outputFile = new File(pandocify.toFile(), "\\" + passage.getNameAsLink() + ".md");

            if(outputFile.exists()){
                outputFile.delete();
            }
            outputFile.createNewFile();

            BufferedWriter output = Files.newBufferedWriter(outputFile.toPath(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND);

            output.newLine();
            output.write("## " + passage.getOrder() + "{#" + passage.getNameAsLink() + "}");
            output.newLine();
            output.write("\n**" + passage.getName() + "**\n");
            output.newLine();
            output.write(passage.getDescriptionText());
            output.newLine();

            output.newLine();

            for(PassageLink link : passage.getLinks()){

                output.write("- [" + link.getLinkText() + " (goto " + story.findOrderOfPassage(link.getLinkName()) + ")]" + "(#" + link.getLinkName() + ")");
                output.newLine();
            }

            output.newLine();
            output.newLine();
            output.write("---");
            output.newLine();
            output.newLine();

            output.flush();
            output.close();
        }

        // identify start passage

        // output book.txt  - ordered passages
        File outputFile = new File(pandocify.toFile(), "\\" + "book.txt");

        if(outputFile.exists()){
            outputFile.delete();
        }
        outputFile.createNewFile();

        BufferedWriter output = Files.newBufferedWriter(outputFile.toPath(),
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);

        if(addTitleDetailsToContents){
            output.write("_titles.md");
            output.newLine();
        }

        for(TwinePassage passage : story.orderedPassages()){

                output.write(passage.getNameAsLink() + ".md");
                output.newLine();
        }

        output.flush();
        output.close();
    }

    private void outputTitleDetails(Path pandocify, TwineStory story) throws IOException {
        File outputFile = new File(pandocify.toFile(), "\\_titles.md");

        if(outputFile.exists()){
            outputFile.delete();
        }
        outputFile.createNewFile();

        BufferedWriter output = Files.newBufferedWriter(outputFile.toPath(),
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);

        output.newLine();
        output.write("# " + story.getTitle());
        output.newLine();
        output.newLine();
        output.write("**" + story.getSubTitle() + "**");
        output.newLine();
        output.newLine();
        output.write("_By " + story.getAuthor() + "_");
        output.newLine();
        output.newLine();

        output.flush();
        output.close();
    }
}
