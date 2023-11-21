package com.ardine.fruturity.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardine.fruturity.data.Repository
import com.ardine.fruturity.ui.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _resultState: MutableStateFlow<ResultState<CartState>> = MutableStateFlow(ResultState.Loading)
    val resultState: StateFlow<ResultState<CartState>>
        get() = _resultState

    fun getAddedFruitOrder() {
        viewModelScope.launch {
            _resultState.value = ResultState.Loading
            repository.getAddedOrders()
                .collect { orders ->
                    val totalPrice =
                        orders.sumOf { it.fruits.price * it.count }
                    _resultState.value = ResultState.Success(
                        CartState(orders, totalPrice)
                    )
                }
        }
    }

    fun updateFruitOrder(fruitId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateFruitOrder(fruitId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedFruitOrder()
                    }
                }
        }
    }
}