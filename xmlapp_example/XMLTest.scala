/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/xmlapp_example/XMLTest.java
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

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.Writer

import org.apache.velocity.VelocityContext
import org.apache.velocity.Template
import org.apache.velocity.app.Velocity

import org.jdom.Document
import org.jdom.input.SAXBuilder

/**
 * Example to show basic XML handling in a template.
 */
object XMLTest {

  def main( args: Array[String] ): Unit = {

    if( args.length < 1 ) {
      Console.out.println("Usage : java XMLTest <templatename>")
      return
    }

    val templateFile = args(0)

    var writer: Writer = null
                        
    try {
      /*
       *  and now call init
       */
      Velocity.init()

      /*
       * build a Document from our xml
       */

      var builder: SAXBuilder = null
      var root: Document = null

      try {
        builder = new SAXBuilder()
        root = builder.build("test.xml")
      } catch {
        case ee: java.lang.Throwable =>
          Console.out.println("Exception building Document : " + ee)
          return
      }

      /*
       * now, make a Context object and populate it.
       */
      val context = new VelocityContext()
      context.put("root", root)

      /*
       *  make a writer, and merge the template 'against' the context
       */
      val template: Template = Velocity.getTemplate(templateFile)

      writer = new BufferedWriter(new OutputStreamWriter(Console.out))
      template.merge( context , writer)

    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("Exception : " + e)
    } finally {
      if ( writer != null)
      {
        try
        {
          writer.flush()
          writer.close()
        } catch {
          case ee: java.lang.Throwable =>
            Console.out.println("Exception : " + ee )
        }
      }
    }
  }
}
