#!/bin/bash

echo "--DEBUG--"
echo "$TRAVIS_REPO_SLUG"
echo "$TRAVIS_JDK_VERSION"
echo "$TRAVIS_PULL_REQUEST"
echo "$TRAVIS_BRANCH"

if [ "$TRAVIS_REPO_SLUG" == "jRDP/libjrdp" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
 .travis/publish_javadocs.sh
fi

