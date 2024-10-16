# UAA-PAPDM-Grupo-3-TP-1
Se agrega el proyecto que tiene como finalidad gestionar el registro de mascotas para una veterinaria o refugio.

Breve descripción del trabajo practico: El repositorio en si cuenta con dos ramas, una rama main y otra rama feature/tareas para que cada compañero pueda realizar sus aportes.
El proyecto fue desarrollado en Kotlin utilizando Jetpack Compose. La aplicación esta diseñada para la gestión de mascotas en una veterinaria o refugio, permitiendo a los usuarios registrar mascota, ver la lista de mascotas registradas, eliminar mascotas y cargar imágenes de las mascotas desde una URL. Además, la aplicación soporta tema claro y oscuro, adaptándose automáticamente según la configuración del sistema.

Funcionalidades implementadas: 
  1-) Formulario de ingreso de mascotas: Un formulario que permite registrar los datos de una mascota. Los campos que incluye el formulario son nombre, especie, raza, edad, peso y url de la foto de la mascota, cada campo gestiona su propio estado utilizando rember y mutableStateOf permitiendo que los datos se mantengan y puedan modificarse dinámicamente. El componente utilizado es textField para los campos de texto y un botón “Registrar”

  2-) Lista de Mascotas Registradas: Una lista que muestra las mascotas registradas con sus respectivos datos, el componente utilizado LazyColumn para mostrar la lista de mascotas y LazyRow para mostrar los detalles de cada uno de ellos. Así como cada mascota tiene un botón Eliminar que permite eliminarla de la lista.

  3-) Carga de imágenes desde la URL: Se implementa la libreria io.coil-kt:coil-compose:2.2.2 para la visualizacion de las imagenes de las mascotas, como tambien algunos ajustes generales.

  4-) Soporte para tema claro y oscuro: La aplicación soporta tanto el tema claro como el oscuro, adaptándose automáticamente a la configuración del dispositivo, los componentes utilizados MaterialTheme en combinación con darkColorScheme y lightColorScheme para definir los colores del tema.

 5-) Vista previa: Durante el desarrollo, se implementaron vistas previas que muestran como se vera la interfaz en ambos temas (claro y oscuro) el componente @Preview se utiliza para generar una vista previa de los composables, tanto en modo claro como en modo oscuro.
