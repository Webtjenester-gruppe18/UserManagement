package dtu.messagingutils;

import dtu.models.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}