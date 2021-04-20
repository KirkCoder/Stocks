package ru.kcoder.stocks.presentation.stock

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StockItemDecorator(
    private val marginBottom: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val count = parent.adapter?.itemCount ?: 0
            if (parent.getChildAdapterPosition(view) == count - 1) {
                bottom = marginBottom
            }
        }
    }
}