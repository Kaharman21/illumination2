package idnull.znz.illumination2.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import idnull.znz.illumination2.R
import idnull.znz.illumination2.data.ChatMeMassage
import idnull.znz.illumination2.data.ChatOtherMassage
import idnull.znz.illumination2.data.ChatRV


class RecyclerViewAllMessage : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val meMessage = 1
    private val otherMessage = 2

    private val massageList: MutableList<ChatRV> = ArrayList()
    fun setData(data:List<ChatRV>){
        massageList.clear()
        massageList.addAll(data)

      //  mylog("my class $data")
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return when(massageList[position]){
            is ChatMeMassage -> 1
            else -> 2
        }
//        return if (FireBaseInit.aunt.currentUser?.email.toString() == massageList[position].name) {
//            TYPE_ITEM = meMessage
//            meMessage
//        } else {
//            TYPE_ITEM = otherMessage
//            otherMessage
//        }
    }
     class ItemMessage(view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById<TextView>(R.id.name)
        val itemText = view.findViewById<TextView>(R.id.text_message)
        fun bind(model: ChatOtherMassage) {
        //    mylog("fun  bind in item message other")
            itemName.text = model.name
            itemText.text = model.text
        }
    }
    class ItemMessageMe(view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById<TextView>(R.id.nameMe)
        val itemText = view.findViewById<TextView>(R.id.text_messageMe)
        fun bind(model: ChatMeMassage) {
         //   mylog("fun  bind in item message me")
            itemName.text = model.name
            itemText.text = model.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
 //       mylog("view we viewtype $viewType")
//        return when(viewType){
//
//            1 ->  ItemMessageMe(
//                LayoutInflater.from(parent.context).inflate(R.layout.item_me, parent, false)
//            )
//
//
//            else -> ItemMessage(
//                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//            )
//        }
         if (viewType == 1) {
   //          mylog("block if in on create")
            return ItemMessageMe(
                LayoutInflater.from(parent.context).inflate(R.layout.item_me, parent, false)
            )
        } else {
        //    mylog("block else in un create ")
             return  ItemMessage(
                 LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
             )
        }
    }

    override fun getItemCount(): Int = massageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
   //    mylog("we holder $holder")
        when (holder) {
            is ItemMessageMe -> holder.bind(model = (massageList[position] as ChatMeMassage))
            is ItemMessage -> holder.bind(model = (massageList[position] as ChatOtherMassage))
        }
    }
}







