package com.springbank.user.query.api.handlers;


import com.cognizant.core.user.events.UserRegisteredEvent;
import com.cognizant.core.user.events.UserRemovedEvent;
import com.cognizant.core.user.events.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);
    void on(UserUpdatedEvent event);
    void on(UserRemovedEvent event);
}
