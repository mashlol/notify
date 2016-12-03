![notify](http://i.imgur.com/OYoRBS3.png)

[Play Store Link](https://play.google.com/store/apps/details?id=com.kevinbedi.notify)

# Installation
The notify CLI is written with node, so you can install it with npm:

```sh
npm install -g notify-cli
```

Alternatively, you can download & use the shell script equivalent from [here](https://github.com/mashlol/notify/blob/master/sh/notify.sh), with basic support for adding a single key and sending a notification to it.

You'll also need the [app](https://play.google.com/store/apps/details?id=com.kevinbedi.notify) on your phone to actually receive the notifications.

# Usage
Using notify is simple. When you download the app to your phone, it will give you a registration key. This key is how your phone is identified by notify. Before you can begin using notify, you should first register your key.

```sh
notify -r myKey
```

After registering, you can use notify as follows:

```sh
someLongRunningCommand ; notify
```

You will receive a push notification to your phone when the command has completed, regardless of whether or not it was successful.

You can also specify what the notification will say, like this:

```
notify --text "My message"
notify -t "Some cool message"
```
