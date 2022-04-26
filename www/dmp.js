var cordova = require('cordova');
var DMP = function () { };

function exec (action, params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      action,
      [params]
    );
  });
}

DMP.prototype.initialize = function (params) {
  return exec('initialize', params);
};

DMP.prototype.sendRequests = function (params) {
  return exec('sendRequests', params);
};

DMP.prototype.getSegments = function (params) {
  return exec('getSegments', params);
};

DMP.prototype.trackPage = function (params) {
  return exec('trackPage', params);
};

DMP.prototype.fireEvent = function (params) {
  return exec('fireEvent', params);
};

module.exports = new DMP();
