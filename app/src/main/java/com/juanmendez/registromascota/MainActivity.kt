package com.juanmendez.registromascota

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.juanmendez.registromascota.ui.theme.Purple80
import com.juanmendez.registromascota.ui.theme.RegistroMascotaTheme


class MainActivity : ComponentActivity() {
    private lateinit var mascotaViewModel: MascotaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mascotaViewModel = ViewModelProvider(this).get(MascotaViewModel::class.java)
        enableEdgeToEdge()
        setContent {
            RegistroMascotaTheme {
                val listaMascotas by mascotaViewModel.listaMascotas.observeAsState(emptyList())
                Column {
                    FormularioDeMascotas(onRegistrar = { mascota ->
                        mascotaViewModel.agregarMascota(mascota)
                    })
                    ListaDeMascotas(mascotas = listaMascotas, onEliminarMascota = { mascota ->
                        mascotaViewModel.eliminarMascota(mascota)
                    })
                }
            }
        }
    }
}

data class Mascota(
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: String,
    val peso: String,
    val foto: String

)

/*Creacion del formulario*/
@Composable
fun FormularioDeMascotas(onRegistrar: (Mascota) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para el nombre de la mascota
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre")},
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && nombre.isBlank()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la especie
        TextField(
            value = especie,
            onValueChange = { especie = it },
            label = { Text("Especie")},
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && especie.isBlank()

        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la raza
        TextField(
            value = raza,
            onValueChange = { raza = it },
            label = { Text("Raza")},
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && raza.isBlank()
        )

        // Campo para la edad (convertimos el valor en entero al agregar)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad (años)")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && (edad.isBlank() || edad.toIntOrNull() == null)
        )

        // Campo para el peso (convertimos el valor en double al agregar)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && (peso.isBlank() || peso.toDoubleOrNull() == null)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = foto,
            onValueChange = { foto = it },
            label = { Text("Foto de la mascota")},
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && foto.isBlank()
        )
        // Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
        // Botón para registrar la mascota
        Button(
            onClick = {
                val nuevaMascota = Mascota(
                    nombre, especie, raza,
                    edad.toInt().toString(), peso.toDouble().toString(), foto
                )
                onRegistrar(nuevaMascota)  // Pasar los valores a la función callback
                //limpiamos el formulario para una nueva carga
                nombre = ""
                especie = ""
                raza = ""
                edad = ""
                peso = ""
                foto = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Mascota")
        }

    }
}
@Composable
fun ListaDeMascotas(
    mascotas: List<Mascota>, //Lista de las mascotas
    onEliminarMascota: (Mascota) ->Unit //Callback para eliminar mascotas
){
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(mascotas) { mascota ->
            MascotaItem(mascota, onEliminarMascota)
        }
    }
}

@Composable
fun ImagenMascota(foto: String) {
    AsyncImage(
        model = foto,
        contentDescription = "Imagen de la mascota",
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun MascotaItem(
    mascota: Mascota,
    onEliminarMascota: (Mascota) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.DarkGray)
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .padding(8.dp)
    ){
        // Nombre de la mascota
        Text(text = mascota.nombre, style = MaterialTheme.typography.titleLarge)

        //Informacion en fila de las mascotas
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            item { Text("Especie: ${mascota.especie}")}
            item { Text("Raza: ${mascota.raza}")}
        }
        // Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            item { Text("Edad: ${mascota.edad} años") }
            item { Text("Peso: ${mascota.peso} kg") }
        }
        //Muestra de la imagen de la mascota
        AsyncImage(
            model = mascota.foto,
            contentDescription = "Foto de ${mascota.nombre}",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados para la imagen

            // placeholder = painterResource(id = R.drawable.placeholder), // Imagen de carga
            //error = painterResource(id = R.drawable.error)
        )

        //Boton para eliminar la parte inferior del item
        Button(
            onClick = {onEliminarMascota(mascota)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar datos del item")
        }
    }

}

/*@Composable
fun ImageFromUrl(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                // Ajustes opcionales para la vista de imagen, como escalado
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            // Aquí Picasso carga la imagen
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)  // Imagen mientras carga
                .error(R.drawable.error)  // Imagen si falla la carga
                .into(imageView)
        }
    )
}
*/
class MascotaViewModel : ViewModel() {
    private val _listaMascotas = MutableLiveData<List<Mascota>>(emptyList())
    val listaMascotas: LiveData<List<Mascota>> get() = _listaMascotas

    fun agregarMascota(mascota: Mascota) {
        _listaMascotas.value = _listaMascotas.value?.plus(mascota)
    }

    fun eliminarMascota(mascota: Mascota) {
        _listaMascotas.value = _listaMascotas.value?.filterNot { it == mascota }
    }
}

@Preview(showBackground = false)
@Composable
fun VistaPreviewAplicacionDeContactos() {
    RegistroMascotaTheme {
        val mascotas = listOf(
            Mascota(
                "Jordan", "Perro", "Husky Siberiano", "5", "35",
                "https://grupoasis.com.es/desarrollo/clientes/virbac/breedselector/es/wp-content/uploads/2013/04/husky.jpg"
            ),
            Mascota(
                "Tina", "Gato", "Siamés", "5", "8",
                "https://png.pngtree.com/element_our/png/20181009/thai-cat-cream-tabby-sitting-png_131622.jpg"
            ),
            Mascota(
                "Tina2.0", "Gato", "Siamés", "5", "8",
                "https://png.pngtree.com/element_our/png/20181009/thai-cat-cream-tabby-sitting-png_131622.jpg"
            )
        )

        Column {
            FormularioDeMascotas(onRegistrar = { /* no hacer nada en la vista previa */ })
            ListaDeMascotas(mascotas = mascotas, onEliminarMascota = { /* no hacer nada en la vista previa */ })
        }
    }
}


