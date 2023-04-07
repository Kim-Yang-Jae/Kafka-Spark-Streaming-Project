FROM openjdk:11-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR


FROM openjdk:11-jdk
COPY --from=builder build/libs/*.jar app.jar

RUN apt-get update
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
RUN wget https://chromedriver.storage.googleapis.com/112.0.5615.28/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip
RUN mv chromedriver /usr/local/bin

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]