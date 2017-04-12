package uk.co.compendiumdev.twine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alan on 12/04/2017.
 */
public class TwineStory {
    private String title;
    private String author;
    private String subTitle;
    List<TwinePassage> passages;
    private ArrayList<TwinePassage> orderedPassages;

    public TwineStory(){
        passages = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void addPassages(TwinePassage passage) {
        passages.add(passage);
    }

    public List<TwinePassage> passages() {
        return passages;
    }

    public void debugPassageOutput() {
        
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

    }

    public void reorder() {
        // create a random order for the story Ids, but put start at 1

        List<String> ids = new ArrayList<>();

        orderedPassages = new ArrayList<TwinePassage>();
        for(int position=0;position<passages.size(); position++){
            orderedPassages.add(position, null);
        }

        for(int id=2; id<=passages.size(); id++){
            ids.add(String.valueOf(id));
        }

        for(TwinePassage passage : passages){
            if(passage.isStartPassage()){

                passage.setOrder("1");
                orderedPassages.set(0, passage);

            }else{
                // randomly choose a value from the array
                int randomNum = ThreadLocalRandom.current().nextInt(0, ids.size());
                String order = ids.get(randomNum);
                passage.setOrder(order);
                ids.remove(randomNum);
                orderedPassages.set(Integer.parseInt(order)-1, passage);
            }
        }

        int position = 0;
        for(TwinePassage orderedPassage : orderedPassages){
            System.out.println(position + " - " + orderedPassage.getOrder() + " - " + orderedPassage.getId());
            position++;
        }

    }

    public ArrayList<TwinePassage> orderedPassages() {
        return orderedPassages;
    }

    public String findOrderOfPassage(String linkName) {
        for(TwinePassage passage : passages){
            if(passage.getNameAsLink().contentEquals(linkName)){
                return passage.getOrder();
            }
        }
        throw new RuntimeException("Could not find order of passage " + linkName);
    }

    public String getTitle() {
        return title.trim();
    }

    public String getSubTitle() {
        return subTitle.trim();
    }

    public String getAuthor() {
        return author.trim();
    }
}
