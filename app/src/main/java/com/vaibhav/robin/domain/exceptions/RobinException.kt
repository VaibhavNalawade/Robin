package com.vaibhav.robin.domain.exceptions

 abstract class RobinException(val errorCode:Int,val error:String):Exception(error) {

}