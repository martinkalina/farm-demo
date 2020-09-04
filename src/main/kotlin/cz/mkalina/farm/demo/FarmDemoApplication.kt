package cz.mkalina.farm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FarmDemoApplication

fun main(args: Array<String>) {
	runApplication<FarmDemoApplication>(*args)
}
