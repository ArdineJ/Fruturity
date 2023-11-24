package com.ardine.fruturity.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardine.fruturity.data.Repository
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.ui.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _resultState: MutableStateFlow<ResultState<List<FruitOrder>>> = MutableStateFlow(ResultState.Loading)
    val resultState: StateFlow<ResultState<List<FruitOrder>>>
        get() = _resultState

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    fun getAllFruits() {
        viewModelScope.launch {
            repository.getAllFruits()
                .catch {
                    _resultState.value = ResultState.Error(it.message.toString())
                }
                .collect { order ->
                    _resultState.value = ResultState.Success(order)
                }
        }
    }

    private fun searchFruits(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchFruits(query)
                .catch { _resultState.value = ResultState.Error(it.message.toString()) }
                .collect { _resultState.value = ResultState.Success(it) }
        }
    }

    fun onQueryChange(query: String){
        _homeState.value = _homeState.value.copy(query = query)
        searchFruits(query)
    }
}