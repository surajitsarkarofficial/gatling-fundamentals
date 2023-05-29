package e_loadSimulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class A_BasicLoadSimulation extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  def getAllAuthors()={
    exec(
        http("Get all authors")
          .get("/api/v1/Authors")
      )
  }

  //scenario
  val scn = scenario("First Script")
    .exec(

        getAllAuthors()

    )

  //setup
  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(5),
      rampUsers(10).during(10)
    ).protocols(httpProtocol)
  )
}
