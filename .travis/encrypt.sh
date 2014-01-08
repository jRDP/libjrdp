#!/bin/bash
DIR="$( cd "$( dirname "$0" )" && pwd )"
cat - | openssl pkeyutl -encrypt -pubin -inkey $DIR/libjrdp-travis.pub.pem  | base64

