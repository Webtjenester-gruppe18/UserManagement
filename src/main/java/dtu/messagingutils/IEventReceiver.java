package dtu.messagingutils;

import dtu.models.Event;

/**
 * @author Emil Glimø Vinkel - s175107
 */

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}