var fs = require('fs');

var Utils = require('./utils');

module.exports = function(key) {
  var existingFile = Utils.getRegFile();

  if (existingFile.indexOf(key + '\n') === -1) {
    console.log(
      '[notify] The registration key ' + key + ' was never registered.'
    );
    return;
  }

  var newContents = existingFile.replace(key + '\n', '');
  Utils.writeToRegFile(newContents);
  console.log(
    '[notify] The registration key ' + 
    key + 
    ' has been removed from ~/.notifyreg'
  );
};
