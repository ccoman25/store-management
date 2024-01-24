FROM openjdk:21
ADD target/store-management-tool.jar store-management-tool.jar
ENTRYPOINT ["java", "-jar", "store-management-tool.jar"]