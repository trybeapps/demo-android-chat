package io.rapid.rapichat.utils

import java.util.*

object User {
    private val firstNames = arrayOf(
            "Lazy",
            "Creative",
            "Lonely",
            "Red",
            "Crafty",
            "Happy",
            "Fizzy",
            "Crazy",
            "Black",
            "Easy",
            "Freaky",
            "Open",
            "Hot",
            "Vibrant",
            "Epic"
    )
    private val lastNames = arrayOf(
            "Dog",
            "Puppy",
            "Crocodile",
            "Elephant",
            "Hippo",
            "Giraffe",
            "Bird",
            "Wolf",
            "Coyote",
            "Frog",
            "Panda",
            "Koala",
            "Bear",
            "Mosquito",
            "Kitty"
    )

    val NAME = generateName()

    private fun generateName(): String {
        val rand = Random()
        return firstNames[rand.nextInt(firstNames.size)] + " " + lastNames[rand.nextInt(lastNames.size)]
    }
}
