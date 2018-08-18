package com.glis.io.network;

import com.glis.DomainController;
import com.glis.io.network.client.networktype.ClientBoth;
import com.glis.io.network.client.networktype.ClientDownStream;
import com.glis.io.network.client.networktype.ClientUpstream;
import com.glis.io.network.codec.SubscribeMessageEncoder;
import com.glis.io.network.input.library.MappedMessageLibrary;
import com.glis.io.network.input.library.MessageLibrary;
import com.glis.io.network.networktype.*;
import com.glis.message.AccessTokenMessage;
import com.glis.message.Message;
import com.glis.message.PlaybackMessage;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class NetworkConfiguration {
    @Bean
    public Message[] networkMessages() {
        return new Message[]{
                new PlaybackMessage(),
                new AccessTokenMessage()
        };
    }

    @Bean
    public MessageLibrary messageLibrary(Message... messages) {
        return new MappedMessageLibrary(messages);
    }

    @Bean
    public Upstream upstream(final DomainController domainController) {
        return new ClientUpstream(new ClientHandlerCustomNetworkTypeHandler(domainController));
    }

    @Bean
    public Downstream downstream(final DomainController domainController, final MessageLibrary messageLibrary) {
        return new ClientDownStream(new ClientHandlerCustomNetworkTypeHandler(domainController) {
            @Override
            public void doCustom(ChannelHandlerContext channelHandlerContext, LinkData linkData) {
                super.doCustom(channelHandlerContext, linkData);
                channelHandlerContext.pipeline().addLast(new SubscribeMessageEncoder());
            }
        }, messageLibrary);
    }

    @Bean
    public Both both(final Upstream upstream, final Downstream downstream) {
        return new ClientBoth(upstream, downstream);
    }
}
