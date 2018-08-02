
FROM openjdk:8

ENV SBT_VERSION 1.2.0

# (thank you https://github.com/hseeberger/scala-sbt)
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion
  
WORKDIR /root

ADD . /root

EXPOSE 8080

CMD sbt ~re-start 