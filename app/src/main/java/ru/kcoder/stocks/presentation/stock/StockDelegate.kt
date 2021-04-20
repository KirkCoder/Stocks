package ru.kcoder.stocks.presentation.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.stock_item.view.*
import ru.kcoder.stocks.R

class StockDelegate(
    private val onSelectStock: (StockPresentation) -> Unit
) : AdapterDelegate<List<StockPresentation>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        )
    }

    override fun isForViewType(items: List<StockPresentation>, position: Int): Boolean {
        return true
    }

    override fun onBindViewHolder(
        items: List<StockPresentation>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position]
        with(holder.itemView) {
            stockNameTextView.text = item.name
            stockIdTextView.text = item.id
            setOnClickListener {
                onSelectStock(item)
            }
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}