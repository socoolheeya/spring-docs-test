package com.test.springdocstest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.test.springdocstest"])
class SpringDocsTestApplication

fun main(args: Array<String>) {
    runApplication<SpringDocsTestApplication>(*args)
}
