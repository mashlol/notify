Parse.Cloud.define('notify', function(request, response) {
  console.log(request.params);
  var key = request.params.key;

  Parse.Push.send({
    channels: [key],
    data: {
      alert: 'Your command is done.',
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
