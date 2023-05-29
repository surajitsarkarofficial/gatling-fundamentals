package a_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class B_ResponseCode extends Simulation{

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
        .check(status in (100,200,300))
        .check(status in (100 to 300))
        .check(status not 400)
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
