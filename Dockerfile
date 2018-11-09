FROM ubuntu:18.10 as builder

# Install gradle
ENV GRADLE_VERSION=4.10.2
ENV GRADLE_HOME /home/user/gradle-$GRADLE_VERSION
ENV PATH $GRADLE_HOME/bin:$PATH

RUN apt-get update \
    && apt-get install -y wget unzip openjdk-11-jdk-headless
RUN wget -P /home/user/ --quiet https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip && \
    cd /home/user/ && unzip gradle-$GRADLE_VERSION-bin.zip && rm gradle-$GRADLE_VERSION-bin.zip

# Setup envirnoment for gradle
ENV APP_HOME /home/gradle/src/
WORKDIR $APP_HOME
ENV GRADLE_OPTS "$GRADLE_OPTS -Dorg.gradle.daemon=false"

# Now create the actual application with all the source files
COPY . $APP_HOME
RUN gradle bootJar

FROM azul/zulu-openjdk:11
WORKDIR /usr/app/
ARG version="0.1.0"
COPY --from=builder /home/gradle/src/build/libs/cityquest-${version}.jar /usr/app/cityquest.jar
CMD ["java", "-jar", "/usr/app/cityquest.jar"]