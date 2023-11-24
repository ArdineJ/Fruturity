package com.ardine.fruturity.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ardine.fruturity.R
import com.ardine.fruturity.di.Injection
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.ui.component.SearchBar
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
    val homeState by viewModel.homeState

    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resultState ->
        when(resultState){
            is ResultState.Loading -> {
                viewModel.getAllFruits()
            }
            is ResultState.Success -> {
                HomeContent(
                    order = resultState.data,
                    navigateToDetail = navigateToDetail,
                    query = homeState.query,
                    onQueryChange = viewModel::onQueryChange,
                    modifier = modifier,
                )
            }
            is ResultState.Error -> {
                Toast.makeText(LocalContext.current,R.string.empty_msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    order: List<FruitOrder>,
    navigateToDetail: (Long) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(R.drawable.fruturitylogo),
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = modifier.height(120.dp)
            )
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                modifier = modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        if (order.isEmpty()){
            Text(
                modifier = modifier
                    .align(Alignment.CenterHorizontally) ,
                text = stringResource(R.string.empty_msg)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("FruitList")
        ){

            items(order, key = { it.fruits.id}) { data ->
                Items(
                    image = data.fruits.image,
                    title = data.fruits.title,
                    price = data.fruits.price,
                    modifier = modifier
                        .clickable { navigateToDetail(data.fruits.id) }
                        .animateItemPlacement(tween(durationMillis = 600))
                )
            }
        }
    }
}