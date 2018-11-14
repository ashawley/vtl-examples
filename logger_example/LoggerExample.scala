/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/logger_example/LoggerExample.java
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

import scala.Boolean
import scala.Int
import scala.Predef.String
import scala.Unit
import scala.Console

import scala.Array

import org.apache.velocity.app.Velocity
import org.apache.velocity.runtime.log.LogChute
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.RuntimeServices

/**
 *  This is a toy demonstration of how Velocity
 *  can use an externally configured logger.  In
 *  this example, the class using Velocity
 *  implements Velocity's logger interface, and
 *  all Velocity log messages are funneled back
 *  through it.
 */
object LoggerExample extends LogChute {
    
  /**
    *  Required init() method for LogSystem
    *  to get access to RuntimeServices
    */
  def init(rs: RuntimeServices): Unit = {
  }

  /**
    * This just prints the message and level to System.out.
    */
  def log(level: Int, message: String): Unit = {
    Console.out.println("level : " + level + " msg : " + message)
  }

  /**
    * This prints the level, message, and the Throwable's message to
    * System.out.
    */
  def log(level: Int, message: String, t: java.lang.Throwable): Unit = {
    Console.out.println("level : " + level + " msg : " + message + " t : "
      + t.getMessage)
  }

  /**
    * This always returns true because logging levels can't be disabled in
    * this impl.
    */
  def isLevelEnabled(level: Int): Boolean = {
    true
  }
// }

// object LoggerExample {

  def main(args: Array[String]): Unit = {

    // val logger = new LoggerExample

    try
    {
      /*
       *  this class implements the LogSystem interface, so we
       *  can use it as a logger for Velocity
       */

      Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, this)
      Velocity.init()

      /*
       *  that will be enough.  The Velocity initialization will be
       *  output to stdout because of our
       *  logVelocityMessage() method in this class
       */
    } catch {
      case e: java.lang.Throwable => Console.out.println("Exception : " + e)
    }
  }
}
