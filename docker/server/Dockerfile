
ARG SMS_ACCESS_KEY_SECRET
ARG SMS_ACCESS_KEY_ID

FROM openjdk:8-jdk-alpine

ENV ALIYUN_SMS_ACCESS_KEY_SECRET=${SMS_ACCESS_KEY_SECRET:-}
ENV ALIYUN_SMS_ACCESS_KEY_ID=${SMS_ACCESS_KEY_ID:-}

VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=druid","--aliyun.sms.access-key-id=${ALIYUN_SMS_ACCESS_KEY_ID}","--aliyun.sms.access-key-secret=${ALIYUN_SMS_ACCESS_KEY_SECRET}"]