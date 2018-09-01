package com.glis.io.network.input.handlers;

import com.glis.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.StopPlaybackMessage;

/**
 * @author Glis
 */
public class StopPlaybackHandler implements InputHandler<StopPlaybackMessage> {
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
        domainController.stopPlayback();
        return null;
    }
}
