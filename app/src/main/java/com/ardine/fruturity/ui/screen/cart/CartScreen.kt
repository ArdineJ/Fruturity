package com.ardine.fruturity.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ardine.fruturity.R
import com.ardine.fruturity.di.Injection
import com.ardine.fruturity.ui.ResultState
import com.ardine.fruturity.ui.ViewModelFactory
import com.ardine.fruturity.ui.component.CartItem
import com.ardine.fruturity.ui.component.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
//    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resultState ->
        when (resultState){
            is ResultState.Loading -> {
                viewModel.getAddedFruitOrder()
            }
            is ResultState.Success -> {
                CartContent(
                    resultState.data,
                    onProductCountChanged = { rewardId, count ->
                        viewModel.updateFruitOrder(rewardId, count)
                    },
//                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is ResultState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
//    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
//    val shareMessage = stringResource(
//        state.fruitOrder.count(),
//        state.price
//    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.fruitOrder, key = { it.fruits.id }) { item ->
                CartItem(
                    rewardId = item.fruits.id,
                    image = item.fruits.image,
                    title = item.fruits.title,
                    totalPrice = item.fruits.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.price),
            enabled = state.fruitOrder.isNotEmpty(),
            onClick = {
//                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}