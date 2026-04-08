package com.example.lista_de_tareas.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lista_de_tareas.dataclasses.Tareas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {

    private val _tareas = MutableStateFlow<List<Tareas>>(emptyList())
    val tareas = _tareas.asStateFlow()



    private var siguienteId = 0

    fun agregar(nuevaTarea: String){
        if(nuevaTarea.isBlank()) return

        val tareaNueva= Tareas(
            id = siguienteId++,
            tareas = nuevaTarea
        )
        _tareas.value += tareaNueva
    }

    fun cambiarEstado(id: Int, completada: Boolean){
        _tareas.value = _tareas.value.map {
            if (it.id == id) it.copy(completada=completada)
            else it
        }
    }
    fun eliminar(){
            _tareas.value = _tareas.value.filter { !it.completada }
    }
}