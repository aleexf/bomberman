package com.aleexf.game.world.cell

open abstract class Object(
    var x:Int,
    var y:Int,
    val breakByExplosion:Boolean, 
    var collision:Int
)
