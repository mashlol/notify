var express = require('express');

var notify = require('./notify');

var app = express();

app.get('/notify', function(req, res) {
  notify(req.query, res);
});

app.listen(3000);
