package a_fundamentals
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;
class A_FirstScript extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  //scenario
  val scn = scenario("First Script")
    .exec(
      http("Get All Authors")
        .get("/api/v1/Authors")
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
