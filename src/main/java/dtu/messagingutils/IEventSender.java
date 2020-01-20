package dtu.messagingutils;

import dtu.models.Event;


public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
