package dk.bayes.infer.gp

import org.junit._
import Assert._
import dk.bayes.math.linear._
import scala.math._

class GenericGPRegressionTest {

  @Test def test_1d_inputs {

    val x = Matrix(1, 2, 3)
    val y = Matrix(1, 4, 9)
    val z = Matrix(1, 2, 3, 4, 50)

    def covFunc = CovSEiso(sf = 7.5120, ell = 2.1887)

    val prediction = GenericGPRegression.predict(x, y, z, covFunc, noiseVar = pow(0.81075, 2))
    assertEquals(Matrix(5, 2, Array(0.878, 1.246, 4.407, 1.123, 8.614, 1.246, 10.975, 6.063, 0, 57.087)).toString, prediction.toString)
  }

  @Test def test_2d_inputs {

    val x = Matrix(Array(
      Array(1d, 2),
      Array(2d, 3),
      Array(3d, 4)))

    val y = Matrix(1, 4, 9)
    val z = Matrix(Array(
      Array(1d, 2),
      Array(2d, 3),
      Array(3d, 4),
      Array(4d, 5),
      Array(50d, 51)))

    def covFunc = CovSEiso(sf = 7.5120, ell = 2.1887)

    val prediction = GenericGPRegression.predict(x, y, z, covFunc, noiseVar = pow(0.81075, 2))
    assertEquals(Matrix(5, 2, Array(0.93507, 1.28129, 4.18751, 1.23829, 8.77379, 1.28129, 9.62865, 11.226, 0, 57.087)).toString, prediction.toString)
  }
}