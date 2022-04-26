package br.com.eduardo.test.enviroment.controller

import br.com.eduardo.test.enviroment.model.Greeting
import br.com.eduardo.test.enviroment.model.NewGreetingRequest
import br.com.eduardo.test.enviroment.model.NewGreetingResponse
import br.com.eduardo.test.enviroment.repository.GreetingRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("/hello")
class GreetingController(
    val repository: GreetingRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun registerGreeting(@RequestBody request: NewGreetingRequest) : Mono<NewGreetingResponse> {
        return Mono.fromCallable {
            request.toGreeting()
        }
            .publishOn(Schedulers.boundedElastic())
            .flatMap {
                repository.save(it)
                    .also { logger.info(Thread.currentThread().name) }
            }
            .publishOn(Schedulers.parallel())
            .flatMap{
                Mono.just(NewGreetingResponse(it.id, it.name))
                    .also { logger.info(Thread.currentThread().name) }
            }
    }

    @GetMapping
    fun findAllGreetings(@RequestParam page: Int,
              @RequestParam size: Int,
              @RequestParam orderBy: String
    ): Flux<Greeting> {
        val pageable = PageRequest.of(page, size, Sort.by(orderBy))

        return  repository.findBy(pageable)
    }
}

