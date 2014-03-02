/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *
 */ 
package net.imadz.org.apache.bcel.verifier.exc;


/**
 * When loading a class file, BCEL will throw an instance of LoadingException if
 * the class file is malformed; so it is not conforming to the "Pass 1" verification
 * process as described in the Java Virtual Machine specification, 2nd. edition.
 * @version $Id: LoadingException.java 992350 2010-09-03 16:18:20Z markt $
 * @author Enver Haase
 */
public class LoadingException extends VerifierConstraintViolatedException{

	private static final long serialVersionUID = -7911901533049018823L;

    /**
	 * Constructs a new LoadingException with null as its error message string.
	 */
	public LoadingException(){
		super();
	}
                   
	/**
	 * Constructs a new LoadingException with the specified error message.
	 */
	public LoadingException(String message){
		super (message);
	}
}
