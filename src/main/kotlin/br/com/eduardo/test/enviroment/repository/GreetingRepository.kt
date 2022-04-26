package br.com.eduardo.test.enviroment.repository

import br.com.eduardo.test.enviroment.model.Greeting
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface GreetingRepository : ReactiveCrudRepository<Greeting, Long> {

    fun findBy(pageable: Pageable) : Flux<Greeting>
}