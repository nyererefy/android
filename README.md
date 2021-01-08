## About this project
This project contains source code of Nyererefy Android application.

## Configuring this project
1. You need to create `gradle.properties` at the root of project and add the following lines

```
DEV_BASE_URL="http://YOUR-IP/graphql"
LIVE_BASE_URL="https://nyererefy.com/graphql"

DEV_WS_URL="ws://YOUR-IP/graphql"
LIVE_WS_URL="ws://nyererefy.com/graphql"

DEV_API_KEY="<ANYTHING-HERE>"
LIVE_API_KEY="<YOUR-LIVE-API-KEY>"

GITHUB_GRAPHQL_URL="https://api.github.com/graphql"
GITHUB_TOKEN="<YOUR-GITHUB-TOKEN>"

android.enableJetifier=true
android.useAndroidX=true
```

2. You need a fake server. Install GraphQL Faker by following the guideline here https://github.com/APIs-guru/graphql-faker

3. After installing GraphQL faker open a new terminal in  `app/src/main/graphql/com/nyererefy/graphql` and run
`graphql-faker -o schema.json`

4. Run the app
