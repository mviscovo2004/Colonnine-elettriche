package it.univaq.colonnine_elettriche.ui.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionGate(
    permissions: List<String>,
    content: @Composable () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)

    if (permissionsState.allPermissionsGranted) {
        // I permessi sono concessi, mostriamo la mappa
        content()
    } else {
        // I permessi NON sono concessi: mostriamo l'interfaccia di richiesta
        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (permissionsState.shouldShowRationale) {
                        "L'app ha bisogno della posizione per mostrare le colonnine vicine a te."
                    } else {
                        "Per visualizzare la mappa è necessario concedere i permessi di posizione."
                    },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                    Text("Concedi Permessi")
                }
            }
        }

        // Se è la prima volta (non dobbiamo mostrare rationale), chiediamo i permessi automaticamente
        LaunchedEffect(permissionsState) {
            if (!permissionsState.allPermissionsGranted && !permissionsState.shouldShowRationale) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
    }
}
