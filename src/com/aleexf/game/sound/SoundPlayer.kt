package com.aleexf.game.sound

class SoundPlayer(val s: Sound, var loop: Boolean = false) {
    fun play() {
        object: Thread() {
            init {
                isDaemon = true
                this.start()
            }
            override fun run() {
                playOnce()
                while (loop && s.sound != null) {
                    playOnce()
                }
                s.sound?.stop()
            }

            private fun playOnce() {
                s.sound?.let {
                    it.framePosition = 0
                    it.start()
                    sleep(it.microsecondLength / 1000)
                    it.stop()
                }
            }
        }
    }
    fun stop() {
        loop = false
        s.sound?.stop()
    }
}
