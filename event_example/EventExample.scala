/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/event_example/EventExample.java
 * Geir Magnusson Jr.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import scala.Array
import scala.Boolean
import scala.Int
import scala.Predef.String
import scala.Unit
import scala.Console

import java.lang.Exception
import java.lang.Object
import java.lang.Throwable
import java.io.StringWriter
import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext

import org.apache.velocity.exception.ParseErrorException
import org.apache.velocity.exception.MethodInvocationException

import org.apache.velocity.runtime.log.LogChute
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.RuntimeServices

import org.apache.velocity.app.event.EventCartridge
import org.apache.velocity.app.event.ReferenceInsertionEventHandler
import org.apache.velocity.app.event.MethodExceptionEventHandler
import org.apache.velocity.app.event.NullSetEventHandler

/**
  *   This class is a simple demonstration of how the event handling
  *   features of the Velocity Servlet Engine are used.  It uses a
  *   custom logger as well to check the log message stream
  *   when testing the NullSetEventHandler
  */
object EventExample
    extends ReferenceInsertionEventHandler
    with NullSetEventHandler
    with MethodExceptionEventHandler
    with LogChute {

  private var logOutput = false
  private var exceptionSwitch = false

  def main( args: Array[String] ): Unit = {

    try {
      /*
       *  this class implements the LogSystem interface, so we
       *  can use it as a logger for Velocity
       */

      Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this)
      Velocity.init()
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("Problem initializing Velocity : " + e )
    }

    /*
     *  lets make a Context and add some data
     */
    val context = new VelocityContext()

    context.put("name", "Velocity")

    /*
     *  Now make an event cartridge, register all the
     *  event handlers (at once) and attach it to the
     *  Context
     */
    val ec = new EventCartridge()
    ec.addEventHandler(this)
    ec.attachToContext( context )

    try {

      /*
       *  lets test each type of event handler individually
       *  using 'dynamic' templates
       *
       *  First, the reference insertion handler
       */
      Console.out.println("")
      Console.out.println("Velocity Event Handling Demo")
      Console.out.println("============================")
      Console.out.println("")

      var s = "The word 'Velocity' should be bounded by emoticons :  $name."

      var w = new StringWriter()
      Velocity.evaluate( context, w, "mystring", s )

      Console.out.println("Reference Insertion Test : ")
      Console.out.println("   " +  w.toString())
      Console.out.println("")

      /*
       *  using the same handler, we can deal with
       *  null references as well
       */

      s = "There is no reference $floobie, $nullvalue or anything in the brackets : >$!silentnull<"

      w = new StringWriter()
      Velocity.evaluate( context, w, "mystring", s )

      Console.out.println("Reference Insertion Test with null references : ")
      Console.out.println("   " + w.toString())
      Console.out.println("")

      /*
       *  now lets test setting a null value - this test
       *  should result in *no* log output.
       *  Turn on the logger output.
       */

      logOutput = true

      s = "#set($settest = $NotAReference)"
      w = new StringWriter()

      Console.out.println("NullSetEventHandler test : " )
      Console.out.print("      There should be nothing between >")
      Velocity.evaluate( context, w, "mystring", s )
      Console.out.println("< the brackets.")
      Console.out.println("")

      /*
       *  now lets test setting a null value - this test
       *  should result in log output.
       */

      s = "#set($logthis = $NotAReference)"
      w = new StringWriter()

      Console.out.println("NullSetEventHandler test : " )
      Console.out.print("     There should be a log message between >")
      Velocity.evaluate( context, w, "mystring", s )
      Console.out.println("< the brackets.")
      Console.out.println("")

      logOutput = false

      /*
       *  finally, we test a method exception event - we do this
       *  by putting this class in the context, and calling
       *  a method that does nothing but throw an exception.
       *  we use a little switch to turn the event handling
       *  on and off
       *
       *  Note also how the reference insertion process
       *  happens as well
       */

      exceptionSwitch = true

      context.put("this", this )

      s = " $this.throwException()"
      w = new StringWriter()

      Console.out.println("MethodExceptionEventHandler test : " )
      Console.out.print("    This exception will be controlled and converted into a string : ")
      Velocity.evaluate( context, w, "mystring", s )
      Console.out.println("   " + w.toString())
      Console.out.println("")

      /*
       *  now, we turn the switch off, and we can see that the
       *  exception will propgate all the way up here, and
       *  wil be caught by the catch() block below
       */

      exceptionSwitch = false

      s = " $this.throwException()"
      w = new StringWriter()

      Console.out.println("MethodExceptionEventHandler test : " )
      Console.out.println("    This exception will NOT be controlled. "
        + " The next thing you should see is the catch() output ")
      Velocity.evaluate( context, w, "mystring", s )
      Console.out.println("If you see this, it didn't work!")

    } catch {
      case pee: ParseErrorException =>
        /*
         * thrown if something is wrong with the
         * syntax of our template string
         */
        Console.out.println("ParseErrorException : " + pee )
      case mee: MethodInvocationException =>
        /*
         *  thrown if a method of a reference
         *  called by the template
         *  throws an exception. That won't happen here
         *  as we aren't calling any methods in this
         *  example, but we have to catch them anyway
         */
        Console.out.println("   Catch Block : MethodInvocationException : " + mee )
      case e: java.lang.Throwable =>
        Console.out.println("Exception : " + e )
    }
  }

  /**
    *  silly method to throw an exception to demonstrate
    *  the method invocation exception event handling
    */
  def throwException(): Unit = {
    throw new Exception("Hello from throwException()")
  }

  /**
    *  Event handler for when a reference is inserted into the output stream.
    */
  def referenceInsert( reference: String, value: Object  ): Object = {
    /*
     *  if we have a value
     *  lets decorate the reference with emoticons
     */

    var s: String = null

    if( value != null ) {
      s = " ) " + value.toString() + " :-)"
    } else {
      /*
       * we only want to deal with $floobie - anything
       *  else we let go
       */
      if ( reference.equals("floobie") ) {
        s = "<no floobie value>"
      }
    }
    return s
  }

  /**
    *  Event handler for when the right hand side of
    *  a #set() directive is null, which results in
    *  a log message.  This method gives the application
    *  a chance to 'vote' on msg generation
    */
  def shouldLogOnNullSet(  lhs: String, rhs: String ): Boolean = {
    if (lhs.equals("$settest"))
      return false

    return true
  }

  def methodException( claz: java.lang.Class[_], method: String, e: Exception ): Object = {
    /*
     *  only do processing if the switch is on
     */
    if( exceptionSwitch && method.equals("throwException")) {
      return "Hello from the methodException() event handler method."
    }

    throw e
  }

  /**
    *  Required init method for LogSystem
    *  to get access to RuntimeServices
    */
  def init( rs: RuntimeServices ): Unit = {
  }

  /**
    * This just prints the message and level to System.out.
    */
  def log(level: Int, message: String): Unit = {
    if (logOutput)
    {
      Console.out.println("level : " + level + " msg : " + message)
    }
  }

  /**
    * This prints the level, message, and the Throwable's message to
    * System.out.
    */
  def log(level: Int, message: String, t: Throwable): Unit = {
    if (logOutput)
    {
      Console.out.println("level : " + level + " msg : " + message + " t : "
        + t.getMessage())
    }
  }

  /**
    * This always returns true because logging levels can't be disabled in
    * this impl.
    */
  def isLevelEnabled(level: Int): Boolean = {
    return true
  }
}
