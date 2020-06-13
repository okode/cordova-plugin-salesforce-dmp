var cordova = require('cordova');
var DMP = function () { };

// TODO - Plugin Errors
// DMP.prototype.DMP__REQUEST__ACCOUNTS_NOT_FOUND = -100

DMP.prototype.initialize = function (params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      'initialize',
      [params]
    );
  });
};
DMP.prototype.sendRequests = function (params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      'sendRequests',
      [params]
    );
  });
};
DMP.prototype.trackPage = function (params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      'trackPage',
      [params]
    );
  });
};

DMP.prototype.fireEvent = function (params) {
  return new Promise(function (resolve, reject) {
    cordova.exec(
      function (success) { resolve(success); },
      function (error) { reject(error); },
      'DMP',
      'fireEvent',
      [params]
    );
  });
};

module.exports = new DMP();
