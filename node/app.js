#!/usr/bin/env node

var program = require('commander');

var packageJson = require('./package.json');
var register = require('./register');
var notify = require('./notify');

program.version(packageJson.version)
  .option('-r, --register [key]', 'Register')
  .parse(process.argv);

// For registration
if (program.register) {
  register(program.register);
  return;
}

notify();
