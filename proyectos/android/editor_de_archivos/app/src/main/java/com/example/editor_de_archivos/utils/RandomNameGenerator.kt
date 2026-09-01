package com.example.editor_de_archivos.utils
import kotlin.random.Random

object RandomNameGenerator {

    private const val CHARACTERS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    private const val NAME_LENGTH = 10

    fun generate(): String {

        return buildString {
            repeat(NAME_LENGTH) {
                append(
                    CHARACTERS[
                        Random.nextInt(CHARACTERS.length)
                    ]
                )
            }
        }
    }
}