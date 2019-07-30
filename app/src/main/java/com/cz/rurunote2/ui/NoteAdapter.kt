package com.cz.rurunote2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.cz.rurunote2.R
import com.cz.rurunote2.db.Note
import kotlinx.android.synthetic.main.note_row_layout.view.*

class NoteAdapter(val notes: List<Note>, val clickListener: (Note) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes.get(position)
        (holder as NoteViewHolder).bind(note, clickListener)
    }
}

class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(note: Note, clickListener: (Note) -> Unit) {
        view.title_text.text = note.title
        view.note_text.text = note.note
        view.setOnClickListener { clickListener(note) }
    }


}