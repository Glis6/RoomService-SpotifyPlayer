package com.glis.io.network.networktype;

import com.glis.DomainController;
import com.glis.io.network.ClientHandler;
import com.glis.io.network.client.ClientAuthorizationHandler;
import com.glis.io.network.codec.AuthorizationEncoder;
import com.glis.io.network.codec.AuthorizationResponseDecoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.NoSuchElementException;

/**
 * @author Glis
 */
public class ClientHandlerCustomNetworkTypeHandler implements CustomNetworkTypeHandler {
    /**
     * The {@link DomainController} that is used for this instance.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} that is used for this instance.
     */
    public ClientHandlerCustomNetworkTypeHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doCustom(ChannelHandlerContext channelHandlerContext, LinkData linkData) {
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        try {
            pipeline.remove(AuthorizationEncoder.class);
            pipeline.remove(ClientAuthorizationHandler.class);
            pipeline.remove(AuthorizationResponseDecoder.class);
        } catch (NoSuchElementException ignored) {}
        pipeline.addLast(new ClientHandler(domainController));
    }
}
