package com.aleexf.game.sound

import java.util.*
import javax.sound.sampled.Clip

enum class Sounds(val sound: Clip?) {
    EXPLOSION(loadSound("./data/sound/explosion.aiff", -20.0)),
    PICKUP(loadSound("./data/sound/pickup.wav", -20.0)),
    BACKGROUND1(loadSound("./data/sound/background01.aiff", -35.0)),
    BACKGROUND2(loadSound("./data/sound/background02.aiff", -25.0)),
    BACKGROUND3(loadSound("./data/sound/background03.aiff", -40.0)),
    BACKGROUND4(loadSound("./data/sound/background04.aiff", -35.0));
    fun randomBackground() = when (Random().nextInt(4)) {
        0 -> BACKGROUND1
        1 -> BACKGROUND2
        2 -> BACKGROUND3
        3 -> BACKGROUND4
        else -> BACKGROUND1
    }
}