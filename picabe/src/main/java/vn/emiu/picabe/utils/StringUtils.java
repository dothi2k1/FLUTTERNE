package vn.emiu.picabe.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String normalizeString(String input) {
        if (input == null) {
            return "";
        }
        // Convert to Unicode normalized form
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove diacritics (accents)
        String noDiacritics = normalized.replaceAll("\\p{M}", "");
        // Remove all non-alphanumeric characters (except spaces)
        return noDiacritics.replaceAll("[^a-zA-Z0-9 ]", "").replace(" ", "");
    }
}
