package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.model.Airport
import com.example.flightsearch.data.model.Favorite
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FavoriteList(
    favorites: List<Favorite>,
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ){
        items(favorites){favorite ->
            FlightCard(
                departureAirport = favorite.departureAirport,
                destinationAirport= favorite.destinationAirport,
                onClick = {
                    viewModel.addOrRemoveFavorite(favorite = favorite)
                },
                isPressed = viewModel.isFavorite(
                    departureCode = favorite.departureAirport.iata_code,
                    destinationCode = favorite.destinationAirport.iata_code
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}
@Composable
fun RouteList(
    destinationList: List<Airport>,
    departureAirport: Airport,
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(destinationList) { destinationAirport ->
            FlightCard(
                departureAirport = departureAirport,
                destinationAirport = destinationAirport,
                onClick = {
                    viewModel.addOrRemoveFavorite(
                       Favorite(
                           departureAirport = departureAirport,
                           destinationAirport = destinationAirport
                       )
                    )
                },
                isPressed = viewModel.isFavorite(
                    departureCode = departureAirport.iata_code,
                    destinationCode = destinationAirport.iata_code
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun FlightCard(
    departureAirport: Airport,
    destinationAirport: Airport,
    onClick : ()-> Unit,
    isPressed : Boolean,
    modifier: Modifier = Modifier
    ){
    Card(
        shape = RoundedCornerShape(
            topStart = 0f,
            bottomStart = 0f,
            bottomEnd = 0f,
            topEnd = 50f),
            modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(id = R.string.arrive).uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                )
                AirportData(destinationAirport)
            }
            IconButton(onClick = onClick, modifier = Modifier.weight(0.3f)) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription ="Add to Favorites",
                    tint = if (isPressed) MaterialTheme.colorScheme.tertiary else Color.Unspecified,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun FlightCardPreview(){
    val departure = Airport (0, "Warsaw Chopin Airport", 1, "WAW")
    val destination = Airport (1, "Stockholm Arland a Airport", 2, "ARN")
    FlightSearchTheme {
        FlightCard(
            departureAirport = departure,
            destinationAirport = destination,
            onClick = {},
            isPressed = true

        )
    }
}
