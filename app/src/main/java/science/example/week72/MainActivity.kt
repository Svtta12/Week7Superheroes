package science.example.week72

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import science.example.week72.adapter.Listener
import science.example.week72.adapter.SuperheroesAdapter
import science.example.week72.data.ApiInterface
import science.example.week72.data.Repository
import science.example.week72.databinding.ActivityMainBinding
import science.example.week72.model.ModelSuperheroes

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var api: ApiInterface
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: LinearLayoutManager
    private lateinit var adapter: SuperheroesAdapter

    private lateinit var preferences: SharedPreferences
    private lateinit var forjson: List<ModelSuperheroes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        api = Repository.retrofitService

        binding.recyclerview.setHasFixedSize(true)

        layout = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layout

        if (open()) {
            init()
        }
        else getAllSuperheroes()
    }

    override fun onClickItem(superheroes: MutableList<ModelSuperheroes>, position: Int) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", position)
        startActivity(intent)
    }

    private fun getAllSuperheroes() {
        api.getSuperheroes().enqueue(object : Callback<MutableList<ModelSuperheroes>> {
            override fun onFailure(call: Call<MutableList<ModelSuperheroes>>, t: Throwable) {
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MutableList<ModelSuperheroes>>,
                response: Response<MutableList<ModelSuperheroes>>
            ) {
                adapter = SuperheroesAdapter(response.body() as MutableList<ModelSuperheroes>, this@MainActivity)
                adapter.notifyDataSetChanged()
                binding.recyclerview.adapter = adapter
                Information = response.body()!!
                save()
            }
        })
    }


    private fun save(){
        val gson = Gson()
        val editor = preferences.edit()
        editor.putString("Log", gson.toJson(Information)).apply()
    }

    private fun open() :Boolean{
        val dataFromSharedPrefs = preferences.getString("Log", null)
        val gson = Gson()
        if (dataFromSharedPrefs != null) {
            forjson = gson.fromJson(
                dataFromSharedPrefs,
                Array<ModelSuperheroes>::class.java
            ).asList()
            return true
        }
        return false
    }


    private fun init(){
        adapter = SuperheroesAdapter(forjson as MutableList<ModelSuperheroes>, this@MainActivity)
        binding.recyclerview.adapter = adapter
    }

    companion object{
        var Information = listOf<ModelSuperheroes>()
    }
}




