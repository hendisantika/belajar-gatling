package com.hendisantika.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

/**
  * Created by hendisantika on 17/02/17.
  * mvn -Dgatling.simulation.name=FindDriversSimulation clean gatling:execute
  *
  */
class FindDriversSimulation extends Simulation {

  //  val csvFeeder = csv("/home/hendisantika/IdeaProjects/gojek-gatling/src/test/resources/drivers.csv") // use a comma separator
  val csvFeeder = csv("drivers.csv") // use a comma separator

  val restEndPoint = "/drivers?latitude=${latitude}&longitude=${longitude}&radius=${radius}"

  before {
    println("***** My simulation is about to begin! *****")
  }

  after {
    println("***** My simulation has ended! ******")
  }

  val theHttpProtocolBuilder = http
    .baseURL("http://localhost:8080")

  val theScenarioBuilder = scenario("Find Driver Scenario")
    .feed(csvFeeder)
    .exec(
      http("findDriver")
        .get(restEndPoint))

  setUp(
    theScenarioBuilder.inject(atOnceUsers(20))
  ).protocols(theHttpProtocolBuilder)

}
