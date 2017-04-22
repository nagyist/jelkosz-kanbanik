package com.googlecode.kanbanik.db

import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import com.googlecode.kanbanik.model.DocumentField

trait HasEvents extends HasMongoConnection {

  type MappableEntity = {
    def asMap(): Map[String, Any]
  }

  object Event extends DocumentField {
    val entityId = Value("entityId")
    val timestamp = Value("timestamp")
  }

  object EventType extends Enumeration {
    val TaskMoved = Value("TaskMoved")
    val TaskChanged = Value("TaskChanged")
    val TaskCreated = Value("TaskCreated")
    val TaskDeleted = Value("TaskDeleted")
  }

  def publish(eventType: EventType.Value, specific: Map[String, Any]) {
    // todo publish nothing if the specific is empty
    // todo remove the "version" since it is not needed
    val renamedId = specific.map(
      (e: (String, Any)) => if (e._1 == Event.id.toString) Event.entityId.toString -> e._2 else e
    )

    val common = Map(
      Event.id.toString -> new ObjectId,
      Event.timestamp.toString -> System.currentTimeMillis()
    )

    using(createConnection) { conn =>
      coll(conn, Coll.Events) += MongoDBObject((common ++ renamedId).toList)
    }
  }

  def diff(oldVal: Map[String, Any], newVal: Map[String, Any]): Map[String, Any] =
    // todo always retain id
    // todo it for some reason returns the old values not the new ones
    oldVal.filter((e: (String, Any)) => newVal.get(e._1).getOrElse(None) != e._2)

  def diff(oldVal: MappableEntity, newVal: MappableEntity): Map[String, Any] =
    diff(oldVal.asMap(), newVal.asMap())

}
