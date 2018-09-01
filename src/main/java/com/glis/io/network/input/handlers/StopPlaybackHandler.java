package com.glis.io.network.input.handlers;

import com.glis.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.StopPlaybackMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class StopPlaybackHandler implements InputHandler<StopPlaybackMessage> {
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
    public StopPlaybackHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof StopPlaybackMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopPlaybackMessage convert(Object o) {
        return (StopPlaybackMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(StopPlaybackMessage input, MetaData metaData) {
        try {
            domainController.stopPlayback();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Was not able pause the user playback.", e);
        }
        return null;
    }
}
