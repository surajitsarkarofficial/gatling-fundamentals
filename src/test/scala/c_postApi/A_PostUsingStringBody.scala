package c_postApi

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class A_PostUsingStringBody extends Simulation{

  //http protocol
  val httpProtocol = http
    .proxy(Proxy("localhost",8888))
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  def createAuthor()={
    exec(
        http("Create Author")
          .post("/api/v1/Authors")
          .body(StringBody("{\n  \"id\": 100,\n  \"idBook\": 200,\n  \"firstName\": \"Surajit\",\n  \"lastName\": \"Sarkar\"\n}"))
      )
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
