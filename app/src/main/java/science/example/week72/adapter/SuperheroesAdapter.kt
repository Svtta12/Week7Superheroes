package science.example.week72.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import science.example.week72.R
import science.example.week72.databinding.ActivityItemBinding
import science.example.week72.model.ModelSuperheroes


class SuperheroesAdapter(private val superheroes: MutableList<ModelSuperheroes>,
                         private val listener: Listener)
    : RecyclerView.Adapter<SuperheroesAdapter.SuperheroesViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuperheroesAdapter.SuperheroesViewHolder {
        return SuperheroesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: SuperheroesViewHolder, position: Int) {
        val superhero = superheroes[position]
        with(holder.binding) {
            holder.itemView.tag = superhero

            textNameSuperheroes.text = superhero.name
            textTeamSuperheroes.text = superhero.team

            Picasso.get()
                .load(superheroes[position].imageurl)
                .into(imageSuperheroes)

            imageSuperheroes.setOnClickListener {
                listener.onClickItem(superheroes, position)
            }
        }

    }

    override fun getItemCount(): Int = superheroes.size

    class SuperheroesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ActivityItemBinding.bind(itemView)
    }
}

interface Listener {
    fun onClickItem(superheroes: MutableList<ModelSuperheroes>, position: Int)
}
