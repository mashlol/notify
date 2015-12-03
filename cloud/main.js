Parse.Cloud.define('notify', function(request, response) {
  var key = request.params.key;

  var query = new Parse.Query(Parse.Installation);
  query.equalTo('objectId', key);

  var text = request.params.text || 'Your command is complete.';

  Parse.Push.send({
    where: query,
    data: {
      alert: text,
    },
  }).then(function() {
    response.success('Notification sent');
  }, function(error) {
    response.error(
      'Error ' +
      error.code +
      ': ' +
      error.message
    );
  });
});
