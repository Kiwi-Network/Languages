package ee.winni.plugins.languages;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Languages extends Plugin implements LanguagesInterface{

    public static HashMap<String,Configuration> messages = new HashMap<>();
    public static Set<String> languages = new HashSet<String>();
    public static String languagesD = "";

    public static Configuration langs;
    public static File langs_;

    public static Languages instance;

    @Override
    public void onEnable() {
        // Plugin startup logic


        instance = this;
        this.getProxy().getPluginManager().registerCommand(this,new LangCommand());

        try {

            for(File a : getDataFolder().listFiles()){

                if(a.getName().startsWith("messages")) {
                    messages.put(a.getName(),ConfigurationProvider.getProvider(YamlConfiguration.class).load(a));
                }
            }

            langs_ = new File(ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(),"config.yml")).getString("config"));
            langs = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langs_);
            for(String a : langs.getStringList("types")){
                languages.add(a);
                languagesD+=a+", ";
            }

            if(languagesD.endsWith(", "))
                languagesD = languagesD.substring(0,languagesD.length()-2);

            System.out.print(messages.keySet());


        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getLang(String player) {
        return langs.getString("players."+player,"chinese");
    }

    @Override
    public boolean setLang(String player, String lang) {
        langs.set("players."+player,lang);
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(langs,langs_);
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getString(String lang, String key) {

        if(messages.containsKey("messages_"+lang+".yml")){
            return messages.get("messages_"+lang+".yml").getString(key);
        }
        return getString("chinese",key);
    }

    @Override
    public String getPlayerString(String player, String key) {
        return getString(getLang(player),key);
    }
}
