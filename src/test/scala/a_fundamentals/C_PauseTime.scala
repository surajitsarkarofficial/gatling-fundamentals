package a_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt;

class C_PauseTime extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  //scenario
  val scn = scenario("Verify Response Code")
    .exec(
      http("Get All Authors")
        .get("/api/v1/Authors")
        .check(status is 200)
    )
    .pause(2)
    .exec(
      http("Get All Authors 2")
        .get("/api/v1/Authors")
        .check(status is 200)
    )
    .pause(3,5)
    .exec(
    http("Get All Authors 3")
      .get("/api/v1/Authors")
      .check(status is 200)
  )
    .pause(4000.milliseconds)

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
