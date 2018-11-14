/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/app_example2/Example2.java
 * Jason van Zyl
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
import scala.Predef.String
import scala.Unit
import scala.Console

import org.slf4j.LoggerFactory

import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext

import org.apache.velocity.exception.ParseErrorException
import org.apache.velocity.exception.MethodInvocationException

/**
 * This class is a simple demonstration of how the Velocity Template Engine
 * can be used in a standalone application using the Velocity utility class.
 *
 * It demonstrates two of the 'helper' methods found in the org.apache.velocity.util.Velocity
 * class, mergeTemplate() and evaluate().
 */
object Example {
    
  val logger = LoggerFactory.getLogger(getClass)

  val getNames = Array(
    "ArrayList element 1",
    "ArrayList element 2",
    "ArrayList element 3",
    "ArrayList element 4"
  )

  def main(args: Array[String]): Unit = {

    /* first, we init the runtime engine.  Defaults are fine. */
    try {
      Velocity.init()
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("Problem initializing Velocity : " + e)
    }

    /* lets make a Context and put data into it */
    val context = new VelocityContext

    context.put("name", "Velocity")
    context.put("project", "Jakarta")

    /* lets render a template */
    var w = new java.io.StringWriter()

    try {
      Velocity.mergeTemplate("example2.vm", "ISO-8859-1", context, w )
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("Problem merging template : " + e)
    }

    Console.out.println(" template : " + w )

    /*
     *  lets dynamically 'create' our template
     *  and use the evaluate() method to render it
     */
    val s = "We are using $project $name to render this."
    w = new java.io.StringWriter()

    try {
      Velocity.evaluate( context, w, "mystring", s );
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
        Console.out.println("MethodInvocationException : " + mee )
      case e: java.lang.Throwable =>
        Console.out.println("Exception : " + e )
    }

    Console.out.println(" string : " + w )
  }
}
