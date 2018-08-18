package com.glis;

import com.glis.io.network.input.InputHandlerConfiguration;
import com.glis.io.network.input.InputHandlerLibrary;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.input.dispatcher.PriorityInputDispatcher;
import com.glis.io.network.input.handlers.InputHandler;
import com.glis.io.network.NetworkConfiguration;
import com.glis.util.HandlerLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Glis
 */
@Configuration
@Import({
        NetworkConfiguration.class,
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
}
