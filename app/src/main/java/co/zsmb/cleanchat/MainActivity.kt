package co.zsmb.cleanchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.zsmb.cleanchat.ui.Contacts
import co.zsmb.cleanchat.ui.Messages
import co.zsmb.cleanchat.ui.theme.CleanChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanChatTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "contacts") {
                    composable("contacts") {
                        Contacts(navController)
                    }
                    composable("messages") {
                        Messages()
                    }
                }
            }
        }
    }
}
