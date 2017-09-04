/**
 * Created by jamesaudretsch on 9/1/17.
 */
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.json.JSONException;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


import static spark.Spark.*;

public class Main {
    final static String resultKeyword = "result";
    final static String errorKeyword = "errors";

    static String findPath(String searchTerm){
        // Make a json like object with HashMap for JsonUtil to convert
        String response;
        String startUrl = "/wiki/" + searchTerm;
        HashMap<String, Object> results = new HashMap<>();

        // Check if the record exists
        String result = DbUtil.get_from_db(searchTerm);
        if(result != null) {
            try {
                response = JsonUtil.fromJson(String.format("{%s: %s}", resultKeyword, result)).toString();
            } catch(JSONException e){
                results.put(errorKeyword, "Internal Server error.");
                response = JsonUtil.toJson(results);
            }
        }

        else {
            try {
                LinkedList<String> path = new Pathfinder(startUrl).findPath("/wiki/Philosophy");
                Collections.reverse(path);
                if (path == null) {
                    results.put(errorKeyword, "Circular path, no way to reach Philosophy.");
                }
                else {
                    results.put(resultKeyword, path);
                    // Insert into the DB
                    DbUtil.insert_in_db(searchTerm, JsonUtil.toJson(path));
                }
            } catch (IOException e) {
                results.put(errorKeyword, "Can't find path, error connecting to Wikipedia. Did you provide a proper term? (" + e.toString() + ")");
            }
            response = JsonUtil.toJson(results);
        }

        return response;
    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> new RenderUtil().renderContent("index.html"));

        get("/path", (req, res) -> {
            String start = req.queryParams("start");
            return Main.findPath(start);
        });

        after("/path", (req, res) -> {
            res.type("application/json");
        });

        System.out.println("Server running at http://0.0.0.0:4567");

    }
}





