/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/context_example/DBContextTest.java
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
import scala.collection.mutable.HashMap
import scala.collection.JavaConverters.mapAsJavaMapConverter

import org.apache.velocity.Template
import org.apache.velocity.runtime.RuntimeSingleton

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.Writer
import java.lang.Object
import java.util.Properties

/**
 *  the ultimate in silliness...
 *
 *  tests the DBContext example by putting a string and a hashtable
 *  into the context and then rendering a simple template with it.
 */

object DBContextTest {

  def main(args: Array[String]): Unit = {
    val writer: Writer =
      new BufferedWriter(new OutputStreamWriter(Console.out))

    try {

      val templateFile = args(0)

      RuntimeSingleton.init( new Properties() )

      val template: Template = RuntimeSingleton.getTemplate(templateFile)

      val dbc: DBContext = new DBContext()

      val h = new HashMap[String, Object]()
      h.put("Bar", "this is from a hashtable!")

      dbc.put( "string", "Hello!")
      dbc.put( "hashtable", h.asJava )

      template.merge(dbc, writer)

    } catch {
      case e: java.lang.Throwable =>
        RuntimeSingleton.getLog().error("Something funny happened", e)
    } finally {
      writer.flush()
      writer.close()
    }
  }
}
