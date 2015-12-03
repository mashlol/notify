#!/usr/bin/env node

var program = require('commander');

var packageJson = require('./package.json');
var register = require('./register');
var notify = require('./notify');

program.version(packageJson.version)
  .option('-r, --register [key]', 'Register')
  .option('-t --text [text]', 'Text for the notification')
  .parse(process.argv);

// For registration
if (program.register) {
  register(program.register);
  return;
}

notify(program.text);
