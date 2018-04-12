package com.example.vidas.androidproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FilmTableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    DatabaseSQLiteUser dbUser = new DatabaseSQLiteUser(FilmTableActivity.this);
    DatabaseSQLiteFilm db = new DatabaseSQLiteFilm(FilmTableActivity.this);

    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Film> filmList;
    private FilmAdapter adapter;

    Button btnPrideti;
    Toolbar toolbar;
    User user;
    String name = "";
    int id, userlevel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_table);

        mRecyclerView = (RecyclerView) findViewById(R.id.film_list);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FilmTableActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.search_label);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle !=null){
            name = bundle.getString("username");
        }

        user = dbUser.getUser(name);
        userlevel = user.getUserlevel();
        if(dbUser.isAdmin(userlevel)) {
            filmList = db.getAllFilms();
            adapter = new FilmAdapter(this, filmList, user);
            mRecyclerView.setAdapter(adapter);
        }else{
            filmList = db.getUserFilms(name);
            adapter = new FilmAdapter(this, filmList, user);
            mRecyclerView.setAdapter(adapter);
        }


        adapter.setOnItemClickListener(new FilmAdapter.ClickListener(){
            @Override
            public void onItemClick(int position, View v) {
                //starts new activity on Card click from adapter's class, ViewHolder method
                Intent intent = new Intent(FilmTableActivity.this, FilmReworkActivity.class);
                FilmTableActivity.this.finish();

            }
        });

        btnPrideti = (Button) findViewById(R.id.btnPrideti);
        btnPrideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilmTableActivity.this, NewFilmActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
                FilmTableActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

   /* @Override  //every time you press search btn an activity is created which in turn class this function
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsynchFetch(query).execute();
        }
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Film> newList = new ArrayList<>();
        for(Film film : filmList){
            String pavadinimas = film.getName().toLowerCase();
            if(pavadinimas.contains(newText)) newList.add(film);
        }
        adapter.setFilter(newList, user);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.film_table_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchManager sm = (SearchManager) FilmTableActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(FilmTableActivity.this);
        }
        return true;
    }

}
