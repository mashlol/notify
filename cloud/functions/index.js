const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.https.onRequest((request, response) => {
  var params = request.query;

  var to = params.to;
  var text = params.text || 'Your command is complete.';
  var title = params.title || 'Notify';

  if (!to) {
    response.json({success: false, error: 'No "to" param specified.'});
    return;
  }

  var db = admin.database();
  return db.ref('tokens/' + to + '/gcmToken').once('value').then(function(snap) {
    to = snap.val();

    if (!to) {
      response.json({success: false, error: 'Invalid "to" param specified.'});
      return;
    }

    var payload = {
      data: {
        title: title,
        text: text,
      },
    };

    console.log("[Notify] Sending message to " + to);
    admin.messaging().sendToDevice(to, payload)
      .then(() => {
        console.log("[Notify] Sent message to " + to);
        response.json({success: true});
      })
      .catch((error) => {
        console.warn("[Notify] Unable to send message to " + to, error);
        response.json({success: false, error: error});
      });
  });
});
