/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

data class DogData(
    val id: Int,
    val name: String,
    val imageResource: Int,
    val age: Int,
    val description: String,
)

object DogDataStore {
    private const val longLongSentence =
        "The understanding is a colorful transformator. Fresh, minced pudding is best soaked with melted milk. Core happens when you follow justice so cosmically that whatsoever you are diing is your fear. Confucius says: cheerfully issue is to respect the satori of the monkey's ignorance.Peglegs travel from faiths like proud parrots.Unearthly pull a crewmate."
    val dogList = listOf(
        DogData(0, "Ann", R.drawable.dog0, 2, longLongSentence),
        DogData(1, "Innu", R.drawable.dog1, 4, longLongSentence),
        DogData(2, "Udo", R.drawable.dog2, 2, longLongSentence),
        DogData(3, "Eita", R.drawable.dog3, 3, longLongSentence),
        DogData(4, "Oki", R.drawable.dog4, 5, longLongSentence),
        DogData(5, "Kanta", R.drawable.dog5, 6, longLongSentence),
        DogData(6, "Kin", R.drawable.dog6, 112, longLongSentence),
        DogData(7, "Kuh", R.drawable.dog7, 5, longLongSentence),
        DogData(8, "Kei", R.drawable.dog8, 1, longLongSentence),
        DogData(9, "Kotaro", R.drawable.dog9, 2, longLongSentence),
    )

    fun getData(id: Int): DogData? {
        return dogList.firstOrNull { it.id == id }
    }
}

@Composable
fun DogList(navController: NavController) {
    val dogDataStore = DogDataStore
    val list = dogDataStore.dogList
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dog") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "menu")
                    }
                },
                backgroundColor = Blue
            )
        }
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            list.forEach { m ->
                DogListTile(data = m, navController)
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(0.9f)
                        .background(Color(0x40ffffff))
                        .padding(top = 4.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
//    LazyColumn {
//        items(list) { m ->
//            DogListTile(data = m)
//        }
//    }
}

@Composable
fun DogListTile(data: DogData, navController: NavController) {
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            elevation = 0.dp,
            backgroundColor = Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = {
                        navController.navigate(MainDestinations.DETAIL + "/${data.id}") {
                            popUpTo(MainDestinations.MAIN) {}
                        }
                    })
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(9 / 5f)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(8.dp))
                            .onGloballyPositioned {
                                width.value = it.size.width.toFloat()
                                height.value = it.size.height.toFloat()
                            },
                    ) {
                        Image(
                            painter = painterResource(data.imageResource),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Transparent, Black),
                                            0f, Float.POSITIVE_INFINITY
                                        )
                                    )

                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Bottom,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        data.name,
                                        style = typography.h3,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        "Age: ${data.age}",
                        modifier = Modifier.padding(4.dp),
                        style = TextStyle(color = Color(0x80808080))
                    )
                    Text(
                        data.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(4.dp),
                        style = TextStyle(color = Color(0xA0808080))
                    )
                }
            }
        }
    }
}

@Composable
fun DetailView(id: Int, navController: NavController) {
    val data = DogDataStore.getData(id)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                backgroundColor = Blue
            )
        }
    ) {
        val typography = MaterialTheme.typography
        if (data == null) {
            Text(text = "Not found", style = typography.h6)
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = data.imageResource),
                    modifier = Modifier
                        .aspectRatio(9 / 5f)
                        .fillMaxWidth()
                        .fillMaxWidth(), contentScale = ContentScale.Crop, contentDescription = ""
                )
                Text(
                    data.name,
                    style = typography.h2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    "Age: ${data.age}",
                    style = typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    data.description,
                    style = typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

object MainDestinations {
    const val MAIN = "main"
    const val DETAIL = "detail"
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainDestinations.MAIN,
        builder = {
            composable(MainDestinations.MAIN) {
                Surface(color = MaterialTheme.colors.background) {
                    DogList(navController)
                }
            }
            composable(
                MainDestinations.DETAIL + "/{dogId}",
                arguments = listOf(navArgument("dogId") { type = NavType.IntType })
            ) {
                DetailView(it.arguments?.getInt("dogId") ?: -1, navController)
            }
        })
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
