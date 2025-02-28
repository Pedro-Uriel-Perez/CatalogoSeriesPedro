package mx.edu.utng.crudpersonalizado

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SeriesAdapter
    private lateinit var dbHelper: SeriesDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            Log.d("MainActivity", "Iniciando onCreate")

            setContentView(R.layout.activity_main)
            Log.d("MainActivity", "setContentView completado")

            try {
                dbHelper = SeriesDbHelper(this)
                Log.d("MainActivity", "SeriesDbHelper inicializado")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error inicializando SeriesDbHelper: ${e.message}")
                Toast.makeText(this, "Error en base de datos: ${e.message}", Toast.LENGTH_LONG).show()
                return
            }

            // Configurar RecyclerView
            try {
                recyclerView = findViewById(R.id.recyclerView)
                if (recyclerView == null) {
                    Log.e("MainActivity", "recyclerView no encontrado en el layout")
                    Toast.makeText(this, "Error: recyclerView no encontrado", Toast.LENGTH_LONG).show()
                    return
                }
                recyclerView.layoutManager = LinearLayoutManager(this)
                Log.d("MainActivity", "RecyclerView configurado")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error configurando RecyclerView: ${e.message}")
                Toast.makeText(this, "Error en RecyclerView: ${e.message}", Toast.LENGTH_LONG).show()
                return
            }

            // Configurar el adaptador
            try {
                val series = dbHelper.getAllSeries()
                Log.d("MainActivity", "Series obtenidas: ${series.size}")
                adapter = SeriesAdapter(series)
                recyclerView.adapter = adapter
                Log.d("MainActivity", "Adaptador configurado")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error configurando adaptador: ${e.message}")
                Toast.makeText(this, "Error en adaptador: ${e.message}", Toast.LENGTH_LONG).show()
                return
            }

            // Configurar el clic en los elementos del RecyclerView
            try {
                adapter.setOnItemClickListener { serie ->
                    val intent = Intent(this, SerieDetailActivity::class.java)
                    intent.putExtra("SERIE_ID", serie.id)
                    startActivity(intent)
                }
                Log.d("MainActivity", "Listener de clic configurado")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error configurando listener: ${e.message}")
                Toast.makeText(this, "Error en listener: ${e.message}", Toast.LENGTH_LONG).show()
            }

            // Configurar el botón flotante para agregar series
            try {
                val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
                if (fabAdd == null) {
                    Log.e("MainActivity", "fabAdd no encontrado en el layout")
                    Toast.makeText(this, "Error: fabAdd no encontrado", Toast.LENGTH_LONG).show()
                    return
                }
                fabAdd.setOnClickListener {
                    val intent = Intent(this, SerieForm::class.java)
                    startActivity(intent)
                }
                Log.d("MainActivity", "FAB configurado")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error configurando FAB: ${e.message}")
                Toast.makeText(this, "Error en FAB: ${e.message}", Toast.LENGTH_LONG).show()
            }

            Log.d("MainActivity", "onCreate completado con éxito")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error general en onCreate: ${e.message}")
            e.printStackTrace()
            Toast.makeText(this, "Error general: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        try {
            super.onResume()
            Log.d("MainActivity", "onResume iniciado")
            // Actualizar la lista cuando volvamos a la actividad
            adapter.updateList(dbHelper.getAllSeries())
            Log.d("MainActivity", "Lista actualizada en onResume")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onResume: ${e.message}")
            Toast.makeText(this, "Error al actualizar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}