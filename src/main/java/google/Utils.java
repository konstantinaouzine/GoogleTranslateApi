package google;

import java.util.HashMap;
import java.util.Map;

import static google.TextConstants.*;

public class Utils {
    public static Map<String, Object> prepareTranslationQueryParams(boolean sendLanguage, Object language, boolean sendText, Object text) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (sendLanguage)
            queryParams.put(P_LANGUAGE, language);
        if (sendText)
            queryParams.put(P_TEXT, text);
        return queryParams;
    }

    public static String getRapidApiKey() {
        //Some work to receive the key -> currently hardcoded
        return "e0ebe57d74mshdb467cd843c8112p1b99bejsnbf9425db7285";
    }

    public static String getRapidApiHost() {
        //Some work to receive the Host -> currently hardcoded
        return "google-translate53.p.rapidapi.com";
    }
}
