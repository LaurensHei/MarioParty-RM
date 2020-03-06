package de.laurens.marioparty.language;

import de.laurens.marioparty.MarioParty;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Language {

    public String name;
    public HashMap<String, String> langIndex;

    public Language(String name) {
        this.name = name;
        this.langIndex = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setLangIndex(HashMap<String, String> langIndex) {
        this.langIndex = new HashMap<>(langIndex);
    }

    public String format(String value) {
        value = value.replaceAll("@CP@", MarioParty.ingame.size() + "");
        value = value.replaceAll("@MP@", "24");
        return value;
    }

    public String getContent(String code) {
        String value = langIndex.get(code);
        value = format(value);
        return value;
    }

    public String getContent(String code, Player player) {
        String value = format(langIndex.get(code));
        value = value.replaceAll("@PLAYER@", player.getName());
        return value;
    }

    public String getContent(String code, Object v) {
        String value = format(langIndex.get(code));
        value = value.replaceAll("@V@", v.toString());
        return value;
    }
}
