package com.example.gatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gatos.network.KtorHttpClient
import com.example.gatos.network.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatsViewModel: ViewModel() {
    private val _cats = MutableStateFlow<Result<List<Cat>>>(Result.success(emptyList()))
    val cats: StateFlow<Result<List<Cat>>> = _cats.asStateFlow()

    init {
        viewModelScope.launch {
            _cats.value = KtorHttpClient.getCats()
        }
    }
}