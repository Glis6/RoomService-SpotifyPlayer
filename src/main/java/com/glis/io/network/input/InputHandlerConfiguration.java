package com.glis.io.network.input;

import com.glis.DomainController;
import com.glis.io.network.input.handlers.AccessTokenInputHandler;
import com.glis.io.network.input.handlers.InputHandler;
import com.glis.io.network.input.handlers.PlaybackInputHandler;
import com.glis.util.HandlerLibrary;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class InputHandlerConfiguration {

    @Bean
    public SmartInitializingSingleton registerInputHandlers(final HandlerLibrary<InputHandler, Object> handlerLibrary, final DomainController domainController) {
        return () -> handlerLibrary.registerHandlers(new InputHandler[]{
                new PlaybackInputHandler(domainController),
                new AccessTokenInputHandler(domainController)
        });
    }
}
