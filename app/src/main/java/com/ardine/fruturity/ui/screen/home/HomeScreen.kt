package com.ardine.fruturity.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ardine.fruturity.di.Injection
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.ui.ResultState
import com.ardine.fruturity.ui.ViewModelFactory
import com.ardine.fruturity.ui.component.Items

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resultState ->
        when(resultState){
            is ResultState.Loading -> {
                viewModel.getAllFruits()
            }
            is ResultState.Success -> {
                HomeContent(
                    order = resultState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is ResultState.Error -> {

            }
        }
    }
}
@Composable
fun HomeContent(
    order: List<FruitOrder>,
    modifier: Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = modifier.testTag("List")
    ){
        items(order) { data ->
            Items(
                image = data.fruits.image,
                title = data.fruits.title,
                price = data.fruits.price,
                modifier = modifier.clickable {
                    navigateToDetail(data.fruits.id)
                }
            )
        }
    }
}