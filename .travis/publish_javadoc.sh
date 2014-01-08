#!/bin/bash

echo -e "Starting latest javadoc auto publish...\n"

  #cd ../build
  mvn javadoc:javadoc || return -1
  
  cp -R target/site/apidocs $HOME/javadoc-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_PAGES}@github.com/jRDP/libjrdp gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc/latest
  cp -Rf $HOME/javadoc-latest ./javadoc/latest
  git add -f .
  git commit -m "Automatic update of javadoc/latest/ by Travis CI build #$TRAVIS_BUILD_NUMBER"
  git push -fq origin gh-pages > /dev/null

  echo -e "Done javadoc latest auto publish.\n"
  
