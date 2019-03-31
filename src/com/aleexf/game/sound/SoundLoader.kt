package com.aleexf.game.sound

import com.aleexf.logging.Logger
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

fun loadSound(path:String, volume:Double): Clip? {
    try {
        val clip = AudioSystem.getClip()
        clip.open(AudioSystem.getAudioInputStream(File(path)))

        val gControl = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
        gControl.value = volume.toFloat()

        return clip
    } catch (e:Exception) {
        Logger.error("File not found", "Sound ${path} not found", false)
        Logger.error("[Exception]: ", e.toString())
    }
    return null
}