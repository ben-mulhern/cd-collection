package response

sealed trait ActionResponse[+A] {
  def isValid: Boolean
}

case class ActionSuccess[A](value: A) extends ActionResponse[A] {
  val isValid = true
}

case class ActionFailure[A](errors: Seq[ActionError]) extends ActionResponse[A] {
  val isValid = false
}

case class ActionError(message: String)