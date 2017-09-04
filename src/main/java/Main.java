/**
 * Created by jamesaudretsch on 9/1/17.
 */
import org.json.JSONException;
import spark.Spark;

import java.io.IOException;
import java.util.*;


import static spark.Spark.*;

public class Main {
    // Configure the json response 'keys'
    final static String resultKeyword = "result";
    final static String errorKeyword = "errors";

    static String findPath(String searchTerm){
        /* Finds a path from a wikipedia article to philosophy.
         * Stores the path in a DB,  caches response if possible. Try/Catch ensures that the code works even if the DB stops working.
         * Should return a json formatted String
         * */
        searchTerm = searchTerm.toLowerCase();
        String response = null;
        String startUrl = "/wiki/" + searchTerm;
        HashMap<String, Object> results = new HashMap<>();    // Make a json like object with HashMap for JsonUtil to convert

        String result = null;
        try {   // Try to get path from DB
            result = DbUtil.get_from_db(searchTerm);
        }
        catch (Exception e){}  // If DB isn't working, ignore

        if (result != null) { // Return cached path from the DB
            try {
                response = JsonUtil.fromJson(String.format("{%s: %s}", resultKeyword, result)).toString();
            } catch (JSONException e) {   // Failed to convert to json
                results.put(errorKeyword, "Internal Server error (" + e.toString() + ")");
                response = JsonUtil.toJson(results);
            }
        } else {  // Find the path if not in DB
            List path = null;
            boolean error = false;
            try {
                path = new Pathfinder(startUrl).findPath("/wiki/Philosophy"); // null means there's no path
            } catch (IOException e) {
                // Trouble connecting to Wikipedia
                // This can happen if the wikipedia url is malformed
                results.put(errorKeyword, "Can't find path, error connecting to Wikipedia. Did you provide a proper term?"
                        + "https://wikipedia.org/wiki/" + searchTerm + " should exist. " + "(" + e.toString() + ")");
                response = JsonUtil.toJson(results);
                error = true;
            }

            if (!error) {
                if (path == null) {
                    results.put(errorKeyword, "No way to reach Philosophy");
                } else {
                    Collections.reverse(path);
                    results.put(resultKeyword, path);
                    try {
                        DbUtil.insert_in_db(searchTerm, JsonUtil.toJson(path));
                    } catch (Exception e) {} // Trouble connecting to DB, just return the answer to the user
                }

                response = JsonUtil.toJson(results);
            }
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
        Spark.staticFileLocation("/public");


        get("/", (req, res) -> new RenderUtil().renderContent("public/index.html"));

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





