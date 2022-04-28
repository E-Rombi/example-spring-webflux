package br.com.eduardo.test.enviroment.controller

import br.com.eduardo.test.enviroment.model.Greeting
import br.com.eduardo.test.enviroment.repository.GreetingRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import reactor.core.publisher.Flux
import java.net.URI


@SpringBootTest
@AutoConfigureMockMvc
internal class GreetingControllerTest {

    @MockBean
    lateinit var repository: GreetingRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    val existsId: Long = 1
    val greeting: Greeting = Greeting("Eduardo").apply { id = existsId }
    val pageable: Pageable = PageRequest.of(0, 1000, Sort.by("id"))

    @WithMockUser(roles = ["admin"])
    @Test
    fun `should return Greetings when authenticated and with admin role`() {
        `when`(repository.findBy(pageable))
            .thenReturn(Flux.just(greeting))

        mockMvc.perform(
            MockMvcRequestBuilders.get(
                URI("/hello?page=0&size=1000&orderBy=id")
            )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @WithMockUser
    @Test
    fun `should return FORBIDDEN without admin role`() {
        `when`(repository.findBy(pageable))
            .thenReturn(Flux.just(greeting))

        mockMvc.perform(
            MockMvcRequestBuilders.get(
                URI("/hello?page=0&size=1000&orderBy=id")
            )
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun `should return UNAUTHORIZED without bearer token`() {
        `when`(repository.findBy(pageable))
            .thenReturn(Flux.just(greeting))

        mockMvc.perform(
            MockMvcRequestBuilders.get(
                URI("/hello?page=0&size=1000&orderBy=id")
            )
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}