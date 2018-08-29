package com.glis;

import com.glis.io.network.client.ServerConnection;

import java.util.Optional;

/**
 * @author Glis
 */
public class StartUp {
    /**
     * The starting point of the application.
     */
    public static void main(String[] args) throws Exception {
        try (ServerConnection serverConnection = new ServerConnection(
                Optional.ofNullable(System.getenv("HOST")).orElseThrow(() -> new Exception("HOST is not set in the environment")),
                Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElseThrow(() -> new Exception("PORT is not set in the environment"))),
                Optional.ofNullable(System.getenv("LEDCONTROLLER_CLIENTID")).orElseThrow(() -> new Exception("LEDCONTROLLER_CLIENTID is not set in the environment")),
                Optional.ofNullable(System.getenv("LEDCONTROLLER_CLIENTSECRET")).orElseThrow(() -> new Exception("LEDCONTROLLER_CLIENTSECRET is not set in the environment"))
        )) {
            serverConnection.connect();
        }
    }
}
