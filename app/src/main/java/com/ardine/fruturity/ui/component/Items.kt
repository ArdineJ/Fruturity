package com.ardine.fruturity.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ardine.fruturity.R
import com.ardine.fruturity.ui.theme.FruturityTheme

@Composable
fun Items (
    image: Int,
    title: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Image of $title",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(180.dp)
                .clip(
                    RoundedCornerShape(8.dp))
        )

        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
            ),
        )

        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = stringResource(R.string.price, price),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
            ),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ItemsPreview(){
    FruturityTheme {
        Items(image = R.drawable.banana, title = "Banana" , price = 10000)
    }
}