// Make call to firebase
var restler = require('restler');

var config = require('./config.json');

var FIREBASE_SEND_URL = 'https://fcm.googleapis.com/fcm/send';
var FIREBASE_AUTH_KEY = 'key=' + config.serverKey;

module.exports = function(params, res) {
  var to = "cdlL1lhb1Kw:APA91bGmtuPzxcd9-02kjcV0Bk0IfpKNsCFa8XJ7DPPku1jpsnj14i097YWZ2L6MNfCixXQ1hv1VVlky47OX-1PsH4kw37njQdwxCwF_kV_MD9iTO9iApbvgyawojre1ZrK-9pVesL8k";
  var text = "Hello";

  console.log(params);

  // TODO lookup short tokens

  restler.post(FIREBASE_SEND_URL, {
    headers: {
      Authorization: FIREBASE_AUTH_KEY,
      'Content-Type': 'application/json',
    },
    data: JSON.stringify({
      to: to,
      notification: {
        body: text,
      },
    }),
  }).on('complete', function(result, response) {
    console.log(response);
    if (response.statusCode === 200) {
      console.log("Sent message to " + to);
      res.json({
        success: true,
      });
    }
  });
};
