@file:Suppress("UNUSED_PARAMETER") // <- REMOVE
package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.math.pow

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = Math.sqrt(dx.pow(2) + dy.pow(2))

  val radiant: Double
    get() {
      val cosWithE1: Double = dx / magnitude
      if (dy > 0)
        return Math.acos(cosWithE1)
      return -Math.acos(cosWithE1)
    }

  val degree: Double
    get() = Math.toDegrees(radiant)

  val unit: Vector2D
    get() = Vector2D(dx / magnitude, dy / magnitude)

  val normal: Vector2D
    get() = Vector2D(dy, -dx) / magnitude

  operator fun times(scalar: Double): Vector2D {
    return Vector2D(dx * scalar, dy * scalar)
  }

  operator fun div(scalar: Double): Vector2D {
    return this * (1 / scalar)
  }

  operator fun times(v: Vector2D): Double {
    return dx * v.dx + dy * v.dy
  }

  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(dx + v.dx, dy + v.dy)
  }

  operator fun plus(p: Point2D): Point2D {
    return Point2D(dx + p.x, dy + p.y)
  }

  operator fun unaryMinus(): Vector2D {
    return Vector2D(-dx, -dy)
  }

  operator fun minus(v: Vector2D): Vector2D {
    return Vector2D(dx - v.dx, dy - v.dy)
  }

  fun scalarProject(target: Vector2D): Double {
    val result: Double
    if (target.dy == 0.0)
      result = dx
    else if (target.dx == 0.0)
      result = dy
    else {
      val lambda: Double = (this * target) / (target * target)
      result = lambda * target.magnitude
    }
    return result
  }

  fun vectorProject(target: Vector2D): Vector2D {
    val result: Vector2D
    if (target.dy == 0.0)
      result = Vector2D(dx, 0.0)
    else if (target.dx == 0.0)
      result = Vector2D(0.0, dy)
    else {
      val lambda: Double = this * (target.unit / target.magnitude)
      result = lambda * target
    }
    return result
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return v * this
}
