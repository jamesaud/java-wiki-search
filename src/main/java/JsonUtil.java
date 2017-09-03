import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by jamesaudretsch on 9/2/17.
 */
public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
