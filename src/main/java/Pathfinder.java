import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by jamesaudretsch on 9/1/17.
 */
public class Pathfinder {
    LinkedList<String> path = new LinkedList<>();

    Pathfinder(String startUrl){
        this.path.add(startUrl);
    }

    String traverseNext() throws IOException{
       String nextLink = Wikiscrape.searchFirst(this.path.peek());
       this.path.add(0, nextLink);
       return nextLink;
    }

    public LinkedList<String> findPath(final String destination) throws IOException{
        final HashSet<String> visited = new HashSet<>();
        String url = "";
        LinkedList<String> path = this.path;

        while (!url.equals(destination) ){
            url = this.traverseNext();

            if (visited.contains(url) || url == null){
                path = null;  // It's a loop, or no more links to traverse.
                break;
            }
            visited.add(url);
        }
        return path;
    }

}
