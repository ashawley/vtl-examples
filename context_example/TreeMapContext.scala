/*
 * Copyright (C) 2018 Aaron S. Hawley
 * Based on velocity/examples/context_example/TreeMapContext.java
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
import scala.Predef.String
import scala.collection.mutable.TreeMap

import org.apache.velocity.context.AbstractContext

import java.lang.Object

/**
 *   Example context impl that uses a TreeMap
 *
 *   Not much point other than to show how easy it is.
 *
 *   This is unsupported, example code.
 *
 * @author <a href="mailto:geirm@optonline.net"></a>
 * @version $Id$
  */
class TreeMapContext extends AbstractContext {

  private val context = new TreeMap[String, Object]

  def internalGet( key: String ): Object = {
    context.get( key )
  }

  def internalPut( key: String, value: Object ): Object = {
    context.put( key, value )
  }

  def internalContainsKey(key: String): Boolean = {
    context.contains( key )
  }

  def internalGetKeys(): Array[String] = {
    context.keys.toArray
  }

  def internalRemove(key: String): Object = {
    context.remove( key )
  }
}
