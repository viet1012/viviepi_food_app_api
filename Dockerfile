
# Cập nhật gói và cài đặt OpenJDK 17 (nếu cần)
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Sao chép mã nguồn vào thư mục /usr/src/app trong container
COPY . /usr/src/app

# Thiết lập thư mục làm việc mặc định
WORKDIR /usr/src/app

# Sử dụng Maven để build project
RUN mvn clean package

FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ./target/Viviepi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]