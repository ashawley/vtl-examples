/** build.sbt -- sbt build file */

organization := "xmlapp_example"
name := "vtl-examples"
version := "0.0"
scalaVersion := "2.12.7"
fork in run := true // Otherwise, hangs in sbt.
resolvers ++= Seq(
  Resolver.mavenLocal
)
libraryDependencies ++= Seq(
  "org.slf4j"           % "slf4j-api"            % "1.7.25",
  "org.slf4j"           % "slf4j-simple"         % "1.7.25",
  "org.jdom"            % "jdom"                 % "1.1.3",
  "org.apache.velocity" % "velocity-engine-core" % "2.0"
)
scalacOptions ++= Seq(
  // https://tpolecat.github.io/2017/04/25/scalac-flags.html
  "-deprecation",
  "-encoding", "utf-8",
  "-explaintypes",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xcheckinit",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint:adapted-args",
  // "-Xlint:by-name-right-associative",
  "-Xlint:constant",
  "-Xlint:delayedinit-select",
  "-Xlint:doc-detached",
  "-Xlint:inaccessible",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:nullary-override",
  "-Xlint:nullary-unit",
  "-Xlint:option-implicit",
  "-Xlint:package-object-classes",
  "-Xlint:poly-implicit-overload",
  "-Xlint:private-shadow",
  "-Xlint:stars-align",
  "-Xlint:type-parameter-shadow",
  // "-Xlint:unsound-match",
  // "-Yno-adapted-args",
  // "-Ypartial-unification",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  // "-Ywarn-inaccessible",
  // "-Ywarn-infer-any",
  // "-Ywarn-nullary-override",
  // "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:params",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
  "-Ywarn-value-discard",
  "-Yno-predef",
  "-Yno-imports"
)
scalacOptions in (Compile, console) --= Seq(
  "-Xfatal-warnings",
  "-Ywarn-unused:imports",
  "-Yno-predef",
  "-Yno-imports"
)
