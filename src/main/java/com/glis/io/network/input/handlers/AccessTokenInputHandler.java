package com.glis.io.network.input.handlers;

import com.glis.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.AccessTokenMessage;

import java.util.logging.Logger;

/**
 * @author Glis
 */
public class AccessTokenInputHandler implements InputHandler<AccessTokenMessage> {
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
    public AccessTokenInputHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof AccessTokenMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessTokenMessage convert(Object o) {
        return (AccessTokenMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(AccessTokenMessage input, MetaData metaData) {
        domainController.setAccessToken(input.getAccessToken());
        logger.info("Received and set a new access token.");
        return null;
    }
}
