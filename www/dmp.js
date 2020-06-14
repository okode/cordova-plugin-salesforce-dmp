var cordova = require('cordova');
var DMP = function () { };

function exec (action, params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      action,
      params ? [params] : undefined
    );
  });
}

DMP.prototype.initialize = function (params) {
  return exec('initialize', params);
};

DMP.prototype.sendRequests = function () {
  return exec('sendRequests');
};

DMP.prototype.trackPage = function (params) {
  return exec('trackPage', params);
};

DMP.prototype.fireEvent = function (params) {
  return exec('fireEvent', params);
};

module.exports = new DMP();
