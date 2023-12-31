import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.model.AirportDao
import com.example.flightsearch.data.model.FlightSearchDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AirportDaoTest {
    private lateinit var airportDao: AirportDao
    private lateinit var database: FlightSearchDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.databaseBuilder(
            context = context, FlightSearchDatabase::class.java, "test_database"
        ).createFromAsset("database/flight_search.db")
            .fallbackToDestructiveMigration()
            .build()
        airportDao = database.airportDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAll_getAllByPassengerCount() = runBlocking {
        val allItems = airportDao.getAllByPassengers(0).first()
        allItems.forEach {
            if (it.id < allItems.count()) Assert.assertTrue(allItems[it.id - 1].passengers >= allItems[it.id].passengers)
        }
    }

    @Test
    @Throws(Exception::class)
    fun daoGetByName_getByIataCodeFull() = runBlocking {
        val allItems=airportDao.getAirportByName("WAW").first()
        Assert.assertEquals(allItems[0].name, "Warsaw Chopin Airport")
    }

    @Test
    @Throws(Exception::class)
    fun daoGetByName_getByIataCodePartial() = runBlocking {
        val allItems=airportDao.getAirportByName("WA%").first()
        Assert.assertEquals(allItems[0].name, "Warsaw Chopin Airport")
    }

    @Test
    @Throws(Exception::class)
    fun daoGetByName_getByNameFull() = runBlocking {
        val allItems=airportDao.getAirportByName("Warsaw Chopin Airport").first()
        Assert.assertEquals(allItems[0].iata_code, "WAW")
    }

    @Test
    @Throws(Exception::class)
    fun daoGetByName_getByNamePartial() = runBlocking {
        val allItems=airportDao.getAirportByName("%saw Chopin%").first()
        Assert.assertEquals(allItems[0].iata_code, "WAW")
    }
}