package com.cz.rurunote2.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager


import com.cz.rurunote2.R
import com.cz.rurunote2.db.Note
import com.cz.rurunote2.db.NoteDatabase
import com.cz.rurunote2.util.toast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    lateinit var notes: List<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                notes = NoteDatabase(it).getNoteDao().getAllNote()
                recycler.adapter = NoteAdapter(notes)

            }
        }

        button_add.setOnClickListener {
            findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment())
        }

        remove_button.setOnClickListener {
            launch {
                context?.let {
                    NoteDatabase(it).getNoteDao().removeAllNotes()
                    notes = listOf()
                    recycler.adapter = NoteAdapter(notes)
                    it.toast("Removed all...")

                }
            }
        }
    }


}
