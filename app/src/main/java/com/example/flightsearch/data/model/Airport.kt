
package com.example.flightsearch.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val  name: String,
    val  passengers: Int,
    @ColumnInfo(name = "iata_code") val iata_code: String,

)
@ProvidedTypeConverter
class AirportConverter{
    @TypeConverter
    fun StringToAirport(airportJson: String?) : Airport?{
        return airportJson?.let { Json.decodeFromString(it) }
    }
    @TypeConverter
    fun AirportToString (airport: Airport?): String{
        return Json.encodeToString(airport)
    }
}
