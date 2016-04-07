/* =========================================================================================
 * Copyright © 2013-2015 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

import sbt._
import Keys._

object Projects extends Build {
  import AspectJ._
  import Settings._
  import Dependencies._

  lazy val kamon = Project("kamon", file("."))
    .aggregate(kamonPlay25)
    .settings(basicSettings: _*)
    .settings(formatSettings: _*)
    .settings(noPublishing: _*)

  lazy val kamonPlay25 = Project("kamon-play-25", file("kamon-play-2.5.x"))
    .settings(basicSettings: _*)
    .settings(formatSettings: _*)
    .settings(aspectJSettings: _*)
    .settings(
      scalaVersion := "2.11.6",
      libraryDependencies ++=
        compile(play25, playWS25, kamonCore, kamonScala) ++
        provided(aspectJ, typesafeConfig) ++
        test(playTest25, akkaTestKit, slf4jApi, kamonTestKit, slf4jApi, logback, slf4jJul, slf4jLog4j))

  val noPublishing = Seq(publish := (), publishLocal := (), publishArtifact := false)
}
