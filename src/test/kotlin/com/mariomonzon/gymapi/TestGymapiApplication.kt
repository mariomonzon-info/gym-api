package com.mariomonzon.gymapi

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<GymapiApplication>().with(TestcontainersConfiguration::class).run(*args)
}
