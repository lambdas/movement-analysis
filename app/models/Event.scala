package models

trait Event {
  val deviceId: String
  val unixTime: Long = Event.currentUnixTime
}

object Event {

  protected var events = List[Event]()

  def create(event: Event) {
    events = event :: events
  }

  def currentUnixTime: Long = System.currentTimeMillis / 1000L

}
