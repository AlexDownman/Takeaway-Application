package APIs;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSON {
    public static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> map = new HashMap<>();
        for (Object keyObj : json.keySet()) {
            String key = (String) keyObj;
            Object value = json.get(key);

            // If the value is a nested JSONObject, convert it recursively
            if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }

            map.put(key, value);
        }
        return map;
    }
}
