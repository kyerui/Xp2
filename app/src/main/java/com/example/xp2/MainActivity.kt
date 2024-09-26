package com.example.xp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xp2.ui.theme.Xp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaJogadores()
        }
    }
}

@Composable
fun ListaJogadores() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(6) { index ->
            JogadorItem(jogadorNumero = index + 1)
            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp)
        }
    }
}

@Composable
fun JogadorItem(jogadorNumero: Int) {
    var nome by remember { mutableStateOf("") }
    var level by remember { mutableIntStateOf(1) }
    var bonus by remember { mutableIntStateOf(0) }
    var modificadores by remember { mutableIntStateOf(0) }
    var poder by remember { mutableIntStateOf(0) }

    val poderTotal = level + bonus + modificadores + poder

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Jogador $jogadorNumero", style = MaterialTheme.typography.titleLarge)

        // Inserir o nome do jogador
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Jogador") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Controles do jogador
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LevelControl(level) { newLevel -> level = newLevel }
            Text(text = "Poder: $poderTotal")
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Controle do poder
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { poder = (poder - 1).coerceAtLeast(-100) }) {
                Text(text = "-")
            }
            Text(text = "Poder: $poder")
            Button(onClick = { poder += 1 }) {
                Text(text = "+")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        BônusControl(bonus) { newBonus -> bonus = newBonus }

        Spacer(modifier = Modifier.height(4.dp)) // Reduzido

        ModificadorControl(modificadores) { newModificador -> modificadores = newModificador }
    }
}

@Composable
fun LevelControl(level: Int, onLevelChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { onLevelChange((level - 1).coerceAtLeast(1)) }) {
            Text(text = "-")
        }
        Text(text = "Level: $level")
        Button(onClick = { onLevelChange((level + 1).coerceAtMost(10)) }) {
            Text(text = "+")
        }
    }
}

@Composable
fun BônusControl(bonus: Int, onBonusChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { onBonusChange((bonus - 1).coerceAtLeast(0)) }) {
            Text(text = "-")
        }
        Text(text = "Bônus: $bonus")
        Button(onClick = { onBonusChange((bonus + 1).coerceAtMost(99)) }) {
            Text(text = "+")
        }
    }
}

@Composable
fun ModificadorControl(modificadores: Int, onModificadorChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { onModificadorChange((modificadores - 1).coerceAtLeast(-10)) }) {
            Text(text = "-")
        }
        Text(text = "Modificadores: $modificadores")
        Button(onClick = { onModificadorChange((modificadores + 1).coerceAtMost(10)) }) {
            Text(text = "+")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaJogadores() {
    ListaJogadores()
}
