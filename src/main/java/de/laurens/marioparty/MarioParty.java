package de.laurens.marioparty;

import de.laurens.marioparty.commands.SetlobbyCMD;
import de.laurens.marioparty.commands.SetspawnCMD;
import de.laurens.marioparty.jumpandrun.JnrCMD;
import de.laurens.marioparty.jumpandrun.JumpListener;
import de.laurens.marioparty.language.Language;
import de.laurens.marioparty.language.LanguageManager;
import de.laurens.marioparty.listeners.BuildListener;
import de.laurens.marioparty.listeners.ClickListener;
import de.laurens.marioparty.listeners.InteractListener;
import de.laurens.marioparty.listeners.PlayerListener;
import de.laurens.marioparty.minigame.MinigameManager;
import de.laurens.marioparty.skin.SkinManager;
import de.laurens.marioparty.timer.LobbyTimer;
import de.laurens.marioparty.vote.VoteManager;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;


public class MarioParty extends JavaPlugin {

    @Getter
    public static MarioParty instance;
    public static GameState gameState;

    public LanguageManager languageManager;
    public MinigameManager minigameManager;

    public static ArrayList<Language> languages = new ArrayList<>();
    public static ArrayList<Player> ingame = new ArrayList<>();

    @Getter
    public SkinManager skinManager;

    @Getter
    public VoteManager voteManager;

    @Getter
    public LobbyTimer lobbyTimer;

    @Getter
    public File configFolder = new File("plugins//MarioParty");

    @Getter
    public File jumpFile = new File(configFolder.getPath() + "//JumpAndRun.yml");
    @Getter
    public Location jumpStartLocation;
    @Getter
    public Location jumpEndLocation;

    @Getter
    public File lobbyLocationFile = new File(configFolder.getPath() + "//Lobby.yml");
    @Getter
    public Location lobbyLocation;

    //FIXME
    public static Language testLang;

    public void onLoad() {
        languageManager = new LanguageManager();
        for (Language language : languages) {
            if (language.getName().equals("Deutsch")) {
                this.testLang = language;
            }

            System.out.println("Language load: " + language.getName());
        }

    }


    public void onEnable() {
        instance = this;
        gameState = GameState.LOBBY;

        lobbyTimer = new LobbyTimer();

        skinManager = new SkinManager();

        voteManager = new VoteManager();

        lobbyLocation = loadLobbyLocation();
        jumpStartLocation = loadJumpStartLocation();
        jumpEndLocation = loadJumpEndLocation();

        minigameManager = new MinigameManager(new File("plugins//MarioParty//Minigames"));

        Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        if (jumpStartLocation != null && jumpEndLocation != null) {
            Bukkit.getPluginManager().registerEvents(new JumpListener(), this);
        }

        getCommand("setlobby").setExecutor(new SetlobbyCMD());
        getCommand("jnr").setExecutor(new JnrCMD());
        getCommand("setspawn").setExecutor(new SetspawnCMD());
        System.out.println("Enabled MarioParty.");
    }

    public Location loadLobbyLocation() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(lobbyLocationFile);
        if (cfg.getString("Lobby.World") == null) {
            System.err.println("Could not load lobbyLocation");
            return Bukkit.getWorlds().get(0).getSpawnLocation();
        }
        boolean loaded = false;
        for (World w : Bukkit.getServer().getWorlds()) {
            System.err.println(w.getName() + " is loaded");
            if (w.getName().equals(cfg.getString("Lobby.World"))) {
                loaded = true;
            }
        }
        Bukkit.getServer().createWorld(new WorldCreator(cfg.getString("Lobby.World")));
        World world = Bukkit.getWorld(cfg.getString("Lobby.World"));

        double x = cfg.getDouble("Lobby.X");
        double y = cfg.getDouble("Lobby.Y");
        double z = cfg.getDouble("Lobby.Z");
        float yaw = (float) cfg.getDouble("Lobby.Yaw");
        float pitch = (float) cfg.getDouble("Lobby.Pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public Location loadJumpStartLocation() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(jumpFile);
        if (cfg.getString("Jump.Start.World") == null) {
            return null;
        }
        World world = Bukkit.getWorld(cfg.getString("Jump.Start.World"));
        double x = cfg.getDouble("Jump.Start.X");
        double y = cfg.getDouble("Jump.Start.Y");
        double z = cfg.getDouble("Jump.Start.Z");
        float yaw = (float) cfg.getDouble("Jump.Start.Yaw");
        float pitch = (float) cfg.getDouble("Jump.Start.Pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public Location loadJumpEndLocation() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(jumpFile);
        if (cfg.getString("Jump.End.World") == null) {
            return null;
        }
        World world = Bukkit.getWorld(cfg.getString("Jump.End.World"));
        double x = cfg.getDouble("Jump.End.X");
        double y = cfg.getDouble("Jump.End.Y");
        double z = cfg.getDouble("Jump.End.Z");
        float yaw = (float) cfg.getDouble("Jump.End.Yaw");
        float pitch = (float) cfg.getDouble("Jump.End.Pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void giveLobbyItems(Player player) {
        player.getInventory().clear();
        ItemStack votemap = new ItemStack(Material.PAPER);
        ItemMeta votemapmeta = votemap.getItemMeta();
        votemapmeta.setDisplayName(MarioParty.testLang.getContent("voteitem"));
        votemap.setItemMeta(votemapmeta);
        player.getInventory().setItem(0, votemap);

        ItemStack skin = new ItemStack(Material.GOLD_INGOT);
        ItemMeta skinmeta = skin.getItemMeta();
        skinmeta.setDisplayName(MarioParty.testLang.getContent("skinitem"));
        skin.setItemMeta(skinmeta);
        player.getInventory().setItem(2, skin);
    }


    public void onDisable() {
        System.out.println("Disabled MarioParty.");
    }
}
