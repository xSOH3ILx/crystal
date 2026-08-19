package ir.marheil.crystal.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class NobitexStatsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("stats") val stats: Map<String, NobitexMarketStat>
)

data class NobitexMarketStat(
    @SerializedName("latest") val latestPriceRials: String,
    @SerializedName("dayChange") val dayChange: String
)

interface NobitexApiService {
    @GET("market/stats")
    suspend fun getMarketStats(
        @Query("srcCurrency") srcCurrency: String = "btc,eth,usdt,sol,ton",
        @Query("dstCurrency") dstCurrency: String = "rls"
    ): NobitexStatsResponse
}
