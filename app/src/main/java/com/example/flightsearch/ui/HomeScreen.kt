package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            FlightSearchTopAppBar(title = stringResource(R.string.flightsearch))
        }){innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                viewModel = viewModel,
                modifier = Modifier.padding(8.dp)
            )
            if (viewModel.flightSearchUi.currentAirport == null && viewModel.flightSearchUi.input != "") {
                SearchResultList(
                    viewModel = viewModel,
                    modifier = Modifier.padding(8.dp)
                )
            }
            if (viewModel.flightSearchUi.currentAirport != null) {
                Text(
                    text = stringResource(id = R.string.flights_from) + "" + viewModel.flightSearchUi.currentAirport!!.iata_code,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                RouteList(
                    destinationList = viewModel.flightSearchUi.destinationAirportList,
                    departureAirport = viewModel.flightSearchUi.currentAirport!!,
                    viewModel = viewModel
                )
            } else if (viewModel.flightSearchUi.input == "") {
                viewModel.updateFavorites()
                Text(
                    text = stringResource(id = R.string.favorite_routes),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                FavoriteList(favorites = viewModel.favoriteUi.favorites, viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(
    title: String,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )

}