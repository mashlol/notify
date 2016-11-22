// Make call to firebase
var restler = require('restler');
var admin = require('firebase-admin');

var config = require('./config.json');

var FIREBASE_SEND_URL = 'https://fcm.googleapis.com/fcm/send';
var FIREBASE_AUTH_KEY = 'key=' + config.serverKey;

var serviceAccount = require('./serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://notify-b7652.firebaseio.com'
});

module.exports = function(params, res) {
  var to = params.to;
  var text = params.text || 'Your command is complete.';
  var title = params.title;

  if (!to) {
    res.json({success: false, error: 'No "to" param specified.'});
  }

  var db = admin.database();
  db.ref('tokens/' + to + '/gcmToken').once('value', function(snap) {
    to = snap.val();

    if (!to) {
      res.json({success: false, error: 'Invalid "to" param specified.'});
    }

    restler.post(FIREBASE_SEND_URL, {
      headers: {
        Authorization: FIREBASE_AUTH_KEY,
        'Content-Type': 'application/json',
      },
      data: JSON.stringify({
        to: to,
        notification: {
          body: text,
          title: title,
        },
      }),
    }).on('complete', function(result, response) {
      if (response.statusCode === 200) {
        console.log('[Notify] Sent message to ' + to);
        res.json({
          success: true,
        });
      }
    });
  });
};
