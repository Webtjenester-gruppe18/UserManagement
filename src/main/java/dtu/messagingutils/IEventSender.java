package dtu.messagingutils;

import dtu.models.Event;

/**
 * @author Emil Glimø Vinkel - s175107
 */

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
