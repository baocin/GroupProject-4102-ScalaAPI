package com.github.baocin.CardAPI

import org.scalatra._
import scalate.ScalateSupport

class CardServlet extends CardapiStack {
  get("/") {
    <html>
      <body>
        <h1>Card API</h1>
        An easy to use api for building card games!<br></br>
        Built with <a href="http://www.scala-lang.org/">Scala</a>, <a href="http://scalatra.org/">Scalatra</a>, <a href="http://wiki.fasterxml.com/JacksonHome/">Jackson</a>,
      </body>
    </html>
  }

  get("/card") {
    Utilities.convertToJson(new Card("Heart", 10, "red"))
  }


}
