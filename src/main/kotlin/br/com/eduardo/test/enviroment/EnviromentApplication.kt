package br.com.eduardo.test.enviroment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EnviromentApplication

fun main(args: Array<String>) {
	runApplication<EnviromentApplication>(*args)
}
