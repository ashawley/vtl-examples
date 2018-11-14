/** build.sbt -- sbt build file */

organization := "logger_example"
name := "vtl-examples"
version := "0.0"
scalaVersion := "2.12.7"
fork in run := true // Otherwise, hangs in sbt.
resolvers ++= Seq(
  Resolver.mavenLocal
)
libraryDependencies ++= Seq(
  "log4j"               % "log4j"           % "1.2.17",
  "org.apache.velocity" % "velocity"        % "1.7",             // !
  "junit"               % "junit"           % "4.11"   % "test",
  "org.hamcrest"        % "hamcrest-all"    % "1.3"    % "test",
  "org.mockito"         % "mockito-all"     % "1.10.5" % "test",
  "com.novocode"        % "junit-interface" % "0.11"   % "test"
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
  "-Xlint:unsound-match",
  // "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  // "-Ywarn-unused:params", // LoggerExample
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
