package com.example.randommovie.domain.entity

data class Token(
    val apiKey: String,
    val email: String,
    val isCustom: Boolean
){
    override fun toString(): String {
        return email
    }

    companion object{
        val tokens = listOf(Token("5c2d749b-5c0c-4809-b62d-a3c98a9f527e", "Guest-1",false),
            Token("6b31e33d-9d30-4513-9ef6-7705aad38ee1", "Guest-2",false),
            Token("326d244f-a438-4085-afb8-231fa4fb4702", "Guest-3",false)
            )


    }
}