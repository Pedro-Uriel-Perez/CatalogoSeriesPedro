package mx.edu.utng.crudpersonalizado

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SerieDetailActivity : AppCompatActivity() {
    private lateinit var dbHelper: SeriesDbHelper
    private var serieId: Int = 0

    // UI Components
    private lateinit var tvTitulo: TextView
    private lateinit var tvGenero: TextView
    private lateinit var tvPlataforma: TextView
    private lateinit var tvAnio: TextView
    private lateinit var tvTemporadas: TextView
    private lateinit var tvCalificacion: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvSinopsis: TextView
    private lateinit var ivPortada: ImageView
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_detail)

        // Inicializar la base de datos
        dbHelper = SeriesDbHelper(this)

        // Obtener el ID de la serie desde el intent
        serieId = intent.getIntExtra("SERIE_ID", -1)
        if (serieId == -1) {
            finish()
            return
        }

        // Inicializar vistas
        tvTitulo = findViewById(R.id.tvTitulo)
        tvGenero = findViewById(R.id.tvGenero)
        tvPlataforma = findViewById(R.id.tvPlataforma)
        tvAnio = findViewById(R.id.tvAnio)
        tvTemporadas = findViewById(R.id.tvTemporadas)
        tvCalificacion = findViewById(R.id.tvCalificacion)
        tvEstado = findViewById(R.id.tvEstado)
        tvSinopsis = findViewById(R.id.tvSinopsis)
        ivPortada = findViewById(R.id.ivPortada)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)

        // Cargar los datos de la serie
        loadSerieData()

        // Configurar botones
        btnEditar.setOnClickListener {
            val intent = Intent(this, SerieForm::class.java)
            intent.putExtra("SERIE_ID", serieId)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun loadSerieData() {
        val serie = dbHelper.getSerieById(serieId)
        serie?.let {
            tvTitulo.text = it.titulo
            tvGenero.text = "Género: ${it.genero}"
            tvPlataforma.text = "Plataforma: ${it.plataforma}"
            tvAnio.text = "Año: ${it.anioEstreno}"
            tvTemporadas.text = "Temporadas: ${it.temporadas}"
            tvCalificacion.text = "Calificación: ${it.calificacion}/10"
            tvEstado.text = "Estado: ${it.estado}"
            tvSinopsis.text = it.sinopsis

            // Cargar imagen con Glide (si existe URL)
            if (it.imagenUrl.isNotEmpty()) {
                Glide.with(this)
                    .load(it.imagenUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(ivPortada)
            } else {
                ivPortada.setImageResource(R.drawable.placeholder_image)
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Serie")
            .setMessage("¿Estás seguro de que deseas eliminar esta serie?")
            .setPositiveButton("Sí") { _, _ ->
                deleteSerie()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteSerie() {
        val deletedRows = dbHelper.deleteSerie(serieId)
        if (deletedRows > 0) {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadSerieData()
    }
}