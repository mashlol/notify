#!/bin/bash

while [[ $# > 1 ]]
do
  key="$1"

  case $key in
      -r|--register)
      KEY="$2"
      shift
      ;;
      -t|--text)
      TEXT=$(sed 's/ /%20/g' <<< "$2")
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
  curl \
  "https://us-central1-notify-b7652.cloudfunctions.net/sendNotification?to=${KEY}&text=${TEXT}" \
  > /dev/null

  echo "[notify] Successfully sent notification."
fi
