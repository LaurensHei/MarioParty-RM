package de.laurens.marioparty.timer;

import de.laurens.marioparty.MarioParty;
import org.bukkit.Bukkit;

public class LobbyTimer implements Runnable {

    int time = 60;
    boolean isRunning = false;


    @Override
    public void run() {
        if (this.time == -1) {
            isRunning = false;
            return;
        }
        isRunning = true;

        time--;

        if (this.time == 0) {
            this.finishCountdown();
        } else {
            if (time == 60 || time == 30 || time == 15 || time <= 10) {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.sendMessage(MarioParty.testLang.getContent("lobbycountdown.time", time));
                });
            }
        }
    }

    public void resetTime() {
        this.time = 60;
    }

    public void finishCountdown() {
        isRunning = false;
        Bukkit.getOnlinePlayers().forEach(players -> {
            players.sendMessage(MarioParty.testLang.getContent("lobbycountdown.startgame"));
        });
    }


}
