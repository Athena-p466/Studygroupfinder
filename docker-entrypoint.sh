#!/bin/sh
set -e
if [ -n "$DATABASE_URL" ]; then
  rest="${DATABASE_URL#*://}"      # user:pass@host[:port]/db
  creds="${rest%%@*}"              # user:pass
  hostpart="${rest#*@}"            # host[:port]/db
  export SPRING_DATASOURCE_USERNAME="${creds%%:*}"
  export SPRING_DATASOURCE_PASSWORD="${creds#*:}"
  export SPRING_DATASOURCE_URL="jdbc:postgresql://${hostpart}"
fi
if [ -n "$SQLITE_PATH" ]; then     # SQLite: make sure the folder exists
  mkdir -p "$(dirname "$SQLITE_PATH")"
fi
exec java -jar /app/app.jar