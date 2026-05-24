package enterprises.iwakura.cirno;

import java.text.Normalizer;
import java.util.Locale;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    /**
     * The default threshold of 85% for {@link #isSimilar(String, String)}
     */
    public static final double DEFAULT_THRESHOLD = 0.85;

    /**
     * Checks if the supplied string is empty (i.e., no length of 0)
     *
     * @param str String
     *
     * @return True if empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if the supplied string is blank (i.e., contains only whitespaces)
     *
     * @param str String
     *
     * @return True if blank, false otherwise
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Capitalizes the first found letter, sets all other letts to lower case
     *
     * @param str String
     *
     * @return Capitalized string or returns itself if {@link #isBlank(String)}
     */
    public static String capitalize(String str) {
        if (isBlank(str)) {
            return str;
        }

        str = str.toLowerCase();
        int letterIndex = -1;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isLetter(ch)) {
                letterIndex = i;
                break;
            }
        }

        if (letterIndex != -1) {
            char[] charArray = str.toCharArray();
            charArray[letterIndex] = Character.toUpperCase(charArray[letterIndex]);
            return new String(charArray);
        } else {
            return str;
        }
    }

    /**
     * Capitalizes all first letters in words separated by spaces, sets all other letts to lower case
     *
     * @param str String
     *
     * @return Capitalized string or returns itself if {@link #isBlank(String)}
     */
    public static String capitalizeAllWords(String str) {
        if (isBlank(str)) {
            return str;
        }

        char[] charArray = str.toCharArray();
        boolean afterSpace = true;

        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];

            if (afterSpace && Character.isLetter(ch)) {
                charArray[i] = Character.toUpperCase(ch);
                afterSpace = false;
            } else if (Character.isWhitespace(ch)) {
                afterSpace = true;
            }
        }

        return new String(charArray);
    }

    /**
     * Computes the Jaro-Winkler similarity between two strings.
     * Returns a value between 0.0 (no similarity) and 1.0 (identical).
     * Comparison is case-insensitive and locale-independent.
     *
     * @param originalString the original string
     * @param otherString    the string to compare against
     *
     * @return similarity score between 0.0 and 1.0
     */
    public static double getSimilarityScore(String originalString, String otherString) {
        if (originalString == null || otherString == null) {
            return 0.0;
        }

        // Locale-independent lowercase
        final String s1 = originalString.toLowerCase(Locale.ROOT);
        final String s2 = otherString.toLowerCase(Locale.ROOT);

        if (s1.equals(s2)) {
            return 1.0;
        }

        final int len1 = s1.length();
        final int len2 = s2.length();

        if (len1 == 0 || len2 == 0) {
            return 0.0;
        }

        // Maximum distance for characters to be considered matching
        final int matchDistance = Math.max(len1, len2) / 2 - 1;

        final boolean[] s1Matched = new boolean[len1];
        final boolean[] s2Matched = new boolean[len2];

        int matches = 0;
        int transpositions = 0;

        // Find matches
        for (int i = 0; i < len1; i++) {
            final int start = Math.max(0, i - matchDistance);
            final int end = Math.min(i + matchDistance + 1, len2);

            for (int j = start; j < end; j++) {
                if (s2Matched[j] || s1.charAt(i) != s2.charAt(j)) {
                    continue;
                }
                s1Matched[i] = true;
                s2Matched[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) {
            return 0.0;
        }

        // Count transpositions
        int k = 0;
        for (int i = 0; i < len1; i++) {
            if (!s1Matched[i]) {
                continue;
            }
            while (!s2Matched[k]) {
                k++;
            }
            if (s1.charAt(i) != s2.charAt(k)) {
                transpositions++;
            }
            k++;
        }

        // Jaro similarity
        final double jaro = (
            (double) matches / len1 +
                (double) matches / len2 +
                (matches - transpositions / 2.0) / matches
        ) / 3.0;

        // Winkler prefix bonus (up to 4 common prefix chars)
        int prefixLength = 0;
        for (int i = 0; i < Math.min(4, Math.min(len1, len2)); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                prefixLength++;
            } else {
                break;
            }
        }

        return jaro + prefixLength * 0.1 * (1.0 - jaro);
    }

    /**
     * Returns true if the two strings are considered similar
     * based on a given threshold (0.0 to 1.0).
     *
     * @param originalString the original string
     * @param otherString    the string to compare against
     * @param threshold      minimum similarity score to be considered similar
     *
     * @return true if similarity >= threshold
     */
    public static boolean isSimilar(String originalString, String otherString, double threshold) {
        return getSimilarityScore(originalString, otherString) >= threshold;
    }

    /**
     * Returns true if the two strings are considered similar
     * based on the default threshold ({@link #DEFAULT_THRESHOLD})
     *
     * @param originalString the original string
     * @param otherString    the string to compare against
     *
     * @return true if similarity >= threshold
     */
    public static boolean isSimilar(String originalString, String otherString) {
        return getSimilarityScore(originalString, otherString) >= DEFAULT_THRESHOLD;
    }

    /**
     * Normalizes a string for search by removing diacritics and converting to lowercase
     *
     * @param input Input string
     *
     * @return Normalized string
     */
    public static String normalize(String input) {
        if (isBlank(input)) {
            return input;
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        return normalized.toLowerCase();
    }

    /**
     * Creates {@link BoyerMooreStringSearch} pattern for specified text and checks whenever it is contained in the
     * source. It is recommended to pre-build {@link BoyerMooreStringSearch} for the specified text once, if searched
     * through multiple sources.
     *
     * @param text   Text
     * @param source Source
     *
     * @return True if text is contained in source, false otherwise
     */
    public static boolean fastContains(String text, String source) {
        return BoyerMooreStringSearch.of(text).containedIn(source);
    }

    /**
     * Splits the string to lines based on the new line characters.
     *
     * @param str String
     *
     * @return String array of lines
     */
    public static String[] lines(String str) {
        return str.split("\\r?\\n");
    }

    /**
     * Shortens string to a specified max length. If string {@link #isBlank(String)} or is under the max length, returns
     * itself.
     *
     * @param str       String
     * @param maxLength Max length
     *
     * @return Shortened string with <code>...</code> at the end
     */
    public static String shortenString(String str, int maxLength) {
        if (isBlank(str) || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
