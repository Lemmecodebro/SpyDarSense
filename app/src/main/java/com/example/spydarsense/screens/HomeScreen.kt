package com.example.spydarsense.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.spydarsense.R
import com.example.spydarsense.ui.theme.SpyDarSenseTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    var selectedDevice by remember { mutableStateOf<String?>(null) }

    SpyDarSenseTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF02042C))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.orangeweb),
                        contentDescription = "SpyDar Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("SpyDar", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Sense", color = Color(0xFF8D8E93), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))

                if (selectedDevice != null) {
                    Text(
                        "Selected Device: $selectedDevice",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    if (selectedDevice != null) Color(0xFF7B61FF) else Color(0xFF7B61FF).copy(alpha = 0.2f),
                                    if (selectedDevice != null) Color(0xFF9D50FF) else Color(0xFF9D50FF).copy(alpha = 0.2f)
                                )
                            ),
                            shape = MaterialTheme.shapes.large
                        )
                        .clickable(enabled = selectedDevice != null) {
                            selectedDevice?.let {
                                navController.navigate("processing/$it")
                            }
                        }
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.spider_1328758),
                            contentDescription = "Detect Icon",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Detect",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF6A42FF), Color(0xFF5A32D1))
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Wi-Fi Devices", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                "View",
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Color.White, shape = MaterialTheme.shapes.medium) // Add shape here
                                    .padding(horizontal = 12.dp, vertical = 6.dp) // Adjust padding for better shape
                                    .clickable { showSheet = true }
                            )

                        }
                        Spacer(modifier = Modifier.height(0.5.dp))
                        Text("5 Active Devices", color = Color.Green, fontSize = 12.sp)
                    }
                }
            }

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            "Select a Device",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val devices = listOf(
                            "HUAWEI-Cnm9" to "2.4 GHz",
                            "Cammy-hd6" to "5 GHz",
                            "Samsung-f32f" to "2.4 GHz",
                            "tp-Link-w87n" to "5 GHz",
                            "ASUS-w78" to "2.4 GHz",
                            "Netgear-n1" to "5 GHz",
                            "Tenda-TX3" to "2.4 GHz",
                            "Xiaomi-Router4" to "5 GHz"
                        )

                        devices.forEach { (device, frequency) ->
                            WifiDeviceItem(device, frequency, selectedDevice == device) {
                                selectedDevice = device
                                showSheet = false
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WifiDeviceItem(name: String, frequency: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) Color(0xFF7B61FF) else Color.White)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "WiFi Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                name,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                frequency,
                color = if (isSelected) Color.White.copy(alpha = 0.7f) else Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
