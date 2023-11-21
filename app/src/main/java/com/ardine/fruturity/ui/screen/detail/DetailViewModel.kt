package com.ardine.fruturity.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardine.fruturity.data.Repository
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.model.Fruits
import com.ardine.fruturity.ui.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _resultState: MutableStateFlow<ResultState<FruitOrder>> =
        MutableStateFlow(ResultState.Loading)
    val resultState: StateFlow<ResultState<FruitOrder>>
        get() = _resultState

    fun getFruitOrderById(fruitId: Long) {
        viewModelScope.launch {
            _resultState.value = ResultState.Loading
            _resultState.value = ResultState.Success(repository.getFruitOrderById(fruitId))
        }
    }

    fun addToCart(fruit: Fruits , count: Int) {
        viewModelScope.launch {
            repository.updateFruitOrder(fruit.id, count)
        }
    }
}