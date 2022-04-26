package br.com.eduardo.test.enviroment.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
class Greeting(
    var name: String
) {
    @field:Id var id: Long? = null
}
