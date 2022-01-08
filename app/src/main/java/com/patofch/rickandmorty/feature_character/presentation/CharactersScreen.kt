package com.patofch.rickandmorty.feature_character.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.patofch.rickandmorty.feature_character.domain.model.Character
import com.patofch.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharactersScreen() {
    val viewModel = viewModel<CharacterViewModel>()
    val characters = viewModel.characters.value
    var selectedCharacter by remember {
        mutableStateOf<Character?>(null)
    }
    if (selectedCharacter == null) {
        CharacterList(
            characters = characters,
            onValueChange = { searchTerms ->
                if (searchTerms.isNotEmpty()) {
                    viewModel.getCharactersFilterBy(name = searchTerms)
                } else {
                    viewModel.getCharacters()
                }
            },
            onLoadMore = {
                viewModel.getMoreCharacters()
            },
            onClick = {
                selectedCharacter = it
            }
        )
    } else {
        Column(Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.BottomCenter) {
                BackgroundImage(url = selectedCharacter!!.image) {
                    selectedCharacter = null
                }
                Text(
                    text = selectedCharacter!!.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray.copy(alpha = .8f))
                        .padding(10.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BackgroundImage(url: String, onItemClick: () -> Unit) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = "picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .clickable { onItemClick() }
    )
}

@Composable
private fun CharacterList(
    characters: List<Character>,
    onClick: (Character) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    var searchTerms by remember {
        mutableStateOf("")
    }
    Scaffold(
        content = {
            val lastIndex = characters.lastIndex
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(characters) {index, character ->
                    if (index == lastIndex && searchTerms.isEmpty() && lastIndex > 0) {
                        onLoadMore()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { onClick(character) },
                        shape = RoundedCornerShape(15.dp),
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = character.image,
                                    builder = {
                                        transformations(CircleCropTransformation())
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                            Text(
                                text = character.name,
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .weight(1f),
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
        },
        topBar = {
            TextField(
                value = searchTerms,
                onValueChange = {
                    searchTerms = it
                    onValueChange(searchTerms)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        Surface(color = MaterialTheme.colors.background) {
            CharacterList(
                characters = listOf(
                    Character(name = "name", image = "url"),
                    Character(name = "name2", image = "url2")
                )
            )
        }
    }
}