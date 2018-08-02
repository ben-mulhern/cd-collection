#
# Scala and sbt Dockerfile
#
# Adapted from  https://github.com/hseeberger/scala-sbt
#

# Pull base image
FROM openjdk:8

# Env variables
ENV SBT_VERSION 1.2.0

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion
  
# Define working directory
WORKDIR /root

# Copy the source code into the working directory
ADD . /root

# Expose port 8080
EXPOSE 8080

# Run the project
CMD sbt ~re-start 