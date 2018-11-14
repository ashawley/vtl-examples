/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/app_example1/Example.java
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
import scala.Option
import scala.Predef.String
import scala.Unit
import scala.Console

import org.slf4j.LoggerFactory

import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext
import org.apache.velocity.Template

import org.apache.velocity.exception.ParseErrorException
import org.apache.velocity.exception.ResourceNotFoundException

/**
 * This class is a simple demonstration of how the Velocity Template Engine
 * can be used in a standalone application.
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
    /*
     * setup
     */
    val templateFile = args(0)
    val writer = new java.io.BufferedWriter(
      new java.io.OutputStreamWriter(scala.Console.out)
    )
    Velocity.init("velocity.properties")

    try {

      /*
       *  Make a context object and populate with the data.  This
       *  is where the Velocity engine gets the data to resolve the
       *  references (ex. $list) in the template
       */
      val context: VelocityContext = new VelocityContext
      context.put("list", getNames)

      /*
       *  get the Template object.  This is the parsed version of your
       *  template input file.  Note that getTemplate() can throw
       *   ResourceNotFoundException : if it doesn't find the template
       *   ParseErrorException : if there is something wrong with the VTL
       *   Exception : if something else goes wrong (this is generally
       *        indicative of as serious problem...)
       */
      Option(Velocity.getTemplate(templateFile)).foreach { template: Template =>
        /*
         *  Now have the template engine process your template using the
         *  data placed into the context.  Think of it as a  'merge'
         *  of the template and the data to produce the output stream.
         */
        template.merge(context, writer)
      }
    } catch {
      case rnfe: ResourceNotFoundException =>
        Console.out.println("Example : error : cannot find template " + templateFile + ":" + rnfe)
      case pee: ParseErrorException =>
        Console.out.println("Example : Syntax error in template " + templateFile + ":" + pee )
      case e: java.lang.Throwable => Console.out.println(e)
    } finally {
      /*
       *  flush and cleanup
       */
      writer.flush()
      writer.close()
    }
  }
}
