import scala.collection.immutable.HashMap

object MyApp:
  def main(args: Array[String]): Unit =
    val addition1 = Addition(1, 3)
    val addition2 = Addition(3, 2)
    val equal = addition1 == addition2
    println(s"$addition1 == $addition2? $equal")

    val textByAddition = HashMap(addition1 -> "Blabla", addition2 -> "123qwe")
    val result = textByAddition(Addition(1, 3))

    println(textByAddition)
    println(result)

/**
  * Represents an addition operation between two operands.
  *
  * @param op1 First (left) operand
  * @param op2 Second (right) operand
  */
class AdditionRaw(op1arg: Int, op2arg: Int):
  override def toString: String = s"Addition($op1, $op2)"

  override def equals(other: Any): Boolean =
    other match
      case addition: Addition => checkEquals(addition)
      case _ => false
  
  override def hashCode(): Int = op1arg + op2arg

  private def checkEquals(other: Addition): Boolean =
    op1 + op2 == other.op1 + other.op2
  
  val op1: Int = op1arg
  val op2: Int = op2arg

object AdditionRaw:
  def apply(op1: Int, op2: Int): AdditionRaw = new AdditionRaw(op1, op2)

/**
  * Represents an addition operation between two operands.
  *
  * This time without all the hassle.
  *
  * @param op1 First (left) operand
  * @param op2 Second (right) operand
  */
case class Addition(op1: Int, op2: Int)

case class AdditionPlus(op1: Int, op2: Int, op3: Int) extends Addition(op1, op2)