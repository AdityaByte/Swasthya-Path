package in.ayush.swasthyapath.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterResponse {

    public static String filter(String rawJson) {
        Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(rawJson);

        if (matcher.find()) {
            String json = matcher.group();
            return json;
        } else {
            throw new RuntimeException("Could not extract JSON from response");
        }
    }

}
