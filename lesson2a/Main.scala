// scanye.scalalessons.animals

trait Animal:
  def eat(): Unit

trait HouseholdAppliance:
  def break(): Unit

case class Cattle(name: String) extends Animal with HouseholdAppliance {
    def eat(): Unit = println("Eating!")
    def break(): Unit = println("Wziuuuuu...")
}

// ADT = Abstract Data Type
// Sealed family of case classes
sealed trait Season
case class Spring(warm: Boolean) extends Season
case object Summer extends Season
case object Autumn extends Season
case object Winter extends Season

sealed abstract class NotificationService
class DummyService extends NotificationService
class RealService extends NotificationService

object Season:
    def order(season: Season): Int =
        season match
            case Spring => 1
            case Summer => 2
            case Autumn => 3
            case Winter => 4

object Main:
  def main(args: Array[String]): Unit =
    val kittle = Cattle("kittle")

    kittle.eat()
    kittle.break()

    println(s"Which in order is spring? ${Season.order(Spring)}")
