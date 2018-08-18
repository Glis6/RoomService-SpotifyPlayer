package com.glis.io.network.client.networktype;

import com.glis.ApplicationContextProvider;
import com.glis.DomainController;
import com.glis.io.network.ClientHandler;
import com.glis.io.network.client.ClientAuthorizationHandler;
import com.glis.io.network.codec.AuthorizationEncoder;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Glis
 */
public interface NetworkType {
    /**
     * Makes the network connect to the new handlers.
     *
     * @param channelHandlerContext The channel context.
     */
    void passThrough(final ChannelHandlerContext channelHandlerContext);

    /**
     * Does all the neccessary networking to connect to the new handlers and disconnect the old ones.
     *
     * @param channelHandlerContext The channel context.
     */
    default void link(final ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(AuthorizationEncoder.class);
        channelHandlerContext.pipeline().remove(ClientAuthorizationHandler.class);
        channelHandlerContext.pipeline().addLast(new ClientHandler(ApplicationContextProvider.getApplicationContext().getBean(DomainController.class)));
        passThrough(channelHandlerContext);
        channelHandlerContext.pipeline().fireChannelActive();
    }
}
