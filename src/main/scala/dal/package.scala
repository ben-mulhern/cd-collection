import sqlest._

package object dal {
	
  val upper = ScalarFunction1[String, String]("upper")

}