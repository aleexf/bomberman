package com.aleexf.game.sound

import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

import com.aleexf.logging.LoggingFactories

object SoundLoader {
    private val logger by lazy { LoggingFactories.clientFactory.getLogger(this.javaClass.name) }

    fun loadSound(path: String, volume: Double): Clip? {
        try {
            val clip = AudioSystem.getClip()
            clip.open(AudioSystem.getAudioInputStream(File(path)))

            val gControl = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            gControl.value = volume.toFloat()

            return clip
        } catch (e: Exception) {
            logger.error("Sound at ${path} not found")
            logger.warning(e.toString())
        }
        return null
    }
}