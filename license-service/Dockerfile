FROM  openjdk:17.0.2 AS build
ARG   JAR_FILE
COPY  ${JAR_FILE} /app.jar
RUN   mkdir -p /target/dependency && (cd /target/dependency; jar -xf /app.jar)


FROM  openjdk:17.0.2
ARG   DEPENDENCY=/target/dependency
COPY  --from=build ${DEPENDENCY}/BOOT-INF/lib      /app/lib
COPY  --from=build ${DEPENDENCY}/META-INF          /app/META-INF
COPY  --from=build ${DEPENDENCY}/BOOT-INF/classes  /app
ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "com.optimagrowth.license.LicenseServiceApplication"]