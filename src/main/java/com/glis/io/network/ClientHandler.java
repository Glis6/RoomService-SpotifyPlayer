package com.glis.io.network;

import com.glis.DomainController;
import com.glis.message.Message;
import com.glis.message.SubscribeMessage;
import io.github.cdimascio.dotenv.Dotenv;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Objects;
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String[] subscriptions = Objects.requireNonNull(Dotenv.load().get("subscriptions")).split(";");
        final Message message = new SubscribeMessage(subscriptions);
        ctx.pipeline().writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        domainController.handleInput(msg, null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        logger.log(Level.INFO, "Closed channel.", cause);
    }
}
