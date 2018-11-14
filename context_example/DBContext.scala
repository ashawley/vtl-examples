/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/context_example/DBContext.java
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
import scala.Byte
import scala.Boolean
import scala.Predef.String
import scala.Unit
import scala.Console

import org.apache.velocity.context.AbstractContext

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Object
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.PreparedStatement
import java.sql.Statement

/**
 *   Example context impl that uses a database to store stuff :)
 *
 *   yes, this is silly
 *
 *   expects a mysql db test with table
 *
 *  CREATE TABLE contextstore (
 *    k varchar(100),
 *    val blob
 *  );
 *
 *  very fragile, crappy code.... just a demo!
 */

class DBContext extends AbstractContext
{
  var conn: Connection = null

  setup()

  /**
    *  retrieves a serialized object from the db
    *  and returns the living instance to the
    *  caller.
    */
  def internalGet( key: String ): Object = {
    try
    {
      var data: Array[Byte] = null

      val sql = "SELECT k, val FROM contextstore WHERE k ='"+key+"'"

      val s: Statement = conn.createStatement()

      val rs: ResultSet = s.executeQuery( sql )

      if(rs.next())
        data = rs.getBytes("val")

      val in = new ObjectInputStream(  new ByteArrayInputStream( data ))

      val o: Object =  in.readObject()
      rs.close()
      s.close()

      in.close()

      o
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("internalGet() : " + e )
        null
    }
  }

  /**
    *  Serializes and stores an object in the database.
    *  This is really a hokey way to do it, and will
    *  cause problems.  The right way is to use a
    *  prepared statement...
    */
  def internalPut( key: String, value: Object ): Object = {
    try
    {
      val baos = new ByteArrayOutputStream()
      val out = new ObjectOutputStream( baos )

      out.writeObject( value )
      out.flush()
      val data: Array[Byte] = baos.toByteArray()

      val s: Statement = conn.createStatement()

      s.executeUpdate( "DELETE FROM contextstore WHERE k = '" + key + "'" )
      s.close()

      val p: PreparedStatement = conn.prepareStatement( "INSERT INTO contextstore (k,val) values (?, ?)" )

      p.setString(1, key)
      p.setBytes(2, data)
      p.executeUpdate()
      p.close()

      p.close()
      value
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println("internalPut() : " + e )
        null
    }
  }

  /**
    *  Not implementing. Not required for Velocity core
    *  operation, so not bothering.  As we say above :
    *  "very fragile, crappy code..."
    */
  def internalContainsKey(key: String): Boolean = {
    false
  }

  /**
    *  Not implementing. Not required for Velocity core
    *  operation, so not bothering.  As we say above :
    *  "very fragile, crappy code..."
    */
  def internalGetKeys(): Array[String] = {
    null
  }

  /**
    *  Not implementing. Not required for Velocity core
    *  operation, so not bothering.  As we say above :
    *  "very fragile, crappy code..."
    */
  def internalRemove(key: String): Object = {
    null
  }


  def setup(): Unit = {
    try {
      java.lang.Class.forName("com.mysql.jdbc.Driver").newInstance()
      conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root")
    } catch {
      case e: java.lang.Throwable =>
        Console.out.println(e)
    }
  }
}
