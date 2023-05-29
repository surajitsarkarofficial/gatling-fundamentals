package a_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class F_PrintResponseBodyAndSession extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  //scenario
  val scn = scenario("First Script")
    .exec(
      http("Get Author By Id")
        .get("/api/v1/Authors/24")
        .check(jsonPath("$.firstName") is "First Name 24")
        .check(jsonPath("$.id").saveAs("authorID"))
        .check(bodyString.saveAs("responseBody"))
    )
    .exec(
      {
        session=>println(session("responseBody").as[String]);
          session
      }

    )
    .exec(
      {
        session=>println(session);
          session
      }
    )


  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
