package ee.winni.plugins.languages;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LangCommand extends Command {

    public LangCommand() {
        super("lang",null,"l");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length==0){
            sender.sendMessage(Languages.instance.getPlayerString(sender.getName(),"noinput")
            .replace("%lang%",Languages.instance.getLang(sender.getName()))
            .replace("%langs%",Languages.languagesD));
            return;
        }

        String lang = dealLang(args[0].toLowerCase());

        if(!Languages.languages.contains(lang)){
            sender.sendMessage(Languages.instance.getPlayerString(sender.getName(),"donothave").replace("%lang%",lang));
            return;
        }

        if(Languages.instance.setLang(sender.getName(),lang))
            sender.sendMessage(Languages.instance.getPlayerString(sender.getName(),"success").replace("%lang%",lang));
        else
            sender.sendMessage(Languages.instance.getPlayerString(sender.getName(),"failed").replace("%lang%",lang));

    }

    private static String dealLang(String pre){

        switch (pre){
            case "zh_cn":case "cn":case "中文": case "简体中文":return "chinese";
            case "zh_tw": case "tw": case "繁体中文":return "chineset";
            case "en": case "en_us": case "en_gb": return "english";
            case "jp": case "日本語":return "japanese";
            default: return pre;
        }
    }
}
