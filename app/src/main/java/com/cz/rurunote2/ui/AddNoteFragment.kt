package com.cz.rurunote2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_save.setOnClickListener {
            val noteTitle = edit_title.text.toString().trim()
            val noteBody = edit_note.text.toString().trim()

            if (noteTitle.isBlank()){
                edit_title.error = "title required"
                edit_title.requestFocus()
            } else if(noteBody.isBlank()){
                edit_note.error = "note required"
                edit_note.requestFocus()
            } else {
                launch {
                    context?.let {
                        val notes = NoteDatabase(it).getNoteDao().addNote(Note(noteTitle,noteBody))
                        it.toast("Note Added...")
                        findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment())
                    }
                }
            }




        }

    }


}
