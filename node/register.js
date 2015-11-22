var fs = require('fs');

var getUserHome = function() {
  return process.env[(process.platform == 'win32') ? 'USERPROFILE' : 'HOME'];
};

module.exports = function(key) {
  // TODO: check if the key was valid before saving it
  fs.writeFileSync(getUserHome() + '/.notifyreg', key);
  console.log('[notify] Your registration code has been saved to ~/.notifyreg');
};
