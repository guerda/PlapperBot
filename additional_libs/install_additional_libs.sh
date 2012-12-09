#!/bin/bash
GROUP_ID=org.ciscavate.cjwizard
ARTIFACT_ID=cjwizard
VERSION=0.2
JAR=CJWizards-0.2.jar
SRC_JAR=CJWizards-0.21-src.jar

mvn install:install-file -Dsources=$SRC_JAR -Dfile=$JAR -DgroupId=$GROUP_ID -DartifactId=$ARTIFACT_ID -Dversion=$VERSION -Dpackaging=jar
