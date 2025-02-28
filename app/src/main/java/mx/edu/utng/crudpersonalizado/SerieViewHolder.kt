package mx.edu.utng.crudpersonalizado

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SerieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
    private val tvGenero: TextView = itemView.findViewById(R.id.tvGenero)
    private val tvPlataforma: TextView = itemView.findViewById(R.id.tvPlataforma)
    private val tvCalificacion: TextView = itemView.findViewById(R.id.tvCalificacion)
    private val ivPortada: ImageView = itemView.findViewById(R.id.ivPortada)

    fun bind(serie: Serie) {
        tvTitulo.text = serie.titulo
        tvGenero.text = serie.genero
        tvPlataforma.text = serie.plataforma
        tvCalificacion.text = "${serie.calificacion}/10"

        // Cargar imagen con Glide si hay URL
        if (serie.imagenUrl.isNotEmpty()) {
            Glide.with(itemView.context)
                .load(serie.imagenUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(ivPortada)
        } else {
            ivPortada.setImageResource(R.drawable.placeholder_image)
        }
    }
}