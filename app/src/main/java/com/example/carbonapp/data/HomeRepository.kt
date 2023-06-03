package com.example.carbonapp.data

import com.example.carbonapp.data.response.HomeResponse
import com.example.carbonapp.`object`.News

class HomeRepository {

    private val newsDummies = arrayListOf(
        News(1, "https://i0.wp.com/forestsnews.cifor.org/wp-content/uploads/2022/12/52575061464_9232b26d27_c-copy.jpg?resize=800%2C415&ssl=1", "CIFOR Forests News - May 7", "Researchers to support East Kalimantan's low-carbon emission programme with impact evaluation", "Quasi-experimental research seeks to help the East Kalimantan provincial government in implementing effective, efficient, and equitable..."),
        News(2, "https://totalenergies.com/sites/g/files/nytnzq121/files/styles/w_1110/public/images/2023-05/site_biomethanisation_TotalEnergies.jpg?itok=y1oQiCau", "Total Energies - Sep 10", "Reducing scope 3 emissions, together with society", "A major focus of TotalEnergies' strategy is to work with our customers on their energy consumption patterns to reduce scope 3 emissions."),
        News(3, null, "Exxon Mobil - Feb 28", "Carbon capture agreement with Nucor Corporation", "ExxonMobil Low Carbon Solutions' newest carbon capture and storage agreement – with Nucor Corporation, one of North America's largest steel..."),
    )

    companion object {
        val instance: HomeRepository by lazy { HomeRepository() }
    }

    var data: HomeResponse? = null
        private set

    fun fetchData(callback: (Response<HomeResponse>) -> Unit) {
        try {
            // TODO: Get home data from API
            // simulate success request
            val response = Response.Success(200, "success", HomeResponse(
                1000, ArrayList(), newsDummies, ArrayList()
            ))
            callback(response)
        } catch (e: Throwable) {
            callback(Response.Error(0, "Connection Error", Exception(e)))
        }
    }
}