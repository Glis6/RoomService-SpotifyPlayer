package com.glis;

import com.glis.io.network.client.networktype.ClientDownStream;
import com.glis.io.network.input.InputHandlerConfiguration;
import com.glis.io.network.input.InputHandlerLibrary;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.input.dispatcher.PriorityInputDispatcher;
import com.glis.io.network.input.handlers.InputHandler;
import com.glis.io.network.input.library.MappedMessageLibrary;
import com.glis.io.network.input.library.MessageLibrary;
import com.glis.io.network.networktype.ClientHandlerCustomNetworkTypeHandler;
import com.glis.message.AccessTokenMessage;
import com.glis.message.Message;
import com.glis.message.PlaybackMessage;
import com.glis.util.HandlerLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Glis
 */
@Configuration
@Import({
        InputHandlerConfiguration.class
})
public class SpringConfiguration {
    @Bean
    public DomainController domainController(final InputDispatcher inputDispatcher) {
        return new DomainController(inputDispatcher);
    }

    @Bean
    public HandlerLibrary<InputHandler, Object> inputHandlerLibrary() {
        return new InputHandlerLibrary();
    }

    @Bean
    public InputDispatcher inputDispatcher(final HandlerLibrary<InputHandler, Object> handlerLibrary) {
        return new PriorityInputDispatcher(handlerLibrary);
    }
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
    public ClientDownStream downstream(final DomainController domainController, final MessageLibrary messageLibrary) {
        return new ClientDownStream(new ClientHandlerCustomNetworkTypeHandler(domainController), messageLibrary);
    }
}
