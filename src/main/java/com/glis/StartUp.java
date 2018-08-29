package com.glis;

import com.glis.io.network.client.ServerConnection;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

/**
 * @author Glis
 */
public class StartUp {
    /**
     * The starting point of the application.
     */
    public static void main(String[] args) throws Exception {
        Dotenv.configure()
                .directory(".env")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        try(ServerConnection serverConnection = new ServerConnection(
                Objects.requireNonNull(Dotenv.load().get("host")),
                Integer.parseInt(Objects.requireNonNull(Dotenv.load().get("port"))),
                Objects.requireNonNull(Dotenv.load().get("spotifyPlayer.clientId")),
                Objects.requireNonNull(Dotenv.load().get("spotifyPlayer.clientSecret")))) {
            serverConnection.connect();
        }
    }
}
