package com.aleexf.game.sound

object Player:Thread() {
    fun playSound(s:Sounds, loop:Boolean = false) {
        object: Thread() {
            init {
                isDaemon = true
                this.start()
            }
            override fun run() {
                playIt()
                while (loop && s.sound != null) {
                    playIt()
                }
                s.sound?.stop()
            }
            fun playIt() {
                s.sound?.let {
                    it.framePosition = 0
                    it.start()
                    sleep(it.microsecondLength / 1000)
                    it.stop()
                }
            }

        }
    }

}