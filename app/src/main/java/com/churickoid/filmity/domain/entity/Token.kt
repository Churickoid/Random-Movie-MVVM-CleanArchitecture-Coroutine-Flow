package com.churickoid.filmity.domain.entity

data class Token(
    val apiKey: String,
    val email: String,
    val isCustom: Boolean
){
    override fun toString(): String {
        return email
    }

    companion object{
        val defaultTokens = listOf(Token("5c2d749b-5c0c-4809-b62d-a3c98a9f527e", "Guest-1",false),
            Token("326d244f-a438-4085-afb8-231fa4fb4702", "Guest-2",false),
            Token("6b31e33d-9d30-4513-9ef6-7705aad38ee1", "Guest-3",false),
            Token("6ff9826e-b0fd-4cf9-a5bb-1ce38203c63a", "Guest-4",false),
            Token("58426938-fa7e-4d39-a706-4dbcbe34b12c", "Guest-5",false),
            Token("eeebb31a-8616-4ca2-af45-e8554579257d", "Guest-6",false),
            Token("2b1f78b5-4812-4b60-bb0c-1861d09c3383", "Guest-7",false),
            Token("e6d848c7-4d0e-4f5a-b6a1-51407575348a", "Guest-8",false)


            )


    }
}