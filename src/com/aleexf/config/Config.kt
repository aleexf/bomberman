package com.aleexf.config

object Config {
    const val Version = "~2.0.5"

    // Game
    const val GameFieldRows = 15
    const val GameFieldCols = 15

    const val MapBadBonusCount       = 5
    const val MapTotalBonusCount     = 15
    const val BadBonusEffectDuration = 10 * 1000L

    // Player stats
    const val PlayerSpeed           = 5
    const val PlayerMaxSpeed        = 10
    const val PlayerBombDelay       = 3000L
    const val PlayerBombCount       = 1
    const val PlayerExplosionLen    = 2
    const val PlayerMaxExplosionLen = 16

    const val BombMovingSpeed = 5

    // Graph
    const val FPS          = 60
    const val WindowWidth  = 495
    const val WindowHeight = 518

    // Net
    const val Port = 8080
}