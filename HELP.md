# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.github.chat-ai' is invalid and this project uses 'com.github.chat_ai' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/maven-plugin/build-image.html)
* [OpenAI](https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html)
* [Azure OpenAI](https://docs.spring.io/spring-ai/reference/api/chat/azure-openai-chat.html)
* [Vertex AI Gemini](https://docs.spring.io/spring-ai/reference/api/chat/vertexai-gemini-chat.html)
* [Amazon Bedrock](https://docs.spring.io/spring-ai/reference/api/bedrock-chat.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

