package f_commandLine

import io.gatling.core.Predef._
import io.gatling.http.Predef._;

class B_RuntimeParam extends Simulation{

  //http protocol
  val httpProtocol = http
    .baseUrl("https://fakerestapi.azurewebsites.net")
    .contentTypeHeader("application/json")

  var USERS = System.getProperty("USERS","3").toInt
  var DURING = System.getProperty("DURING_PERIOD","5").toInt
  var MAXDURATION = System.getProperty("MAX_DURATION","7").toInt


  def getAllAuthors()={
    forever
    {
      exec(
        http("Get all authors")
          .get("/api/v1/Authors")
      )
    }

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
      constantUsersPerSec(USERS).during(DURING)
    ).protocols(httpProtocol)
  ).maxDuration(MAXDURATION)
}
