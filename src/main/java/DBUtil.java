import javax.swing.text.Document;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import com.mongodb.spark.MongoSpark;

/**
 * Created by jamesaudretsch on 9/3/17.
 */

import java.util.function.Function;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class DBUtil {
    static void insertIntoDB(){
        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/test.myCollection")
                .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/test.myCollection")
                .getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Create a RDD of 10 documents
        JavaRDD<Document> documents = jsc.parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).map
                (new Function<Integer, Document>() {
                    public Document call(final Integer i) throws Exception {
                        return Document.parse("{test: " + i + "}");
                    }
                });

    /*Start Example: Save data from RDD to MongoDB*****************/
        MongoSpark.save(sparkDocuments, writeConfig);
    /*End Example**************************************************/

        jsc.close();
    }

}
