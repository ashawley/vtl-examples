Velocity template language examples
===================================

These examples are from [Apache Velocity Project](https://velocity.apache.org/)'s
source repository.  They are converted from Java and shell scripts to
[Scala](https://scala-lang.org/) and can be run from the
[sbt](https://scala-sbt.org/) build tool.

Starting sbt:

```
$ sbt
```

You can compile all the examples from the sbt console:

```
> compile
```

How to run each of the examples is shown below.

app_example1 
------------

This simple example shows how to use the Velocity Template Engine
in a standalone program.

```
> app_example1/run sample.vm
```

app_example2
------------

Another simple example showing how to use Velocity in a standalone
program.  This examples uses the `org.apache.velocity.app.Velocity`
application utility class, which provides a few convenient methods for
application programmers.

```
> app_example2/run
```

logger_example
--------------

This 'toy' example shows how to use the Velocity logger interface
to have any class function as a logging facility.  

```
> logger_example/runMain LoggerExample
```

This class demonstrates how to configure Velocity to use an 
existing log4j logger for logging.

You will see that the log4j output will contain the output from
velocity's initialization.

```
> logger_example/runMain Log4jLoggerExample
```

context_example
---------------

This is a demonstration of 2 different context implementations:

 - a context implementation that uses a database as the storage.
   Use the `DBContextTest` program to test.  See the DBContextTest.scala file for the 
   db table info.  Unsupported demonstration code.  You may need to futz with 
   it to get it to work in your environment.
 - a context implementation that uses a `TreeMap` for storage.  Very simple.

```
> context_example/run dbtest.vm
```

event_example
-------------

This simple example demonstrates the event handling features of 
the Velocity Template Engine.

```
> event_example/run
```

xmlapp_example 
--------------

Another simple example showing how to use Velocity in a standalone
program.  This example shows how to use Velocity to access XML data
directly from an XML input file within the template.  It also
demonstrates velocimacro recursion.

```
> xmlapp_example/run xml.vm
```

Provenance
----------

These examples are based on checking out from version 1.7:

```
git svn clone -r1035365
              --ignore-paths="^(branches|tags)"
	      --stdlayout
	      http://svn.apache.org/repos/asf/velocity/engine/ ./velocity
```
