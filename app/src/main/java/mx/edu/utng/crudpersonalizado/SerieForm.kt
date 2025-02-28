package mx.edu.utng.crudpersonalizado

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SerieForm : AppCompatActivity() {
    private lateinit var dbHelper: SeriesDbHelper
    private var serieId: Int = -1
    private var isEditMode = false

    // UI Components
    private lateinit var etTitulo: EditText
    private lateinit var etGenero: EditText
    private lateinit var etPlataforma: EditText
    private lateinit var etAnio: EditText
    private lateinit var etTemporadas: EditText
    private lateinit var etCalificacion: EditText
    private lateinit var spinnerEstado: Spinner
    private lateinit var etSinopsis: EditText
    private lateinit var etImagenUrl: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_form)

        // Inicializar base de datos
        dbHelper = SeriesDbHelper(this)

        // Inicializar vistas
        etTitulo = findViewById(R.id.etTitulo)
        etGenero = findViewById(R.id.etGenero)
        etPlataforma = findViewById(R.id.etPlataforma)
        etAnio = findViewById(R.id.etAnio)
        etTemporadas = findViewById(R.id.etTemporadas)
        etCalificacion = findViewById(R.id.etCalificacion)
        spinnerEstado = findViewById(R.id.spinnerEstado)
        etSinopsis = findViewById(R.id.etSinopsis)
        etImagenUrl = findViewById(R.id.etImagenUrl)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Configurar Spinner
        val estados = arrayOf("En emisión", "Finalizada", "Cancelada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter

        // Verificar si estamos editando o creando
        serieId = intent.getIntExtra("SERIE_ID", -1)
        isEditMode = serieId != -1

        if (isEditMode) {
            title = "Editar Serie"
            loadSerieData()
        } else {
            title = "Nueva Serie"
        }

        // Configurar botón guardar
        btnGuardar.setOnClickListener {
            saveSerie()
        }
    }

    private fun loadSerieData() {
        val serie = dbHelper.getSerieById(serieId)
        serie?.let {
            etTitulo.setText(it.titulo)
            etGenero.setText(it.genero)
            etPlataforma.setText(it.plataforma)
            etAnio.setText(it.anioEstreno.toString())
            etTemporadas.setText(it.temporadas.toString())
            etCalificacion.setText(it.calificacion.toString())
            etSinopsis.setText(it.sinopsis)
            etImagenUrl.setText(it.imagenUrl)

            // Seleccionar el estado en el spinner
            when (it.estado) {
                "En emisión" -> spinnerEstado.setSelection(0)
                "Finalizada" -> spinnerEstado.setSelection(1)
                "Cancelada" -> spinnerEstado.setSelection(2)
                else -> spinnerEstado.setSelection(0)
            }
        }
    }

    private fun saveSerie() {
        // Validar campos
        if (!validateFields()) {
            return
        }

        try {
            // Recoger datos del formulario
            val titulo = etTitulo.text.toString().trim()
            val genero = etGenero.text.toString().trim()
            val plataforma = etPlataforma.text.toString().trim()
            val anio = etAnio.text.toString().toInt()
            val temporadas = etTemporadas.text.toString().toInt()
            val calificacion = etCalificacion.text.toString().toFloat()
            val estado = spinnerEstado.selectedItem.toString()
            val sinopsis = etSinopsis.text.toString().trim()
            val imagenUrl = etImagenUrl.text.toString().trim()

            // Crear objeto Serie
            val serie = Serie(
                id = if (isEditMode) serieId else 0,
                titulo = titulo,
                genero = genero,
                plataforma = plataforma,
                anioEstreno = anio,
                temporadas = temporadas,
                calificacion = calificacion,
                estado = estado,
                sinopsis = sinopsis,
                imagenUrl = imagenUrl
            )

            // Guardar en la base de datos
            val result = if (isEditMode) {
                dbHelper.updateSerie(serie)
            } else {
                dbHelper.insertSerie(serie)
            }

            if (result.toInt() > 0) {
                Toast.makeText(this, "Serie guardada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar la serie", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields(): Boolean {
        if (etTitulo.text.toString().trim().isEmpty()) {
            etTitulo.error = "El título es obligatorio"
            return false
        }

        if (etGenero.text.toString().trim().isEmpty()) {
            etGenero.error = "El género es obligatorio"
            return false
        }

        if (etPlataforma.text.toString().trim().isEmpty()) {
            etPlataforma.error = "La plataforma es obligatoria"
            return false
        }

        if (etAnio.text.toString().trim().isEmpty()) {
            etAnio.error = "El año es obligatorio"
            return false
        }

        if (etTemporadas.text.toString().trim().isEmpty()) {
            etTemporadas.error = "El número de temporadas es obligatorio"
            return false
        }

        if (etCalificacion.text.toString().trim().isEmpty()) {
            etCalificacion.error = "La calificación es obligatoria"
            return false
        } else {
            val calificacion = etCalificacion.text.toString().toFloatOrNull()
            if (calificacion == null || calificacion < 0 || calificacion > 10) {
                etCalificacion.error = "La calificación debe estar entre 0 y 10"
                return false
            }
        }

        return true
    }
}