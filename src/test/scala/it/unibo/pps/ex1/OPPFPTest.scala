package it.unibo.pps.ex1

import org.junit.Assert.assertEquals
import org.junit.Test

class OPPFPTest:

  val reference = List(1, 2, 3, 4)

  @Test def testZipWithValue(): Unit =
    val value = 10
    val result = reference.zipWithValue(value)
    val expectedResult = List((1, 10), (2, 10), (3, 10), (4, 10))
    assertEquals(expectedResult, result)

  @Test def testLength(): Unit =
    val length = reference.length()
    val expectedLength = 4
    assertEquals(expectedLength, length)

  @Test def testIndices(): Unit =
    val indices = reference.indices()
    val expectedIndices = List(0, 1, 2, 3)
    assertEquals(expectedIndices, indices)

  @Test def testZipWithIndex(): Unit =
    val result = reference.zipWithIndex
    val expectedResult = List((1, 0), (2, 1), (3, 2), (4, 3))
    assertEquals(expectedResult, result)

  @Test def testPartition(): Unit =
    val predicate: Int => Boolean = _ % 2 == 0
    val result = reference.partition(predicate)
    val expectedResult = (List(2, 4), List(1, 3))
    assertEquals(expectedResult, result)

  @Test def testSpan(): Unit =
    val predicate: Int => Boolean = _ % 2 != 0
    val result = reference.span(predicate)
    val expectedResult = (List(1), List(2, 3, 4))
    assertEquals(expectedResult, result)

  @Test def testSpanWithDifferentPredicate(): Unit =
    val predicate: Int => Boolean = _ < 3
    val result = reference.span(predicate)
    val expectedResult = (List(1, 2), List(3, 4))
    assertEquals(expectedResult, result)

  @Test def testTakeRight(): Unit =
    val n = 3
    val result = reference.takeRight(n)
    val expectedResult = List(2, 3, 4)
    assertEquals(expectedResult, result)

  @Test def testCollect(): Unit =
    val predicate: PartialFunction[Int, Int] = { case x if x % 2 == 0 => x + 1 }
    val result = reference.collect(predicate)
    val expectedResult = List(3, 5)
    assertEquals(expectedResult, result)
