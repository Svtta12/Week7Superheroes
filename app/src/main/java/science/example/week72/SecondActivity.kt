package science.example.week72

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import science.example.week72.MainActivity.Companion.Information
import science.example.week72.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        with(binding) {

            val count = intent.getIntExtra("name", 0)
            val baseURl = Information[count].imageurl

            textName.text = Information[count].name
            textRealname.text = Information[count].realname.toString()
            textTeamSuperheroes.text = Information[count].team.toString()
            textCreatedBy.text = Information[count].createdby.toString()
            textPublisher.text = Information[count].publisher.toString()
            textBio.text = Information[count].bio.toString()

            Picasso.get()
                .load(baseURl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageSuperheroesDetail)
        }
        setContentView(binding.root)

    }

}