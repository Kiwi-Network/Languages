package ee.winni.plugins.languages;

public interface LanguagesInterface {

    String getLang(String player);

    boolean setLang(String player, String lang);

    String getString(String lang, String key);

    String getPlayerString(String player, String key);
}
