package tools;

import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTools {

    public static float getDiagonalOrWeight(@NonNull final String specString) {
        float res = 0;
        Pattern p = Pattern.compile("[.0-9]+");
        Matcher m = p.matcher(specString);
        while (m.find()) {
            float spec = Float.parseFloat(m.group());
            if (Float.parseFloat(m.group()) < 50.0f) {
                res = spec;
            }
        }
        return res;
    }
}
