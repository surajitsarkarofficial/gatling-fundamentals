package a_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class G_Reuse extends Simulation{

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
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
