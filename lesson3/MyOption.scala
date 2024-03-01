sealed abstract class MyOption[+A]
case object MyNone extends MyOption[Nothing]
final case class MySome[A](value: A) extends MyOption[A]

extension [A](opt: MyOption[A]) {
  def map[B](f: A => B): MyOption[B] = opt match {
    case MyNone    => MyNone
    case MySome(a) => MySome(f(a))
  }

  def flatMap[B](f: A => MyOption[B]): MyOption[B] = opt.map(f).flatten

  def isDefined: Boolean = opt match {
    case MyNone    => false
    case MySome(_) => true
  }

  def get: A = opt match {
    case MyNone    => throw new NoSuchElementException("MyNone.get")
    case MySome(a) => a
  }
}

extension [A, Sub <: MyOption[A]](opt: MyOption[Sub]) {
  def flatten: MyOption[A] = opt match {
    case MyNone    => MyNone
    case MySome(a) => a
  }
}

/* This is the same, but in Scala 2:
 * implicit class MyOptionOps[A](opt: MyOption[A]) {
 *   def map[B](f: A => B): MyOption[B] = ???
 * }
 */

// Invariance ( ): MyOption[A] IS NOT A SUBTYPE OF MyOption[B] under any circumstances.
// Covariance (+): MyOption[A] IS A SUBTYPE OF MyOption[B] if A IS A SUBTYPE OF B.
//   Eg. MyOption[Cat] IS A SUBTYPE OF MyOption[Animal].
// Contravariance (-): MyOption[A] IS A SUBTYPE OF MyOption[B] if A IS A SUPERTYPE OF B.
//   Eg. MyOption[Animal] IS A SUBTYPE OF MyOption[Cat].
