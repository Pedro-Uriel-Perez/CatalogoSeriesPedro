# Catálogo de Series

Aplicación Android para gestionar un catálogo personal de series de TV, desarrollada con Kotlin y SQLite.

## Descripción

Esta aplicación permite al usuario crear, visualizar, actualizar y eliminar información sobre sus series favoritas, incluyendo detalles como título, género, plataforma, año de estreno, temporadas, calificación y sinopsis.

## Capturas de pantalla

<div style="display: flex; justify-content: space-between;">
  <img src="screenshots/main_screen.jpg" width="32%" alt="Lista de Series"/>
  <img src="screenshots/detail_view.jpg" width="32%" alt="Detalle de Serie"/>
  <img src="screenshots/form_view.jpg" width="32%" alt="Formulario"/>
</div>

## Estructura del código

### Clases principales
- `MainActivity`: Pantalla principal con lista de series y buscador
- `SerieDetailActivity`: Muestra los detalles completos de una serie
- `SerieForm`: Formulario para crear o editar series
- `SeriesDbHelper`: Gestiona operaciones CRUD con la base de datos SQLite
- `Serie`: Modelo de datos para representar una serie
- `SeriesAdapter`: Para el RecyclerView
- `SerieViewHolder`: ViewHolder para los elementos de la lista

### Base de datos
La aplicación utiliza SQLite para el almacenamiento persistente de datos, con una tabla `series` que incluye los siguientes campos:
- id (INTEGER PRIMARY KEY)
- titulo (TEXT)
- genero (TEXT)
- plataforma (TEXT)
- anio_estreno (INTEGER)
- temporadas (INTEGER)
- calificacion (REAL)
- sinopsis (TEXT)
- imagen_url (TEXT)

## Características
- CRUD completo (Crear, Leer, Actualizar, Eliminar)
- Búsqueda en tiempo real por título, género o plataforma
- Interfaz basada en Material Design
- Estructura Maestro-Detalle
- Soporte para imágenes mediante URLs

## Instalación
1. Descarga el archivo APK desde la carpeta [app-debug.apk](app-debug.apk)
2. Instala la aplicación en tu dispositivo Android
3. Comienza a gestionar tu catálogo de series

## Tecnologías utilizadas
- Kotlin
- SQLite
- RecyclerView
- Material Design
- Glide (para cargar imágenes)
