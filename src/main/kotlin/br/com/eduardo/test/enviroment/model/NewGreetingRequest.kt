package br.com.eduardo.test.enviroment.model

data class NewGreetingRequest(
    val name: String
) {
    fun toGreeting(): Greeting = Greeting(this.name)
}
