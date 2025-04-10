package APIs;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.util.Map;

public class Email {
    private static final Dotenv dotenv = environmentLoader.envLoader();

    public static void main(String[] args) {
        System.out.println(isValidEmail("alexdownman@gmail.com"));
    }

    public static Boolean isValidEmail(String email) {
        try {
            HttpURLConnection apiConnection = API.fetchApiResponse("https://emailvalidation.abstractapi.com/v1/?api_key="
                    + dotenv.get("EMAIL_API_KEY")
                    + "&email="
                    + email
                    + "&auto_correct=true");

            assert apiConnection != null;
            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: " + apiConnection.getResponseMessage());
            } else {
                System.out.println(apiConnection.getResponseCode());
            }

            String jsonResponse = API.readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            Map<String, Object> resultMap = JSON.jsonToMap(jsonObject);

            double qScore = Double.parseDouble(resultMap.get("quality_score").toString());

            return (qScore > 0.20);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
