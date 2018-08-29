package com.glis.io.network;

import com.glis.DomainController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link DomainController} to use.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} to use.
     */
    public ClientHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        domainController.handleInput(msg, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        //We ignore the closing message.
        if(!cause.getMessage().equals("An existing connection was forcibly closed by the remote host")) {
            logger.log(Level.WARNING, "An exception occurred on the channel. Closing channel.", cause);
        }
    }
}
