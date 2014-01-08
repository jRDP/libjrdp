#!/bin/bash

#echo "--DEBUG--"
#echo "$TRAVIS_REPO_SLUG"
#echo "JDK-VERSION: $TRAVIS_JDK_VERSION"
#echo "$TRAVIS_PULL_REQUEST"
#echo "$TRAVIS_BRANCH"
#&& [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ]
if [ "$TRAVIS_REPO_SLUG" == "jRDP/libjrdp" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
 .travis/publish_javadoc.sh
fi

