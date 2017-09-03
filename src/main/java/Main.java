/**
 * Created by jamesaudretsch on 9/1/17.
 */
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import static spark.Spark.*;

public class Main {
    final static String resultKeyword = "result";
    final static String errorKeyword = "errors";

    static HashMap<String, Object> findPath(String searchTerm){
        // Make a json like object with HashMap for JsonUtil to convert
        HashMap<String, Object> results = new HashMap<>();

        String startUrl = "/wiki/" + searchTerm;
        try {
            LinkedList<String> path = new Pathfinder(startUrl).findPath("/wiki/Philosophy");
            Collections.reverse(path);
            if (path == null){
                results.put(errorKeyword, "Circular path, no way to reach Philosophy.");
            }
            else{
                results.put(resultKeyword, path);
            }
        }
        catch (IOException e){
            results.put(errorKeyword, "Can't find path, error connecting to Wikipedia. Did you provide a proper term? (" + e.toString() + ")");
        }

        return results;

    }


    public static void main(String[] args) {
        get("/", (req, res) -> new RenderUtil().renderContent("index.html"));
        get("/path", (req, res) -> Main.findPath("Human"), JsonUtil.json());
        System.out.println("Server running at http://0.0.0.0:4567");
    }
}





