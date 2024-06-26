package fplhn.udpm.identity.infrastructure.config.listener;

import org.springframework.context.ApplicationEvent;

public class ModuleInsertedEvent extends ApplicationEvent {

    public ModuleInsertedEvent(Object source) {
        super(source);
    }

}