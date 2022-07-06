package idnull.znz.illumination2.utils

import android.util.Log
import android.widget.Toast
import idnull.znz.illumination2.MainActivity

lateinit var APP_ACTIVITY:MainActivity
const val ID_FB = "id"
const val NAME = "name"
const val TEXT = "text"
const val MESSAGE_NODE = "users"

var TYPE_ITEM = 0






fun ShowToast(massage:String){
    Toast.makeText(APP_ACTIVITY,massage,Toast.LENGTH_LONG).show()
}
fun logShowExept(e:Exception){

    Log.d("MYONLYTAG","EROR $e")
}

fun mylog(s:String){
    Log.d("MYONLYTAG","отработал $s")
}

class Utils {


}