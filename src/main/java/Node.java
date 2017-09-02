/**
 * Created by jamesaudretsch on 9/1/17.
 */
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node{
    String data;
    Node parent;

    // Constructor to create a new node
    // next and prev is by default initialized as null
    Node(String d, Node parent){
        this.data = d;
        this.parent = parent;
    }


    public List<Node> getPathToRoot(){
        Node node = this;
        List<Node> path = new ArrayList<>();
        while (node != null){
            path.add(node);
            node = node.parent;
        }
        return path;
    }

    public void setParent(Node node){
        this.parent = node;
    }

    public void printPath(){
        Node node = this;
        while (node != null){
            System.out.println(node + " -> ");
            node = node.parent;
        }
    }

    public String toString() {
        return getClass().getName() + ": " + this.data;
    }
}
