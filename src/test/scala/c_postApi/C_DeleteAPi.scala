package c_postApi

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class C_DeleteAPi extends Simulation{

  //http protocol
  val httpProtocol = http
    .proxy(Proxy("localhost",8888))
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  def deleteAuthor()={
    exec(
        http("Delete Author")
          .delete("/api/v1/Authors/100")
      )
  }


  //scenario
  val scn = scenario("First Script")
    .exec(
      deleteAuthor()
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
