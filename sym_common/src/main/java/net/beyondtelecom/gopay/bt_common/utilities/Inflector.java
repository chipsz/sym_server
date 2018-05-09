package net.beyondtelecom.gopay.bt_common.utilities;

import net.beyondtelecom.gopay.bt_common.structure.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Transforms words (from singular to plural, from camelCase to under_score, etc.). I got bored of doing Real Work...
 *
 * @author chuyeow
 */
public class Inflector {

    // Pfft, can't think of a better setName, but this is needed to avoid the price of initializing the pattern on each call.
    private static final Pattern UNDERSCORE_PATTERN_1 = Pattern.compile("([A-Z]+)([A-Z][a-z])");
    private static final Pattern UNDERSCORE_PATTERN_2 = Pattern.compile("([a-z\\d])([A-Z])");
    private static final Pattern ALL_LOWERCASE = Pattern.compile("([a-z\\d])");

    private static List<Pair<String, String>> plurals = new ArrayList<>();
    private static List<Pair<String, String>> singulars = new ArrayList<>();
    private static List<String> uncountables = new ArrayList<>();

    private static Inflector instance; // (Pseudo-)Singleton instance.

    private Inflector() { initialize(); }

    private void initialize() {
        plural("$", "s");
        plural("s$", "s");
        plural("(ax|test)is$", "$1es");
        plural("(octop|vir)us$", "$1i");
        plural("(alias|status)$", "$1es");
        plural("(bu)s$", "$1es");
        plural("(buffal|tomat)o$", "$1oes");
        plural("([ti])um$", "$1a");
        plural("sis$", "ses");
        plural("(?:([^f])fe|([lr])f)$", "$1$2ves");
        plural("(hive)$", "$1s");
        plural("([^aeiouy]|qu)y$", "$1ies");
        plural("([^aeiouy]|qu)ies$", "$1y");
        plural("(x|ch|ss|sh)$", "$1es");
        plural("(matr|vert|ind)ix|ex$", "$1ices");
        plural("([m|l])ouse$", "$1ice");
        plural("(ox)$", "$1en");
        plural("(quiz)$", "$1zes");

        singular("s$", "");
        singular("(n)ews$", "$1ews");
        singular("([ti])a$", "$1um");
        singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis");
        singular("(^analy)ses$", "$1sis");
        singular("([^f])ves$", "$1fe");
        singular("(hive)s$", "$1");
        singular("(tive)s$", "$1");
        singular("([lr])ves$", "$1f");
        singular("([^aeiouy]|qu)ies$", "$1y");
        singular("(s)eries$", "$1eries");
        singular("(m)ovies$", "$1ovie");
        singular("(x|ch|ss|sh)es$", "$1");
        singular("([m|l])ice$", "$1ouse");
        singular("(bus)es$", "$1");
        singular("(o)es$", "$1");
        singular("(shoe)s$", "$1");
        singular("(cris|ax|test)es$", "$1is");
        singular("([octop|vir])i$", "$1us");
        singular("(alias|status)es$", "$1");
        singular("^(ox)en", "$1");
        singular("(vert|ind)ices$", "$1ex");
        singular("(matr)ices$", "$1ix");
        singular("(quiz)zes$", "$1");

        irregular("person", "people");
        irregular("man", "men");
        irregular("child", "children");
        irregular("sex", "sexes");
        irregular("move", "moves");

        uncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep");
    }

    public static Inflector getInstance() {
        if (instance == null) {
            instance = new Inflector();
        }
        return instance;
    }

    public String underscore(String camelCasedWord) {
        if (ALL_LOWERCASE.matcher(camelCasedWord).matches()) {
            return camelCasedWord;
        }

        // Regexes in Java are stupid...
        String underscoredWord = UNDERSCORE_PATTERN_1.matcher(camelCasedWord).replaceAll("$1_$2");
        underscoredWord = UNDERSCORE_PATTERN_2.matcher(underscoredWord).replaceAll("$1_$2");
        underscoredWord = underscoredWord.replace('-', '_').toLowerCase();

        return underscoredWord;
    }

    public String hypehnate(String camelCasedWord) {
        return underscore(camelCasedWord).replace('_', '-');
    }

    public String phrase(String camelCasedWord) {
        String underScored = underscore(camelCasedWord);
        String[] split = underScored.split("_");
        return Arrays.stream(split).map((val) -> capitalise(val)).collect(Collectors.joining(" "));
    }

    public String capitalise(String word) {
        if (word == null || word.length() < 1) {
            return word;
        }
        if (word.length() == 1) {
            return word.toUpperCase();
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


    public String pluralize(String word) {
        if (uncountables.contains(word.toLowerCase())) {
            return word;
        }
        return replaceWithFirstRule(word, plurals);
    }

    public String singularize(String word) {
        if (uncountables.contains(word.toLowerCase())) {
            return word;
        }
        return replaceWithFirstRule(word, singulars);
    }

    private String replaceWithFirstRule(String word, List<Pair<String, String>> ruleAndReplacements) {

        for (Pair<String, String> rar : ruleAndReplacements) {
            String rule = rar.getLeft();
            String replacement = rar.getRight();

            // Return if we find a match.
            Matcher matcher = Pattern.compile(rule, Pattern.CASE_INSENSITIVE).matcher(word);
            if (matcher.find()) {
                return matcher.replaceAll(replacement);
            }
        }
        return word;
    }

    public String tableize(String className) {
        return pluralize(underscore(className));
    }

    public String tableize(Class klass) {
        // Strip away package setName - we only want the 'base' class setName.
        String className = klass.getName().replace(klass.getPackage().getName() + ".", "");
        return tableize(className);
    }

    public static void plural(String rule, String replacement) {
        plurals.add(0, new Pair<>(rule, replacement));
    }

    public static void singular(String rule, String replacement) {
        singulars.add(0, new Pair<>(rule, replacement));
    }

    public static void irregular(String singular, String plural) {
        plural(singular, plural);
        singular(plural, singular);
    }

    public static void uncountable(String... words) {
        Collections.addAll(uncountables, words);
    }
}