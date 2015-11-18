package com.github.baocin.CardAPI

import com.fasterxml.jackson._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.StringWriter

object Utilities {


  def convertToJson[T](obj : T) : String = {
    var obm = new ObjectMapper();
    obm.configure(SerializationFeature.INDENT_OUTPUT, true);
    var stringObject = new StringWriter();
    obm.writeValue(stringObject, obj);
    return stringObject.toString();
  }

}
