FROM openjdk:11
WORKDIR /app
COPY . .
RUN curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc \
    | tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null \
    && echo "deb https://ngrok-agent.s3.amazonaws.com buster main" \
    | tee /etc/apt/sources.list.d/ngrok.list \
    && apt update \
    && apt install ngrok

RUN \
  mkdir /working/ && \
  cd /working/ && \
  curl -L -o sbt-1.9.9.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-1.9.9.deb && \
  dpkg -i sbt-1.9.9.deb && \
  rm sbt-1.9.9.deb && \
  apt-get update && \
  apt-get install sbt && \
  cd && \
  rm -r /working/

CMD ngrok authtoken $NGROK_AUTH_TOKEN && \
    ngrok http 9000 & \
    sbt run