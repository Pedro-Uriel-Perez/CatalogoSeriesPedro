package mx.edu.utng.crudpersonalizado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SeriesAdapter(private var series: List<Serie>) : RecyclerView.Adapter<SerieViewHolder>() {
    private var onItemClickListener: ((Serie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Serie) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newList: List<Serie>) {
        series = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_serie_view_holder, parent, false)
        return SerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = series[position]
        holder.bind(serie)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(serie)
        }
    }

    override fun getItemCount(): Int = series.size
}