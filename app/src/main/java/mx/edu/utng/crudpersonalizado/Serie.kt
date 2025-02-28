package mx.edu.utng.crudpersonalizado

data class Serie(
    var id: Int = 0,
    var titulo: String = "",
    var genero: String = "",
    var plataforma: String = "",
    var anioEstreno: Int = 0,
    var temporadas: Int = 0,
    var calificacion: Float = 0.0f,
    var estado: String = "",
    var sinopsis: String = "",
    var imagenUrl: String = "" // URL o ruta local de la imagen
)
