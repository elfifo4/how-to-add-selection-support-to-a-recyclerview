package com.tutsplus.recyclerviewselectiontutorial

import androidx.recyclerview.selection.SelectionTracker

fun SelectionTracker<Long>.selectFirst() {
    select(0)
}

fun <T> SelectionTracker<Long>.selectLast(list: List<T>) {
    select(list.lastIndex.toLong())
}

fun <T> SelectionTracker<Long>.selectAll(list: List<T>) {
    list.forEachIndexed { index, _ -> select(index.toLong()) }
}

fun <T> SelectionTracker<Long>.deselectAll(list: List<T>) {
    list.forEachIndexed { index, _ -> deselect(index.toLong()) }
}
