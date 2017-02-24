package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
  * Created by hendisantika on 24/02/17.
  * mvn -Dgatling.simulation.name=SyncSimulation gatling:execute
  */
abstract class AbstractSimulation extends Simulation{
  val ramUpTimeSecs = 60 seconds
  val testTimeSecs = 90 seconds
  val noOfUsers = 1000

  def baseName: String
  val requestName = baseName + "-request"
  val scenarioName = baseName + "-scenario"
  def URI: String

  val baseURL = "http://localhost:8080"

  val scn = scenario(scenarioName)
    .during(testTimeSecs){
      exec(
        http(requestName)
          .get(URI)
          .check(status.is(200))
      )
    }

  val httpConf = http.baseURL(baseURL)
  setUp(scn.inject(rampUsers(noOfUsers) over ramUpTimeSecs).protocols(httpConf))

}

class SyncSimulation extends AbstractSimulation{
  def baseName = "sync"
  def URI = "/sync"
}
