package com.glis.io.network;

import com.glis.DomainController;
import com.glis.io.network.client.networktype.ClientDownStream;
import com.glis.io.network.input.library.MappedMessageLibrary;
import com.glis.io.network.input.library.MessageLibrary;
import com.glis.io.network.networktype.ClientHandlerCustomNetworkTypeHandler;
import com.glis.io.network.networktype.Downstream;
import com.glis.message.AccessTokenMessage;
import com.glis.message.Message;
import com.glis.message.PlaybackMessage;
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
    public Downstream downstream(final DomainController domainController, final MessageLibrary messageLibrary) {
        return new ClientDownStream(new ClientHandlerCustomNetworkTypeHandler(domainController), messageLibrary);
    }
}
