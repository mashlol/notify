![notify](http://i.imgur.com/OYoRBS3.png)

# Installation
notify is written with node, so you can install with npm.

```sh
npm install -g notify-cli
```

Alternatively, you can download & use the shell script equivalent from [here](https://github.com/mashlol/notify/blob/master/sh/notify.sh), its usage is the exact same as the node CLI.

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
