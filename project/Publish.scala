/* =========================================================================================
 * Copyright © 2013-2014 the kamon project <http://kamon.io/>
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
import sbt.Keys._

object Publish {

  lazy val settings = Seq(
    crossPaths := true,
    pomExtra := kamonPomExtra,
    publishTo := {
      val nexus = "http://publish-nexus.agiledigital.com.au/nexus/"
      if (version.value.trim.endsWith("SNAPSHOT")) {
        Some("snapshots" at nexus + "content/repositories/snapshots")
      } else {
        Some("releases" at nexus + "content/repositories/releases")
      }
    },
    organization := "io.kamon",
    pomIncludeRepository := { x => false },
    publishMavenStyle := true,
    publishArtifact in Test := false
  )

  def kamonPomExtra = {
    <url>http://kamon.io</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/agiledigital/kamon-play-2.5.x.git</url>
        <connection>scm:git:git@github.com:agiledigital/kamon-play-2.5.x.git</connection>
      </scm>
      <developers>
        <developer><id>dspasojevic</id><name>Daniel Spasojevic</name></developer>
      </developers>
  }
}
