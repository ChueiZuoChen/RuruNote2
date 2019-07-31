package com.cz.rurunote2.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.navigation.fragment.findNavController

import com.cz.rurunote2.R
import com.cz.rurunote2.db.Note
import com.cz.rurunote2.db.NoteDatabase
import com.cz.rurunote2.util.toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            edit_title.setText(note?.title)
            edit_note.setText(note?.note)
        }


        button_save.setOnClickListener {
            val noteTitle = edit_title.text.toString().trim()
            val noteBody = edit_note.text.toString().trim()

            if (noteTitle.isBlank()) {
                edit_title.error = "title required"
                edit_title.requestFocus()
            } else if (noteBody.isBlank()) {
                edit_note.error = "note required"
                edit_note.requestFocus()
            } else {
                launch {
                    context?.let {

                        val mNote = Note(noteTitle, noteBody)
                        if (note == null) {
                            NoteDatabase(it).getNoteDao().addNote(mNote)
                            it.toast("Note added...")
                        } else {
                            mNote.id = note!!.id
                            NoteDatabase(it).getNoteDao().updateNote(mNote)
                            it.toast("Note update...")
                        }
                        findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment())
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (note != null) {
                deleteNote()
            } else {
                context!!.toast("You cannot delete")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Delete action")
            setMessage("Are you sure you want to delete ${note!!.title}?")
            setPositiveButton("YES"){_,_->
                launch {
                    NoteDatabase(context).getNoteDao().removeNote(note!!)
                    findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment())
                }
            }
            setNegativeButton("Cancel"){_,_->
                context.toast("Cannot delete")
            }
        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
