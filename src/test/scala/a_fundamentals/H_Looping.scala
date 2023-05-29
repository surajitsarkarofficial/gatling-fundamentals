package a_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class H_Looping extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  def getAllAuthors()={
    repeat(3){
      exec(
        http("Get all authors")
          .get("/api/v1/Authors")
      )
    }
  }

  def getAuthorById()={
    repeat(5,"counter"){
      exec(
        http("Get Author By Id - #{counter}")
          .get("/api/v1/Authors/#{counter}")
      )
    }
  }

  //scenario
  val scn = scenario("First Script")
    .exec(
      repeat(2){
        getAllAuthors()
      }

    )
    .exec(
      getAuthorById()
    )

  //setup
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
