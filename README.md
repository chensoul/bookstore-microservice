# BookStore Microservices

![](docs/bookstore-spring-microservices.png)


## Prerequisites
* JDK 21 or later. Recommend using [SDKMAN](https://sdkman.io/) for [managing Java versions](https://youtu.be/ZywEiw3EO8A).
* Docker ([installation instructions](https://docs.docker.com/engine/install/)). Recommend using [OrbStack](https://orbstack.dev/).
* Install [IntelliJ IDEA](https://www.jetbrains.com/idea) or any of your favorite IDE.
* Install [Postman](https://www.postman.com/) or any REST Client.
* Install [go-task](https://task-zh.readthedocs.io/zh-cn/latest) for managing run scripts.

## How to get started?

```shell
$ git clone https://github.com/chensoul/bookstore-microservice.git
$ cd bookstore-microservice

# brew tap sdkman/tap
# brew install sdkman-cli
$ sdk env install

# brew install go-task/tap/go-task
$ task test build

# Run tests
$ ./mvnw verify

# Format code
$ ./mvnw spotless:apply

# Run application
# Run/Debug ApiGatewayApplication.java from your IDE.
```

## References

- https://github.com/feature-tracker
- https://github.com/SaiUpadhyayula/spring-boot-3-microservices-course
