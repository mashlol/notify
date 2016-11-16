// Make call to firebase
var restler = require('./restler');

var config = require('./config.json');

var FIREBASE_SEND_URL = 'https://fcm.googleapis.com/fcm/send';
var FIREBASE_AUTH_KEY = 'key=' + config.serverKey;

// TODO hook into AWS params 
var to = "todo";
var text = "todo";

restler.post(FIREBASE_SEND_URL, {
  'Content-Type': 'application/json',
  Authorization: FIREBASE_AUTH_KEY,
  to: to,
  notification: {
    body: text,
  },
}).on('complete', function(result, response) {
  if (response.statusCode === 200) {
    console.log("Sent message to " + to);
  }
});
