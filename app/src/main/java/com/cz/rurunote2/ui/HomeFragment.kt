package com.cz.rurunote2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var adapter: NoteAdapter
    var notess = arrayListOf<Note>()
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

                notess.addAll(notes)
                adapter = NoteAdapter(notess, { note: Note -> itemClicked(note) })
                recycler.adapter = adapter

            }
        }

        button_add.setOnClickListener {
            findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment())
        }

        remove_button.setOnClickListener {
            launch {
                context?.let {
                    NoteDatabase(it).getNoteDao().removeAllNotes()
                    notess.removeAll(notes)
                    adapter.notifyDataSetChanged()
                    it.toast("Removed all...")

                }
            }
        }
    }

    fun itemClicked(note: Note) {

        launch {
            context?.let {
                NoteDatabase(it).getNoteDao().removeNote(note)
                Log.d(MainActivity::class.java.simpleName,"${note.id}")
                notess.remove(note)
                adapter.notifyDataSetChanged()
                it.toast("Remove ${note.title}")
            }
        }

    }

}
