package dgsw.stac.knowledgender.ui

import dgsw.stac.knowledgender.navigation.NavigationDepth1
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import dagger.hilt.android.AndroidEntryPoint
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard



private val showPermissionReDialog = mutableStateOf(false)
private val showPermissionDialog = mutableStateOf(true)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    권한 요청 결과 처리
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // 권한 부여되면 실행
                showPermissionDialog.value = false
            } else {
                showPermissionReDialog.value = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KnowledgenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavigationDepth1()
                    if (showPermissionDialog.value) {
                        onRequestLocationPermission()
                    }
                    if (showPermissionReDialog.value) {
                        showPermissionRationaleDialog()
                    }
                }
            }
        }
    }

    //    위치 권한을 요청 메서드
    @Composable
    fun onRequestLocationPermission() {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(LightBlack),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.height(40.dp).width(200.dp),
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PermissionChecker.PERMISSION_GRANTED
                        ) {
                            // 권한 이미 부여 됨 !
                            showPermissionDialog.value = false
                        } else {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                showPermissionDialog.value = true
                            } else {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        }
                    }
                ) {
                    Text(
                        "위치 권한 요청",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }

    // 권한 거부되었을 때 권한 요청
    @Composable
    private fun showPermissionRationaleDialog() {
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    "알림", color = BaseBlack, style = TextStyle(
                        fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 16.sp
                    )
                )
            },
            text = {
                Text(
                    "위치 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.",
                    color = BaseBlack,
                    style = TextStyle(
                        fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 16.sp
                    )
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:${context.packageName}")
                            startActivity(intent)
                        }
                    ) {
                        Text(
                            "설정", color = BaseBlack, style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        onClick = {
                            finish()
                        }
                    ) {
                        Text(
                            "나가기", color = BaseBlack, style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        )
    }
}