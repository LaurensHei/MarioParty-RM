package de.laurens.marioparty.language;

import de.laurens.marioparty.MarioParty;

import java.io.*;
import java.util.HashMap;

public class LanguageManager {

    String line;

    public LanguageManager() {
        loadLanguages();
    }

    private void loadLanguages() {
        File folder = new File("plugins//MarioParty//Languages");
        if (!folder.exists()) {
            System.out.println("NICHT GEFUNDEN");
            folder.mkdirs();
        }
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(".lang")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

                    String name = "";
                    HashMap<String, String> langIndex = new HashMap<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("lang=")) {
                            name = line.replace("lang=", "");
                        } else {
                            String[] index = line.split("=");
                            langIndex.put(index[0], index[1]);
                        }
                    }

                    Language language = new Language(name);
                    language.setLangIndex(langIndex);
                    MarioParty.languages.add(language);
                    System.out.println("Added Language");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

}
