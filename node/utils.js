var fs = require('fs');

var getUserHome = function() {
  return process.env[(process.platform == 'win32') ? 'USERPROFILE' : 'HOME'];
};

var getRegFilename = function() {
  return getUserHome() + '/.notifyreg';
};

module.exports = {
  getRegFile: function() {
    if (fs.existsSync(getRegFilename())) {
      return fs.readFileSync(getRegFilename(), {encoding: 'utf8'});
    }

    return '';
  },

  writeToRegFile: function(text) {
    fs.writeFileSync(getRegFilename(), text);
  },
};