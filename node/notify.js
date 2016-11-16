var fs = require('fs');
var Parse = require('parse/node');

var Utils = require('./utils');

module.exports = function(text) {
  var keys = Utils.getRegFile().split(/\n+/).filter(key => key);
  if (!keys.length) {
    console.log(
      '[notify] No keys have been registered.' +
      ' Register a key with `notify -r {key}`.'
    );
    return;
  }
  for (var key of keys) {
    // TODO actually make the call to the hosted service
    console.log('[notify] Notifying ' + key);
  }
};
