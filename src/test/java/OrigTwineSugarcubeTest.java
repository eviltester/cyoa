import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;
import uk.co.compendiumdev.twine.PassageLink;
import uk.co.compendiumdev.twine.TwinePassage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class OrigTwineSugarcubeTest {



        @Test
        public void convert() throws IOException {

            String path = "D:\\Users\\Alan\\Documents\\Documents\\Compendium Developments\\cyoa\\twine\\zombies_aaargh\\twine2\\" + "testTwineArchive.html";



            File book_txt_file = new File(path);

            if(!book_txt_file.exists()){
                throw new FileNotFoundException("ERROR: Could not find file:" + book_txt_file.getAbsolutePath());
            }

            Document doc = Jsoup.parse(book_txt_file, "UTF-8", "http://example.com/");

            doc.outputSettings().prettyPrint(false);

            Elements elements = doc.getElementsByTag("tw-passagedata");

            List<TwinePassage> passages = new ArrayList<>();

            for(Element element : elements) {
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

                if(passage.getName().contentEquals("Start")){
                    passage.setAsStartPassage();
                }

                passages.add(passage);

            }

            for(TwinePassage passage : passages){

                System.out.println("File - " + passage.getNameAsLink());
                System.out.println("---");
                System.out.println("## " + passage.getId() + "{#" + passage.getNameAsLink() + "}");
                System.out.println("\n**" + passage.getName() + "**\n");
                System.out.println(passage.getDescriptionText());

                System.out.println("\n");

                for(PassageLink link : passage.getLinks()){
                    System.out.println("- [" + link.getLinkText() + "]" + "(#" + link.getLinkName() + ")");
                }


                System.out.println("---");
            }

            // output files

            Path lean = new File(book_txt_file.getParent(),"\\leanpub").toPath();
            Files.createDirectories(lean);

            for(TwinePassage passage : passages){

                File outputFile = new File(lean.toFile(), "\\" + passage.getNameAsLink() + ".md");

                if(outputFile.exists()){
                    outputFile.delete();
                }
                outputFile.createNewFile();

                BufferedWriter output = Files.newBufferedWriter(outputFile.toPath(),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND);

                output.write("\n");
                output.write("## " + passage.getId() + "{#" + passage.getNameAsLink() + "}");
                output.write("\n");
                output.write("\n**" + passage.getName() + "**\n");
                output.write("\n");
                output.write(passage.getDescriptionText());
                output.write("\n");

                output.write("\n");

                for(PassageLink link : passage.getLinks()){

                    output.write("- [" + link.getLinkText() + "]" + "(#" + link.getLinkName() + ")");
                    output.write("\n");
                }


                output.write("\n\n---\n\n");

                output.flush();
                output.close();
            }

            // identify start passage

            // output book.txt
            File outputFile = new File(lean.toFile(), "\\" + "book.txt");

            if(outputFile.exists()){
                outputFile.delete();
            }
            outputFile.createNewFile();

            BufferedWriter output = Files.newBufferedWriter(outputFile.toPath(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND);

            for(TwinePassage passage : passages){
                if(passage.isStartPassage()){
                    output.write(passage.getNameAsLink() + ".md");
                    output.newLine();
                    break;
                }
            }
            for(TwinePassage passage : passages){
                if(!passage.isStartPassage()){
                    output.write(passage.getNameAsLink() + ".md");
                    output.newLine();
                }
            }

            output.flush();
            output.close();

            // handle StoryTitle, StoryAuthor, StorySubTitle specials

            // randomly output remaining passages


        }


}
