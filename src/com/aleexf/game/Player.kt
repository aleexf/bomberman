package com.aleexf.game

import java.awt.Image
import java.awt.Graphics

import com.aleexf.config.Config
import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Bonus
import com.aleexf.game.world.cell.Object
import com.aleexf.graphics.drawer.Animationable
import com.aleexf.graphics.drawer.TextureManager


class Player(x: Int, y: Int, val name: String, val playerId: Int, val world: GameWorld):
        Object(x, y, true, 2),
        Animationable
{

    override var tSizeX: Int = 64
    override var tSizeY: Int = 64
    override var rSizeX: Int = 32
    override var rSizeY: Int = 32
    override var texture: Image = TextureManager.iPlayer[playerId][0]
    override val priority: Int = 228

    override var animState: Int? = 0
    override val animDelay: Long? = null
    override fun nextAnimState() {
        val imgId = when (this.direction) {
            Direction.UP -> 0
            Direction.DOWN -> 3
            Direction.LEFT -> 6
            Direction.RIGHT -> 9
        }
        this.texture = TextureManager.iPlayer[playerId][imgId + animState!!]
        animState = animState!! + 1
    }

    override fun drawIn(graph: Graphics) {
        graph.drawImage(texture,
                y - rSizeY / 2, x + rSizeX / 2,
                y + rSizeX / 2, x + rSizeX / 2,
                0, 0,
                tSizeY, tSizeX,
                null
        )
    }

    var alive            = false
    var canPushBomb      = false
    var shuffledKeyboard = false

    var speed          = Config.PlayerSpeed
    var bombDelay      = Config.PlayerBombDelay
    var explosionLen   = Config.PlayerExplosionLen
    var availableBombs = Config.PlayerBombCount

    var animType = 0
    var direction = Direction.DOWN

    fun placeBomb(): Bomb = Bomb(x/32, y/32, bombDelay, this, explosionLen)

    fun move(dir: Direction) {
        val px = this.x + dir.dx * 5
        val py = this.y + dir.dy * 5
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (canPushBomb) {
            val bx = x/32 + dir.dx
            val by = y/32 + dir.dy
            world.objects
                    .filter { it is Bomb && it.collision == 3 && it.x == bx && it.y == by }
                    .forEach {
                        it as Bomb
                        it.speedX = dir.dx
                        it.speedY = dir.dy
                    }
        }
        if (world.anyObject(this.collision+1, (px+dir.dx*16)/32, (py+dir.dy*16)/32)) return
        this.x = this.x + dir.dx * speed
        this.y = this.y + dir.dy * speed
        direction = dir
        this.nextAnimState()
    }
    fun resetStats() {
        alive            = false
        canPushBomb      = false
        shuffledKeyboard = false

        speed          = Config.PlayerSpeed
        bombDelay      = Config.PlayerBombDelay
        explosionLen   = Config.PlayerExplosionLen
        availableBombs = Config.PlayerBombCount

        animType = 0
        direction = Direction.DOWN
    }
    fun applyBonus(b: Bonus) {
        if (b.bType > 3) {
            val oldSpeed = speed
            speed = if (b.speedBoost != 0) b.speedBoost else speed
            bombDelay = if (b.delayBoost != 0) b.delayBoost.toLong() else bombDelay
            shuffledKeyboard = shuffledKeyboard || b.shuffleKeys
            // Todo: Use a coroutine instead of Thread
            object : Thread() {
                init {
                    isDaemon = true
                    this.start()
                }
                override fun run() {
                    sleep(Config.BadBonusEffectDuration)
                    speed = oldSpeed
                    bombDelay = 3000
                    shuffledKeyboard = false
                }
            }
        } else {
            speed = Math.min(Config.PlayerMaxSpeed, speed + b.speedBoost)
            explosionLen = Math.min(Config.PlayerMaxExplosionLen, explosionLen + b.explosionLen)
            availableBombs += b.bombBoost
            canPushBomb = canPushBomb || b.canPushBomb
        }
    }
}