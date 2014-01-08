#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "jRDP/libjrdp" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
 .travis/publish_javadocs.sh
fi

