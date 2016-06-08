GIT_HASH=`git rev-parse --short HEAD`

./gradlew clean assembleProductionRelease
curl -F "file=@app/build/outputs/apk/app-production-release.apk" -F "token=${DEPLOY_GATE_API_KEY}" -F "message=https://github.com/konifar/annict-android/tree/${GIT_HASH} https://circleci.com/gh/konifar/annict-android/${CIRCLE_BUILD_NUM}" -F "distribution_key=f24993205441b099ab4374d4137f7656ab413198" -F "release_note=https://github.com/konifar/annict-android/tree/${GIT_HASH} https://circleci.com/gh/konifar/annict-android/${CIRCLE_BUILD_NUM}" https://deploygate.com/api/users/konifar/apps
