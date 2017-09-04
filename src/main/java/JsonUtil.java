import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Iterator;

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

    public static JSONObject fromJson(String s) throws JSONException{
        JSONObject jsonObj = new JSONObject(s);
        return jsonObj;
    }

}
