package c_postApi

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class B_PutUsingJsonFile extends Simulation{

  //http protocol
  val httpProtocol = http
    .proxy(Proxy("localhost",8888))
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  def updateAuthor()={
    exec(
        http("Create Author")
          .put("/api/v1/Authors/100")
          .body(ElFileBody("updateAuthor.json"))
      )
  }


  //scenario
  val scn = scenario("First Script")
    .exec(
      updateAuthor()
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
