package com.ardine.fruturity.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ardine.fruturity.R
import com.ardine.fruturity.ui.theme.FruturityTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(R.string.menu_profile))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    FruturityTheme {
        ProfileScreen()
    }
}