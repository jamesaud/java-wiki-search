import java.util.Map;

/**
 * Created by jamesaudretsch on 9/4/17.
 */
final public class Config {
    // Set configuration from environment
    final static Map<String, String> env = System.getenv();

    // DB URI
    private final static String localdb = "mongodb://0.0.0.0:27017";
    final static String mongouri = env.get("MONGODB_URI") != null ? env.get("MONGODB_URI") : localdb;

    // Where to store the data
    final static String dbname = env.get("DB_NAME") != null ? env.get("DB_NAME") : "main-db";
    final static String dbcollection = "paths-to-philosophy";
}