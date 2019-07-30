package com.cz.rurunote2.ui


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
                var notess = arrayListOf<Note>()
                notess.addAll(notes)
                adapter = NoteAdapter(notes, { note: Note -> itemClicked(note) })
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
                    //Remove all list
                    recycler.removeAllViewsInLayout()
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
                it.toast("Remove ${note.title}")
            }
        }

    }


}
