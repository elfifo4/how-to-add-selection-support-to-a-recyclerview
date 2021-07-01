package com.tutsplus.recyclerviewselectiontutorial

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tracker: SelectionTracker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val source = ('A'..'Z').joinToString("")
        val randomString: () -> String = {
            Random().ints(10, 0, source.length)
                .toArray()
                .map(source::get)
                .joinToString("")
        }

        val myList: List<Person> = List(20) { index ->
            Person(randomString(), index.plus(1).times(10).toString().let { "$it-$it" })
        }

        my_rv.layoutManager = LinearLayoutManager(this)
        my_rv.setHasFixedSize(true)

        val adapter = MyAdapter(myList, this).apply {
            setHasStableIds(true)
        }.also {
            my_rv.adapter = it
        }

        tracker = SelectionTracker.Builder(
            "selection-1",
            my_rv,
            StableIdKeyProvider(my_rv),
            MyLookup(my_rv),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        if (savedInstanceState != null) {
            tracker.onRestoreInstanceState(savedInstanceState)
        }

        adapter.setTracker(tracker)

        tracker.selectFirst()
//        tracker.selectLast(myList)
//        tracker.selectAll(myList)
        onSelectionChangedListener(myList)

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                onSelectionChangedListener(myList)
            }
        })
    }

    private fun onSelectionChangedListener(list: List<Person>) {
        tracker.selection.map { list[it.toInt()] }.let {
            Log.d("selected", it.toString())
        }

        val nItems: Int = tracker.selection.size()

        if (nItems > 0) {
            title = "$nItems items selected"
            supportActionBar?.setBackgroundDrawable(
                ColorDrawable(Color.parseColor("#ef6c00"))
            )
        } else {
            title = "RVSelection"
            supportActionBar?.setBackgroundDrawable(
                ColorDrawable(getColor(R.color.colorPrimary))
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker.onSaveInstanceState(outState)
    }
}
