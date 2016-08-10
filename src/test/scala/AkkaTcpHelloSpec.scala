import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import akka.actor.{Actor, ActorSystem, Props}
import akka.io.Tcp.{Received, Write}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration._

class AkkaTcpHelloSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with Matchers
  with FlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaTcpHello"))

  override def afterAll: Unit = {
    Await.result(system.terminate(), 10.seconds)
  }

  "the client handler" should "repeat what the client says" in {
    val handler = TestActorRef(Props[SimplisticHandler])

    within(500.milliseconds) {
      handler ! Received(ByteString("hello"))
      expectMsg(Write(ByteString("> hello")))
    }
  }
}
