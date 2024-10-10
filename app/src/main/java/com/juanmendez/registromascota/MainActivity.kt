package com.juanmendez.registromascota

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.juanmendez.registromascota.ui.theme.RegistroMascotaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroMascotaTheme {

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
fun FormularioDeMascotas(
    nombre: String,
    especie: String,
    raza: String,
    edad: String,
    peso: String,
    foto: String,
    onNombreChange: (String)-> Unit,
    onEspecieChange: (String)-> Unit,
    onRazaChange: (String)-> Unit,
    onEdadChange: (String)-> Unit,
    onPesoChange: (String)-> Unit,
    onFotoChange: (String)-> Unit,
    onRegistrar: (String, String, String, Int, Double, String) -> Unit
) {
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
            onValueChange = onNombreChange,
            label = { Text("Nombre")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la especie
        TextField(
            value = especie,
            onValueChange = onEspecieChange,
            label = { Text("Especie")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la raza
        TextField(
            value = raza,
            onValueChange = onRazaChange,
            label = { Text("Raza")},
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la edad (convertimos el valor en entero al agregar)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = edad,
            onValueChange = onEdadChange,
            label = { Text("Edad (años)")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para el peso (convertimos el valor en double al agregar)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = peso,
            onValueChange = onPesoChange,
            label = { Text("Edad")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = foto,
            onValueChange = onFotoChange,
            label = { Text("Foto de la mascota")},
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para registrar la mascota
        Button(
            onClick = {
                val edadInt = edad.toIntOrNull() ?: 0
                val pesoDouble = peso.toDoubleOrNull() ?: 0.0
                onRegistrar(nombre, especie, raza, edadInt, pesoDouble, foto)  // Pasar los valores a la función callback
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(mascotas) { mascota ->
            MascotaItem(mascota, onEliminarMascota)
        }
    }
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
            .background(Color.LightGray)
            .padding(8.dp)
    ){
        // Nombre de la mascota
        Text(text = mascota.nombre, style = MaterialTheme.typography.titleLarge)

        //Informacion en fila de las mascotas
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            item { Text("Especie: ${mascota.especie}") }
            item { Text("Raza: ${mascota.raza}") }
            item { Text("Edad: ${mascota.edad} años") }
            item { Text("Peso: ${mascota.peso} kg") }
           // item { ImageFromUrl(mascota.foto) }
        }

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


@Preview(showBackground = true)
@Composable
fun VistaPreviewAplicacionDeContactos() {
    RegistroMascotaTheme {
        val mascotas = listOf(
            Mascota("Jordan", "Perro", "Husky Siberiano ", "5", "35", "https://ejemplo.com/luna.jpg"),
            Mascota("Tina", "Gato", "Siamés", "5", "8", "https://ejemplo.com/max.jpg")
        )
        Column{
            FormularioDeMascotas(
                nombre = "",
                especie = "",
                raza = "",
                edad = "",
                peso = "",
                foto = "",
                onNombreChange = {},
                onEspecieChange = {},
                onRazaChange = {},
                onEdadChange = {},
                onPesoChange = {},
                onFotoChange = {},
                onRegistrar = { _, _, _, _, _, _ -> }
            )
            Spacer(modifier = Modifier.height(16.dp))

            ListaDeMascotas(mascotas = mascotas, onEliminarMascota = {})
        }
    }
}
