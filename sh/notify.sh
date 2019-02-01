#!/usr/bin/env bash

TOKENFILE=~/.notifyreg

function usage()
{
    echo "Send notification to an Android device."
    echo
    echo "usage:"
    echo "  notify.sh --register <token>"
    echo "  notify.sh [--title <title>] --text <text>"
    echo
    echo "options:"
    echo "  --register <token>, -r <token>    Store given token in token file"
    echo "  --text <text>, -t <text>          Text for the notification"
    echo "  --title <title>, -i <title>       Title for the notification"
}

TEMP=$(getopt -o hr:t:i: --long help,register:,text:,title: -n 'notify.sh' -- "$@")
eval set -- "$TEMP"

TEXT=""
TITLE=""

while true; do
    case "$1" in
        -r|--register)
              echo "Storing new token in $TOKENFILE."
              echo "$2" > "$TOKENFILE"
              exit
            ;;
        -t|--text)
            TEXT=$2
            shift 2
            ;;
        -i|--title)
            TITLE=$2
            shift 2
            ;;
        -h|--help)
            usage
            exit
            ;;
        --)
            shift
            break
            ;;
        *)
            echo "Internal error."
            exit 1 ;;
    esac
done

if [ "$#" -ne 0 ]
then
    echo "Unexpected positional arguments."
    exit 1
fi

if [ ! -f "$TOKENFILE" ]
then
    echo "Token file missing at $TOKENFILE"
    echo "Use --register to specify token."
    exit 2
fi

TOKEN=$(cat "$TOKENFILE")

if [ -z "$TOKEN" ]
then
    echo "Token file empty."
    echo "Use --register to specify new token."
    exit 2
fi

if curl -g -s -G \
    'https://us-central1-notify-b7652.cloudfunctions.net/sendNotification' \
    --data-urlencode "to=$TOKEN" \
    --data-urlencode "text=$TEXT" \
    --data-urlencode "title=$TITLE" \
    >/dev/null 2>&1
then
    echo "[notify] Successfully sent notification."
else
    echo "[notify] Sending notification failed."
    exit 3
fi
