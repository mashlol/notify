var fs = require('fs');

var Utils = require('./utils');

module.exports = function(key) {
  var existingFile = Utils.getRegFile();
  Utils.writeToRegFile(existingFile + key + '\n');
  console.log('[notify] Your registration code has been saved to ~/.notifyreg');
};
