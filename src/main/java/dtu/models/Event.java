package dtu.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Mathias Hansen s175112
 */

@Getter
@Setter
@NoArgsConstructor
public class Event implements Serializable {

 private EventType type;
 private Object object;
 private String routingKey;

 public Event (EventType type, Object object, String routingKey){
  this.object = object;
  this.type = type;
  this.routingKey = routingKey;
 }
}
