package galaxyraiders.core.game

import galaxyraiders.Config
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

object ExplosionConfig {
  private val exploConfig = Config(prefix = "GR__CORE__GAME__EXPLOSION__")
  private val gameConfig = Config(prefix = "GR__CORE__GAME__GAME_ENGINE__")

  val explosionLifeSpan = exploConfig.get<Int>("LIFE__SPAN")
  val explosionRadius = exploConfig.get<Double>("RADIUS")
  private val frameRate = gameConfig.get<Int>("FRAME_RATE")
  val msPerFrame: Int = MILLISECONDS_PER_SECOND / this.frameRate
}

class Explosion(
  initialPosition: Point2D,
) :
  SpaceObject("Explosion", 'X', initialPosition, Vector2D(0.0, 0.0), ExplosionConfig.explosionRadius, 0.0) {
  var explosionLifeTime: Int = 0

  fun age() {
    explosionLifeTime += ExplosionConfig.msPerFrame
  }

  fun vanished(): Boolean {
    return explosionLifeTime > ExplosionConfig.explosionLifeSpan
  }

  fun stillLasts(): Boolean {
    return explosionLifeTime <= ExplosionConfig.explosionLifeSpan
  }
}
