package com.ardine.fruturity.ui.screen.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ardine.fruturity.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box (
            modifier = modifier.size(150.dp).clip(CircleShape)
        ){
            Image(
                painter = painterResource(R.drawable.me),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(180.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        Text(text = stringResource(R.string.me))

        Spacer(modifier = modifier.height(8.dp))

        Text(text = stringResource(R.string.email))

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialMediaButton(
                icon = R.drawable.discord,
                url =  "https://discordapp.com/users/3004",
                modifier = modifier
            )

            Spacer(modifier = modifier.width(16.dp))

            SocialMediaButton(
                icon = R.drawable.linkedin,
                url =  "https://www.linkedin.com/in/ardinejivensen/",
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SocialMediaButton(
    icon: Int,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as ComponentActivity

        Surface(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                },
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = modifier
                    .padding(12.dp)
                    .size(24.dp)
            )
        }
    }
