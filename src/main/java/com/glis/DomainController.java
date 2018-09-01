package com.glis;

import com.glis.exceptions.InvalidSongException;
import com.glis.exceptions.UnknownHandlerException;
import com.glis.io.network.input.MetaData;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.google.gson.JsonArray;
import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class DomainController {
    /**
     * The string that is used to split the command in multiple parts.
     */
    private final static String COMMAND_SPLIT_STRING = ";";

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link SpotifyApi} that we're using.
     */
    private final SpotifyApi spotifyApi = SpotifyApi
            .builder()
            .setClientId(System.getenv("SPOTIFY_CLIENTID"))
            .setClientSecret(System.getenv("SPOTIFY_CLIENTSECRET"))
            .build();

    /**
     * The {@link InputDispatcher} that is being used to handle the input.
     */
    private final InputDispatcher inputDispatcher;

    /**
     * @param inputDispatcher The {@link InputDispatcher} that is being used to handle the input.
     */
    @Autowired
    public DomainController(InputDispatcher inputDispatcher) {
        this.inputDispatcher = inputDispatcher;
    }

    /**
     * Sends the input through to all the handlers that are interested in it.
     *
     * @param input The input that is being send to the correct handler.
     * @param metaData The metadata to the message.
     */
    public void handleInput(Object input, MetaData metaData) throws UnknownHandlerException {
        inputDispatcher.dispatchToHandler(input, metaData);
    }

    /**
     * @param accessToken The access token to when making Spotify requests.
     */
    public void setAccessToken(final String accessToken) {
        spotifyApi.setAccessToken(accessToken);
    }

    /**
     * @param songCommand Plays a defined song. It can be decoded as either a track or an album.
     */
    public void playSong(final String songCommand) throws Exception {
        logger.info("Attempting to play the song with command '" + songCommand + "'.");
        if(songCommand == null) {
            throw new InvalidSongException("The song cannot be null.");
        }
        //First we check if it takes a track.
        if(songCommand.contains("spotify:track:")) {
            final String multipleSongCommands[] = songCommand.split(COMMAND_SPLIT_STRING);
            final JsonArray jsonArray = new JsonArray();
            for (String multipleSongCommand : multipleSongCommands) {
                jsonArray.add(multipleSongCommand);
            }
            spotifyApi.startResumeUsersPlayback().uris(jsonArray).build().execute();
            logger.info("String matches track, sending command.");
            return;
        }

        //If it does not then we'll accept an album.
        if(songCommand.contains("spotify:album:")) {
            spotifyApi.startResumeUsersPlayback().context_uri(songCommand).build().execute();
            logger.info("String matches album, sending command.");
            return;
        }

        //If nothing then we send an exception.
        throw new InvalidSongException("The song doesn't fit any format.");
    }

    /**
     * Stops the playback of the current song.
     */
    public void stopPlayback() throws Exception {
        spotifyApi.pauseUsersPlayback().build().execute();
        logger.info("Stopped user playback.");
    }
}
