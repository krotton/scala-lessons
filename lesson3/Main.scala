val list1: List[Int] = List(1, 2, 3)
val list2 = 1 :: 2 :: 3 :: Nil

// Left-associative: 1 + 2 + 3 --> (1 + 2) + 3
// Right-associative: 1 :: 2 :: 3 :: Nil --> 1 :: (2 :: (3 :: Nil))
// List: A->B->C

val seq: Seq[Int] = Seq(1, 2, 3)
val vec: Vector[Int] = Vector(1, 2, 3) //: IndexedSeq[Int]
val set: Set[Int] = Set(1, 2, 3, 1, 1) // == Set(1, 2, 3)
//val map: Map[String, Int] = Map(("A", 1), ("B", 2), "C" -> 3)

// But also:
val opt: Option[Int] = Some(6) // or None
//val t: Try[Int] = Success(6) // Failure(Throwable)
//val f: Future[Int] =
val either: Either[String, Int] = Left("Error!") // or Right(6)

case class Lettuce()
case class Sandwich(lettuce: Lettuce)
case class Bag[A](content: A)

object Main:
  // def :+(x: Int): Int = 3 + x

  def main(args: Array[String]): Unit = {
    // 1 (with match)
    val lettuceOpt = tryToGrowLettuce()
    lettuceOpt match {
      case MySome(lettuce) =>
        val sandwichOpt = tryToMakeSandwich(lettuce)
        sandwichOpt match {
          case MySome(sandwich) =>
            val bagOpt = tryToPackSandwich(sandwich)
            bagOpt match {
              case MySome(bag) => println(bag)
              case MyNone      => println("No bag for you!")
            }
          case MyNone => println("No sandwich for you!")
        }

      case MyNone => println("No lettuce for you!")
    }

    // 2 (with if)
    // Try not to do it, because .get sucks (throws).
    val lettuceOpt2 = tryToGrowLettuce()
    if (lettuceOpt2.isDefined) {
      val sandwichOpt2 = tryToMakeSandwich(lettuceOpt2.get)
      if (sandwichOpt2.isDefined) {
        val bagOpt2 = tryToPackSandwich(sandwichOpt2.get)
        if (bagOpt2.isDefined) {
          println(bagOpt2.get)
        } else {
          println("No bag for you!")
        }
      } else {
        println("No sandwich for you!")
      }
    } else {
      println("No lettuce for you!")
    }

    // 3 (with map)
    // Doesn't make sense, really.
    // -> [lettuce] -> [[sandwich]] -> [[[bag]]]
    val lettuceOpt3 = tryToGrowLettuce()
    val sandwichOptOpt3 = lettuceOpt3.map(tryToMakeSandwich)
    val bagOptOptOpt3: MyOption[MyOption[MyOption[Bag[Sandwich]]]] =
      sandwichOptOpt3.map(_.map(tryToPackSandwich))
    // val bagOpt3 = bagOptOptOpt3 match {
    //  case Some(Some(Some(bag))) => Some(bag)
    //  case None                  => None
    // }
    val bagOpt3 = bagOptOptOpt3.flatten.flatten
    bagOpt3 match {
      case MySome(bag) => println(bag)
      case MyNone      => println("No bag for you!")
    }

    // TEA BREAK:
    val maybeInt1: MyOption[Int] = MySome(3)
    val maybeInt2: MyOption[Int] = MyNone

    println(maybeInt1.map(_ + 1))
    println(MySome(MySome(3)).flatten)
    println(MySome(-1).flatMap(x => if (x < 0) MyNone else MySome(x + 1)))

    // 4 (with flatMap)
    // -> [lettuce] -> [sandwich] -> [bag]
    val bagOpt4 =
      tryToGrowLettuce()
        .flatMap(tryToMakeSandwich)
        .flatMap(tryToPackSandwich)

    // 5 (with for)
    // -> [lettuce] -> [sandwich] -> [bag]
    // In for: <- is flatMap, = is map
    val bagOpt5 = for {
      lettuce  <- tryToGrowLettuce() // F[A]
      sandwich <- tryToMakeSandwich(lettuce) // F[B]
      bag      <- tryToPackSandwich(sandwich) // F[C]
    } yield bag // F[C]
  }

  // .map(f: X => Y): [X, X, X] ---> [Y, Y, Y]
  //                   ^  ^  ^
  //                   f  f  f

  // None.map(f)    => [ ] ---> [ ]
  // Some(X).map(f) => [X] ---> [Y]
  // List().map(f)  => [ ] ---> [ ]
  // List(X).map(f) => [X] ---> [Y]

  // Option(3).toList
  // List(3).headOption // .lastOption

  // None    <- Failure (with no extra context)
  // Some(x) <- Success

  // .flatten: [[X], [X], [X, X]] -> [X, X, X, X]

  // .flatMap: .map + .flatten
  //           [X, X, X] --map--> [[Y], [Y], [Y]] --flatten--> [Y, Y, Y]
  //           f: X => [Y]
  // Lets you add or delete elements!

  def tryToGrowLettuce(): MyOption[Lettuce] =
    if (math.random() > 0.5)
      MySome(Lettuce())
    else
      MyNone

  def tryToMakeSandwich(lettuce: Lettuce): MyOption[Sandwich] =
    if (math.random() > 0.9)
      MySome(Sandwich(lettuce))
    else
      MyNone

  def tryToPackSandwich(sandwich: Sandwich): MyOption[Bag[Sandwich]] =
    if (math.random() > 0.9)
      MySome(Bag(sandwich))
    else
      MyNone
