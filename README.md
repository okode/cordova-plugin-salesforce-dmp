# Cordova Salesforce DMP Plugin

[![<okode>](https://circleci.com/gh/okode/cordova-plugin-salesforce-dmp.svg?style=svg)](https://app.circleci.com/pipelines/github/okode/cordova-plugin-salesforce-dmp)

Cordova plugin for Salesforce DMP.

## Installing

Add to your package.json:

```
devDependencies: {
  ...
  "cordova-plugin-dmp": "0.0.2",
  ...
}
```

```
"cordova": {
  "plugins": {
    ...
    "cordova-plugin-dmp": {}
    ...
  }
}
```

## Project configuration

This plugin needs access to network state and location, you need to add the following configuration to your Android Manifest:


  <uses-permission android:name="android.permission.INTERNET"/>
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

## Import

```
import { DMPPlugin } from 'cordova-plugin-salesforce-dmp';
declare var DMP: DMPPlugin;
```

## Usage

```
DMP.initialize({ apiKey: string })
```

```
DMP.sendRequests(params: { policyRegime: string, consent: { [index: string]: number; }, identity?: { [index: string]: string; }})
```

```
DMP.getSegments()
```

```
DMP.trackPage(params: { email: string, logged: boolean, cod_ric: string, path: string, type: string })
```

```
DMP.fireEvent(params: { action: string, category: string, label: string })
```

## License

The project is MIT licensed: [MIT](https://opensource.org/licenses/MIT).
