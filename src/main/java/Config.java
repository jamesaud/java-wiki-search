import java.util.Map;

/**
 * Created by jamesaudretsch on 9/4/17.
 */
final public class Config {
    // Set configuration from environment
    final static Map<String, String> env = System.getenv();
    final static String mongoserver = env.get("MONGODB_URI") != null ? env.get("MONGODB_URI") : "0.0.0.0";
    final static String mongoport = env.get("MONBODB_PORT") != null ? env.get("MONBODB_PORT") : "27017";

    // Where to store the data
    final static String dbname = "main-db";
    final static String dbcollection = "paths-to-philosophy";
}