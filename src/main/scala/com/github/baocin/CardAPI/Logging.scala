package com.github.baocin.CardAPI

import org.slf4j.{Logger, LoggerFactory}

//Super simple mixin example
//Say I wanted to be able to swap out logging code on the fly without caring what uses it
//With this trait (which is like a java interface) we can do that! See Card Servlet's definition
trait Logging {
  val logger =  LoggerFactory.getLogger(this.getClass)
}
