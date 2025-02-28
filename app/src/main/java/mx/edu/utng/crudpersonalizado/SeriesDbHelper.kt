package mx.edu.utng.crudpersonalizado

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SeriesDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SeriesCatalogo.db"

        const val TABLE_SERIES = "series"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_GENERO = "genero"
        const val COLUMN_PLATAFORMA = "plataforma"
        const val COLUMN_ANIO = "anio_estreno"
        const val COLUMN_TEMPORADAS = "temporadas"
        const val COLUMN_CALIFICACION = "calificacion"
        const val COLUMN_ESTADO = "estado"
        const val COLUMN_SINOPSIS = "sinopsis"
        const val COLUMN_IMAGEN_URL = "imagen_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SERIES_TABLE = ("CREATE TABLE $TABLE_SERIES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITULO TEXT," +
                "$COLUMN_GENERO TEXT," +
                "$COLUMN_PLATAFORMA TEXT," +
                "$COLUMN_ANIO INTEGER," +
                "$COLUMN_TEMPORADAS INTEGER," +
                "$COLUMN_CALIFICACION REAL," +
                "$COLUMN_ESTADO TEXT," +
                "$COLUMN_SINOPSIS TEXT," +
                "$COLUMN_IMAGEN_URL TEXT)")
        db.execSQL(CREATE_SERIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERIES")
        onCreate(db)
    }

    // Insertar una nueva serie
    fun insertSerie(serie: Serie): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, serie.titulo)
            put(COLUMN_GENERO, serie.genero)
            put(COLUMN_PLATAFORMA, serie.plataforma)
            put(COLUMN_ANIO, serie.anioEstreno)
            put(COLUMN_TEMPORADAS, serie.temporadas)
            put(COLUMN_CALIFICACION, serie.calificacion)
            put(COLUMN_ESTADO, serie.estado)
            put(COLUMN_SINOPSIS, serie.sinopsis)
            put(COLUMN_IMAGEN_URL, serie.imagenUrl)
        }
        val id = db.insert(TABLE_SERIES, null, values)
        db.close()
        return id
    }

    // Obtener todas las series
    fun getAllSeries(): List<Serie> {
        val seriesList = mutableListOf<Serie>()
        val selectQuery = "SELECT * FROM $TABLE_SERIES ORDER BY $COLUMN_TITULO ASC"
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val serie = Serie(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO)),
                    plataforma = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATAFORMA)),
                    anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIO)),
                    temporadas = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEMPORADAS)),
                    calificacion = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_CALIFICACION)),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO)),
                    sinopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINOPSIS)),
                    imagenUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_URL))
                )
                seriesList.add(serie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return seriesList
    }

    // Buscar series por texto (en título, género o plataforma)
    fun searchSeries(searchText: String): List<Serie> {
        val seriesList = mutableListOf<Serie>()
        val searchPattern = "%$searchText%"
        val selectQuery = """
            SELECT * FROM $TABLE_SERIES 
            WHERE $COLUMN_TITULO LIKE ? 
            OR $COLUMN_GENERO LIKE ? 
            OR $COLUMN_PLATAFORMA LIKE ?
            ORDER BY $COLUMN_TITULO ASC
        """.trimIndent()

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(searchPattern, searchPattern, searchPattern))

        if (cursor.moveToFirst()) {
            do {
                val serie = Serie(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO)),
                    plataforma = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATAFORMA)),
                    anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIO)),
                    temporadas = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEMPORADAS)),
                    calificacion = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_CALIFICACION)),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO)),
                    sinopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINOPSIS)),
                    imagenUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_URL))
                )
                seriesList.add(serie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return seriesList
    }

    // Obtener una serie por ID
    fun getSerieById(id: Int): Serie? {
        val db = this.readableDatabase
        var serie: Serie? = null

        val cursor = db.query(
            TABLE_SERIES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            serie = Serie(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO)),
                plataforma = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATAFORMA)),
                anioEstreno = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIO)),
                temporadas = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEMPORADAS)),
                calificacion = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_CALIFICACION)),
                estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO)),
                sinopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINOPSIS)),
                imagenUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_URL))
            )
        }

        cursor.close()
        db.close()
        return serie
    }

    // Actualizar una serie existente
    fun updateSerie(serie: Serie): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, serie.titulo)
            put(COLUMN_GENERO, serie.genero)
            put(COLUMN_PLATAFORMA, serie.plataforma)
            put(COLUMN_ANIO, serie.anioEstreno)
            put(COLUMN_TEMPORADAS, serie.temporadas)
            put(COLUMN_CALIFICACION, serie.calificacion)
            put(COLUMN_ESTADO, serie.estado)
            put(COLUMN_SINOPSIS, serie.sinopsis)
            put(COLUMN_IMAGEN_URL, serie.imagenUrl)
        }

        val rowsAffected = db.update(
            TABLE_SERIES,
            values,
            "$COLUMN_ID = ?",
            arrayOf(serie.id.toString())
        )

        db.close()
        return rowsAffected
    }

    // Eliminar una serie
    fun deleteSerie(id: Int): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(
            TABLE_SERIES,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )

        db.close()
        return rowsAffected
    }
}