import com.mongodb.*;

/**
 * Created by jamesaudretsch on 9/3/17.
 */
public final class DbUtil {
    final static String key = "search-term";

    static private MongoClient getMongoClient(){
        return new MongoClient(Config.mongoserver, Integer.parseInt(Config.mongoport));
    }

    static void insert_in_db(String searchTerm, String path){
        MongoClient mongoClient = getMongoClient();
        DB db = mongoClient.getDB(Config.dbname); // db holds collections.auto create db in this command

        DBCollection coll = db.getCollection(Config.dbcollection);

        BasicDBObject doc = new BasicDBObject(key, searchTerm)
                .append(searchTerm, path);

        coll.insert(doc);
    }

    static String get_from_db(String searchTerm) {
        MongoClient mongoClient = getMongoClient();

        DB db = mongoClient.getDB(Config.dbname); // db holds collections.auto create db in this command

        DBCollection coll = db.getCollection(Config.dbcollection);
        BasicDBObject searchTermQuery = new BasicDBObject(key, searchTerm);
        DBCursor cursor = coll.find(searchTermQuery);
        String result = null;
        while (cursor.hasNext()) {
            result = cursor.next().get(searchTerm).toString();
        }
        return result;
    }

}
