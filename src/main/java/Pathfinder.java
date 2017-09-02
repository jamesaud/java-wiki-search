import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by jamesaudretsch on 9/1/17.
 */
public class Pathfinder {
    int maxQueueSize = 1000; // Maximum length to save memory
    Node currentNode;
    Queue<Node> queue = new ArrayDeque<>();
    Wikiscrape scrape = new Wikiscrape();

    Pathfinder(String startUrl){
        this.currentNode = new Node(startUrl, null);
    }

    // BFS Traversal
    public List<Node> searchNextNeighbors() throws IOException{
        HashSet<String> results = this.scrape.search("https://en.wikipedia.org" + this.currentNode.data);
        List<Node> new_visits = new ArrayList<>();

        for (String url: results){
            Node newNode = new Node(url, this.currentNode);

            // Optimize queue space
            if (this.queue.size() < this.maxQueueSize) {
                this.queue.add(newNode);
            }

            new_visits.add(newNode);
        }
        this.currentNode = this.queue.poll(); // The next node to explore
        return new_visits;
    }

    public static void main(String[] args) throws IOException {

        Pathfinder pfStart = new Pathfinder("/wiki/Surgeon_general");
        Pathfinder pfEnd = new Pathfinder("/wiki/Philosophy");

        HashMap<String, Node> visitedOrigin = new HashMap<>();
        HashMap<String, Node> visitedDestination = new HashMap<>();

        boolean flag = false;
        boolean found = false;

        Node startNodePath = null;
        Node endNodePath = null;

        while (true){
            if (flag){
                for (Node node: pfStart.searchNextNeighbors()){
                    visitedOrigin.put(node.data, node);
                    if (visitedDestination.get(node.data) != null){
                        endNodePath = visitedDestination.get(node.data);
                        startNodePath = node;
                        found = true;
                        break;
                    }
                }
            }

            else{
                for (Node node: pfEnd.searchNextNeighbors()){
                    visitedDestination.put(node.data, node);
                    if (visitedOrigin.get(node.data) != null){
                        endNodePath = node;
                        startNodePath = visitedOrigin.get(node.data);
                        found = true;
                        break;
                    }
                }
            }

            if (found){break;}

            flag = !flag;
        }

        List<Node> path = startNodePath.getPathToRoot();
        Collections.reverse(path);                // URL we started with should be first
        path.remove(path.size() - 1); // Duplicated element bc the same url is in the end path
        path.addAll(endNodePath.getPathToRoot()); // Add the end path

        System.out.println(path);
    }
}
