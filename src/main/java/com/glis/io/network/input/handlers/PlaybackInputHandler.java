package com.glis.io.network.input.handlers;

import com.glis.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.PlaybackMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class PlaybackInputHandler implements InputHandler<PlaybackMessage> {
    /**
     * The {@link Logger} to use for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link DomainController} to use.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} to use.
     */
    public PlaybackInputHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof PlaybackMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaybackMessage convert(Object o) {
        return (PlaybackMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(PlaybackMessage input, MetaData metaData) {
        try {
            domainController.playSong(input.getPlaybackCommand());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Was not able to play the requested song '" + input.getPlaybackCommand() + "'.", e);
        }
        return null;
    }
}
