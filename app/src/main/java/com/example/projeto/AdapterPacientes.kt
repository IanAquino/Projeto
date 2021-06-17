package com.example.projeto

import org.w3c.dom.DocumentFragment

class AdapterPacientes(val fragment: DocumentFragment) {
 public var cursor: Cursor? = null
    get() = field
    set(value){
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickList



}