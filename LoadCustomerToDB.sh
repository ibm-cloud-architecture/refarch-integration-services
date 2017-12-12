#!/bin/bash
export CP=./build/classes/test:./build/classes/main:./lib/httpclient-4.5.1.jar:./lib/junit.jar:./lib/httpcore-4.4.3.jar:./lib/httpclient-cache-4.5.1.jar:./lib/commons-codec-1/9.jar:./lib/commons-logging-1.2.jar:./lib/org.hamcrest.core_1.3.0.v201303031735.jar:./lib/gson-2.2.4.jar
java -cp $CP org.junit.runner.JUnitCore ws.tests.TestItemAccessViaWS
java -cp $CP org.junit.runner.JUnitCore ws.tests.TestSupplierViaWS
