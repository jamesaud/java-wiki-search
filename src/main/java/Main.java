/**
 * Created by jamesaudretsch on 9/1/17.
 */

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> "Hello World");


        System.out.println("Server running at http://0.0.0.0:4567");
    }
}