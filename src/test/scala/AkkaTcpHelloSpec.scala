import akka.actor.{ActorSystem, Props}
import akka.io.Tcp.{Close, Received, Write}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import akka.util.ByteString
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._

class AkkaTcpHelloSpec(_system: ActorSystem)
  extends TestKit(_system)
    with WordSpecLike
    with ImplicitSender
    with Matchers
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaTcpHello"))

  override def afterAll: Unit = {
    Await.result(system.terminate(), 10.seconds)
  }

  "the client handler" should {
    "repeat what the client says" in {
      val handler = TestActorRef(Props[SimplisticHandler])

      handler ! Received(ByteString("hello"))
      expectMsg(Write(ByteString("> hello")))
    }

    "close the connection when saying bye" in {
      val handler = system.actorOf(Props[SimplisticHandler])

      handler ! Received(ByteString("bye"))
      expectMsg(Write(ByteString("> bye!")))
      expectMsg(Close)
    }
  }
}
