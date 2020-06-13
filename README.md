# Cordova Salesforce DMP Plugin

[![<okode>](https://circleci.com/gh/okode/cordova-plugin-salesforce-dmp.svg?style=svg)](https://app.circleci.com/pipelines/github/okode/cordova-plugin-salesforce-dmp)

Cordova plugin for Salesforce DMP.

## Installing

Add to your package.json:

```
devDependencies: {
  ...
  "cordova-plugin-dmp": "github:okode/cordova-plugin-dmp",
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

## Usage

```
window.dmp.save({ id, name, password });
```

```
window.dmp.request();
```

```
window.dmp.delete({ id });
```

## Operation codes

### Request

- `DMP__REQUEST__ACCOUNTS_NOT_FOUND** = -100`
- `DMP__REQUEST__DIALOG_CANCELLED = -101`

### Save

- `DMP__SAVE = -200`
- `DMP__SAVE__BAD_REQUEST = -201`

### Delete

- `DMP__DELETE = -300`

### Common

- `DMP__COMMON__UNKOWN = -400`
- `DMP__COMMON__CONCURRENT_NOT_ALLOWED = -401`
- `DMP__COMMON__GOOGLE_API_UNAVAILABLE = -402`
- `DMP__COMMON__RESOLUTION_PROMPT_FAIL = -403`

## License

The project is MIT licensed: [MIT](https://opensource.org/licenses/MIT).