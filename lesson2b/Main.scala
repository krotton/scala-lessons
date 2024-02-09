val list1: List[Int] = List(1, 2, 3)
val list2 = 1 :: 2 :: 3 :: Nil

// Left-associative: 1 + 2 + 3 --> (1 + 2) + 3
// Right-associative: 1 :: 2 :: 3 :: Nil --> 1 :: (2 :: (3 :: Nil))
// List: A->B->C

val seq: Seq[Int] = Seq(1, 2, 3)
val vec: Vector[Int] = Vector(1, 2, 3)
val set: Set[Int] = Set(1, 2, 3, 1, 1) // == Set(1, 2, 3)
val map: Map[String, Int] = Map(("A", 1), ("B", 2), "C" -> 3)

// But also:
val opt: Option[Int] = Some(6) // or None
//val t: Try[Int] = Success(6) // Failure(Throwable)
//val f: Future[Int] =
val either: Either[String, Int] = Left("Error!") // or Right(6)

object Main:
  // def :+(x: Int): Int = 3 + x

  def main(args: Array[String]): Unit =
    println(list1 == list2)

    // .map(f: X => Y): [X, X, X] ---> [Y, Y, Y]
    //                   ^  ^  ^        
    //                   f  f  f

    // None.map(f)    => [ ] ---> [ ]
    // Some(X).map(f) => [X] ---> [Y]