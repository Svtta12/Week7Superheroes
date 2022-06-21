package science.example.week72.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import science.example.week72.model.ModelSuperheroes

interface ApiInterface {

    @GET("marvel")
    fun getSuperheroes() : Call<MutableList<ModelSuperheroes>>

}
object Repository {

    private const val baseURL = "https://www.simplifiedcoding.net/demos/"

    val retrofitService: ApiInterface
        get() = RetrofitFactory.getClient(baseURL).create(ApiInterface::class.java)
}
object RetrofitFactory {

    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}