package com.ardine.fruturity.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ardine.fruturity.R
import com.ardine.fruturity.di.Injection
import com.ardine.fruturity.ui.ResultState
import com.ardine.fruturity.ui.ViewModelFactory
import com.ardine.fruturity.ui.component.OrderButton
import com.ardine.fruturity.ui.component.Counter
import com.ardine.fruturity.ui.theme.FruturityTheme

@Composable
fun DetailScreen(
    fruitId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resultState ->
        when (resultState) {
            is ResultState.Loading -> {
                viewModel.getFruitOrderById(fruitId)
            }
            is ResultState.Success -> {
                val data = resultState.data
                DetailContent(
                    data.fruits.image,
                    data.fruits.title,
                    data.fruits.description,
                    data.fruits.price,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.fruits, count)
                        navigateToCart()
                    }
                )
            }
            is ResultState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    description: String,
    price: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPrice by rememberSaveable { mutableIntStateOf(0) }
    var orderCount by rememberSaveable { mutableIntStateOf(count) }

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Row(
                modifier = modifier
            ){
                Column(
                    modifier = modifier
                        .padding(16.dp)
                        .weight(2f)
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 30.sp
                        ),
                    )

                    Spacer(modifier = modifier.height(16.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp
                        ),
                        textAlign = TextAlign.Justify,
                    )
                }
                Column(
                    modifier = modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ){
                    Text(
                        text = stringResource(R.string.price, price),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                    )
                    Text(
                        text = "*KG",
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Spacer(modifier = modifier.height(16.dp))

                    Counter(
                        1,
                        orderCount,
                        onProductIncreased = { orderCount++ },
                        onProductDecreased = { if (orderCount > 0) orderCount-- },
                    )
                    totalPrice = price * orderCount

                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp))
        OrderButton(
            text = stringResource(R.string.add_to_cart, totalPrice),
            enabled = orderCount > 0,
            modifier = modifier.padding(16.dp),
            onClick = {
                onAddToCart(orderCount)
            },
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    FruturityTheme {
        DetailContent(
            R.drawable.apple,
            "Apple",
            "An apple keeps the doctor away",
            12000,
            1,
            onBackClick = {},
            onAddToCart = {},
        )
    }
}