package com.app.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootKotlinTemplateApplication

fun main(args: Array<String>) {
	runApplication<SpringBootKotlinTemplateApplication>(*args)
}
