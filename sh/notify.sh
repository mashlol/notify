#!/bin/sh

while [[ $# > 1 ]]
do
  key="$1"

  case $key in
      -r|--register)
      KEY="$2"
      shift
      ;;
      -t|--text)
      TEXT="$2"
      shift
      ;;
      *)
      ;;
  esac

  shift
done

if [ -n "${KEY}" ]
then
  # Save token to ~/.notifyreg
  echo "${KEY}" > ~/.notifyreg
else
  KEY=`cat ~/.notifyreg`
  curl -X POST \
  -H "X-Parse-Application-Id: HQrMLZDevpTv2J1raSC6KATvlpNqqePPecUE0EgG" \
  -H "X-Parse-REST-API-Key: ivgV8ZoA0kyOOLWKms3M0wxYUxyUw4tfGgbj6DFd" \
  -H "Content-Type: application/json" \
  -d "{\"key\":\"${KEY}\", \"text\": \"${TEXT}\"}" \
  https://api.parse.com/1/functions/notify \
  > /dev/null

  echo "[notify] Successfully sent notification."
fi
