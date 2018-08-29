package com.glis;

import com.glis.io.network.client.ServerConnection;
import io.github.cdimascio.dotenv.Dotenv;

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

        try(ServerConnection serverConnection = new ServerConnection()) {
            serverConnection.connect();
        }
    }
}
