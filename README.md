# annict-android
Annict Android app. https://annict.com


# API
Annict is open source project. https://github.com/annict/annict
The document is [here](https://annict.wikihub.io/wiki/api).


# Contribution

### Build

To build the app locally, you need an application id and secret key of annict.com.

You can register your own application from [here](https://annict.com/oauth/applications).

After getting id and key, create `api_keys.gradle` on the root of module like the following.

```gradle
ext {
    annict_application_id = "YOUR APPLICATION ID"
    annict_secret_key = "YOUR SECRET KEY"
}
```

This project is under development. Issues are managed by waffle.io. https://waffle.io/konifar/annict-android

If you have a feature you want or find some bugs, please write an issue.
