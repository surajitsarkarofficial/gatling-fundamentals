package d_feeder

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random;

class C_Template extends Simulation{

  //http protocol
  val httpProtocol = http
    .proxy(Proxy("localhost",8888))
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  var authorID = (101 to 999).iterator
  var bookID = (1001 to 1999).iterator

  val rnd = new Random()

  def generateRandomString(length:Int)={
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val customFeeder = Iterator.continually(
    Map(
      "id"-> authorID.next(),
      "bookId"->bookID.next(),
      "firstName"->"First-".concat(generateRandomString(5)),
      "lastName"->"Last-".concat(generateRandomString(3))
    )
  )



  def createAuthor()={

      repeat(5){
      feed(customFeeder)
        .exec(
          http("Create Author")
            .post("/api/v1/Authors")
            .body(ElFileBody("template.json"))
        )
      }
  }


  //scenario
  val scn = scenario("First Script")
    .exec(
      createAuthor()
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
