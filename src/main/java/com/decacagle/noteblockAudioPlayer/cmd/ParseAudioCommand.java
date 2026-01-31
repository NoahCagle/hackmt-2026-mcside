package com.decacagle.noteblockAudioPlayer.cmd;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ParseAudioCommand implements BasicCommand {

    private HttpClient client;

    public ParseAudioCommand(HttpClient client) {
        this.client = client;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        if (strings.length == 0) {
            sender.sendRichMessage("<red>You must provide a path to a file!<br>Proper usage: /parseaudio <filepath>");
        } else {
            String path = strings[0];
            if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    path += " " + strings[i];
                }
            }
            sendParseRequest(sender, path);
        }
    }

    public void sendParseRequest(CommandSender sender, String path) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        HttpRequest request = requestBuilder
                .uri(URI.create("http://localhost:8000/parse"))
                .POST(HttpRequest.BodyPublishers.ofString(path))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String richResponse = response.body();
            if (response.statusCode() == 200) {
                richResponse = "<green>" + richResponse;
            } else {
                richResponse = "<red>" + richResponse;
            }
            sender.sendRichMessage(richResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
