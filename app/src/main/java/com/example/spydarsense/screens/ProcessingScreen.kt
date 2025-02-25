package com.example.spydarsense.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spydarsense.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProcessingScreen(navController: NavController, deviceName: String) {
    val scope = rememberCoroutineScope()
    val messages = listOf(
        "Processing...",
        "Generating Parameters...",
        "Collecting CSI",
        "Model Working..."
    )
    var currentMessage by remember { mutableStateOf(messages[0]) }

    // Rotation animation
    val rotation = rememberInfiniteTransition()
    val rotate by rotation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    LaunchedEffect(Unit) {
        scope.launch {
            messages.forEach {
                currentMessage = it
                delay(2000L) // 2-second delay for each message
            }
            navController.navigate("result/$deviceName")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0E21)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cobweb),
            contentDescription = "Loading Icon",
            modifier = Modifier
                .size(50.dp)
                .rotate(rotate)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = currentMessage, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}
