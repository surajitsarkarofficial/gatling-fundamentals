package d_feeder

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class A_CSVFeeder extends Simulation{

  //http protocol
  val httpProtocol = http
    .proxy(Proxy("localhost",8888))
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  val cSVFeeder = csv("authordetails.csv").random

  def createAuthor()={

      repeat(7){
      feed(cSVFeeder)
        .exec(
          http("Create Author")
            .post("/api/v1/Authors")
            .body(StringBody("{\n  \"id\": #{id},\n  \"idBook\": #{bookId},\n  \"firstName\": \"#{firstName}\",\n  \"lastName\": \"#{lastName}\"\n}"))
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
