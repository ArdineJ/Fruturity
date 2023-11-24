package com.ardine.fruturity.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ardine.fruturity.R
import com.ardine.fruturity.ui.theme.FruturityTheme

@Composable
fun CartItem(
    fruitId: Long,
    image: Int,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Image of $title",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.price,
                    totalPrice
                ),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Counter(
            orderId = fruitId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(fruitId, count + 1) },
            onProductDecreased = { onProductCountChanged(fruitId, count - 1) },
            modifier = modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    FruturityTheme {
        CartItem(
            4,
            R.drawable.apple,
            "Apple",
            12000,
            1,
            onProductCountChanged = { _, _ -> },
        )
    }
}