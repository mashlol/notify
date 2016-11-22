var express = require('express');

var notify = require('./notify');
var generateToken = require('./generateToken');

var app = express();

app.get('/notify', function(req, res) {
  notify(req.query, res);
});

app.get('/token', function(req, res) {
  generateToken(req.query, res);
});

app.listen(3000);
