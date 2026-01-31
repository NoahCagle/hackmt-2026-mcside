package com.decacagle.noteblockAudioPlayer;

import com.decacagle.noteblockAudioPlayer.cmd.ParseAudioCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.http.HttpClient;
import java.util.logging.Logger;

public final class NoteblockAudioPlayer extends JavaPlugin {

    public Logger logger;
    private HttpClient client;

    @Override
    public void onEnable() {
        this.logger = getLogger();

        client = HttpClient.newHttpClient();

        registerCommand("parseaudio", "Parses the given audio file to a format readable by the plugin", new ParseAudioCommand(client));

        logger.info("NoteblockAudioPlayer is starting!");

    }

    @Override
    public void onDisable() {

        logger.info("NoteblockAudioPlayer is shutting down!");

    }
}
