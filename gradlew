#!/bin/sh

# Minimal Gradle wrapper launcher. The distribution URL is pinned in
# gradle/wrapper/gradle-wrapper.properties.
set -e

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Missing gradle-wrapper.jar. Use a Gradle installation to regenerate the wrapper." >&2
  exit 1
fi

exec java -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
