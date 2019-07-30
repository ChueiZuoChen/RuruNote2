package com.cz.rurunote2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cz.rurunote2.R
import com.cz.rurunote2.db.Note
import kotlinx.android.synthetic.main.note_row_layout.view.*

class NoteAdapter(val notes:List<Note>) :RecyclerView.Adapter<NoteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.note_row_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes.get(position)
        holder.bind(note)
        holder.view.setOnClickListener {

        }
    }
}

class NoteViewHolder(val view:View):RecyclerView.ViewHolder(view){
    var titleNote = view.title_text
    var noteBody = view.note_text

    fun bind(note:Note){
        titleNote.text = note.title
        noteBody.text = note.note
    }
}