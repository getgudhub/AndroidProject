package com.example.vidas.androidproject;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {

    private static ClickListener clickListener = null;

    private Context context;
    private LayoutInflater inflater;
    private List<Film> film = Collections.emptyList();
    //private List<User> userL = Collections.emptyList(); // Try this
    private Film currentFilm;
    private User user;

    ArrayList<Film> arrayList = new ArrayList<>();
    FilmAdapter(ArrayList<Film> arrayList){
        this.arrayList = arrayList;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvGenre, tvDirector, tvActors;
        private CardView cardView;

        public MyViewHolder(View v)  {
            super(v);
            tvName = (TextView) itemView.findViewById(R.id.etName);
            tvGenre = (TextView) itemView.findViewById(R.id.etGenre);
            tvDirector = (TextView) itemView.findViewById(R.id.etDirector);
            tvActors = (TextView) itemView.findViewById(R.id.etActors);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(clickListener !=null){
                        clickListener.onItemClick(getAdapterPosition(),v);
                        int itemPos = getAdapterPosition();

                        int filmId = film.get(itemPos).getId();
                        String filmName = film.get(itemPos).getName();
                        String filmGenre = film.get(itemPos).getGenre();
                        String filmDirector = film.get(itemPos).getDirector();
                        String filmActors = film.get(itemPos).getActors();
                        int filmLength = film.get(itemPos).getLength();
                        String filmDate = film.get(itemPos).getDate();
                        String filmCenz = film.get(itemPos).getCenzas();
                        int filmRating = film.get(itemPos).getRating();
                        int filmRatingPoints = film.get(itemPos).getRatingPoints();
                        int filmVotes = film.get(itemPos).getVotes();
                        String fUsername = film.get(itemPos).getUsername();
                        String username = user.getUsername();  //Try this
                        int userlevel = user.getUserlevel();

                        Intent intent =  new Intent(context, FilmReworkActivity.class);
                        intent.putExtra("id", filmId);
                        intent.putExtra("name", filmName);
                        intent.putExtra("genre", filmGenre);
                        intent.putExtra("director", filmDirector);
                        intent.putExtra("actors", filmActors);
                        intent.putExtra("length", filmLength);
                        intent.putExtra("date", filmDate);
                        intent.putExtra("cenz", filmCenz);
                        intent.putExtra("rating", filmRating);
                        intent.putExtra("ratingPoints", filmRatingPoints);
                        intent.putExtra("votes", filmVotes);
                        intent.putExtra("fUsername", fUsername);
                        intent.putExtra("username", username);
                        intent.putExtra("userlevel", userlevel);

                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) inflater.inflate(R.layout.container_film_items, parent, false);
        return new MyViewHolder (v);   //return new CustomViewHolder(v)
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        currentFilm = film.get(position);
        vh.tvName.setText(currentFilm.getName());
        vh.tvGenre.setText("Žanras: \n" +currentFilm.getGenre());
        vh.tvDirector.setText("Režisierius: " +currentFilm.getDirector());
        vh.tvActors.setText("Aktoriai: "+ currentFilm.getActors());
    }

    @Override
    public int getItemCount() {
        return film.size();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    // susieti su esamu langu ir perduoti filmu sarasa is DB
    public FilmAdapter(Context context, ArrayList<Film> films, User user) {
        this.context= context;
        this.film = films;
        this.user = user; // try this
        inflater = LayoutInflater.from(context);
    }

    public void setFilter(ArrayList<Film> list, User user){
        film = new ArrayList<>();
        film.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener clickListener){
        FilmAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
