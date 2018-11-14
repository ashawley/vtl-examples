/** build.sbt -- sbt build file */

lazy val root = (project in file("."))
  .aggregate(
    app_example1,
    app_example2,
    context_example,
    logger_example,
    xmlapp_example,
    event_example
  )

lazy val app_example1    = (project in file("app_example1"))

lazy val app_example2    = (project in file("app_example2"))

lazy val context_example = (project in file("context_example"))

lazy val logger_example  = (project in file("logger_example"))

lazy val xmlapp_example  = (project in file("xmlapp_example"))

lazy val event_example   = (project in file("event_example"))

initialCommands in console :=
  """|println("WARNING: You ran root/console !!!")
     |println("WARNING: Did you mean to run <SUB-PROJECT>/console ???")
     |println("WARNING: (e.g. app_example1/console)")
     |""".stripMargin
