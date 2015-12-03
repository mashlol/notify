var fs = require('fs');
var Parse = require('parse/node');

// Public parse keys
Parse.initialize(
  'HQrMLZDevpTv2J1raSC6KATvlpNqqePPecUE0EgG',
  'bBHPgeYLFkAx3xb95J1P4g2LsGroF5pEfzLYznw0'
);

var getUserHome = function() {
  return process.env[(process.platform == 'win32') ? 'USERPROFILE' : 'HOME'];
};

module.exports = function(text) {
  // Fetch registration token
  var key = fs.readFileSync(getUserHome() + '/.notifyreg', {encoding: 'utf8'});

  Parse.Cloud.run('notify', {key: key, text: text}).then(function() {
    console.log('[notify] Successfully sent notification.');
  }, function(error) {
    console.log('[notify] Encountered an error:', error);
  });
};
