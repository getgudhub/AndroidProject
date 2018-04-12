package com.example.vidas.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewFilmActivity extends AppCompatActivity {

    RadioGroup rbGroup;
    RadioButton rbGood, rbBad, rbBest, rbNoVote;
    CheckBox cbDokum, cbDrama, cbIstor, cbLiet, cbVeiks, cbTriler, cbSiaubo, cbFantas, cbKomed, cbAnima;
    Spinner spinner;
    Toolbar toolbar;
    Button btnSubmit;
    EditText etName, etDirector, etActors, etLength, etDate;
    TextView tvGenre;

    String items[] = {"Nėra", "N-7", "N-9", "N-13", "N-16", "N-18" };

    Film film;
    String username="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_film);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_entry_label);

        btnSubmit = (Button) findViewById(R.id.btnAdd);
        etName = (EditText) findViewById(R.id.etName);
        etDirector = (EditText) findViewById(R.id.etDirector);
        etActors = (EditText) findViewById(R.id.etActors);
        etLength = (EditText) findViewById(R.id.etLength);
        etDate = (EditText) findViewById(R.id.etDate);

        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rbBest = (RadioButton) findViewById(R.id.rbBest);
        rbGood = (RadioButton) findViewById(R.id.rbGood);
        rbBad = (RadioButton) findViewById(R.id.rbBad);
        rbNoVote =(RadioButton)findViewById(R.id.rbNoVote);

        cbDokum = (CheckBox) findViewById(R.id.cbDokumentika);
        cbDrama = (CheckBox) findViewById(R.id.cbDrama);
        cbIstor = (CheckBox) findViewById(R.id.cbIstorinis);
        cbLiet = (CheckBox) findViewById(R.id.cbLietuviškas);
        cbVeiks = (CheckBox) findViewById(R.id.cbVeiksmo);
        cbTriler = (CheckBox) findViewById(R.id.cbTrileris);
        cbSiaubo = (CheckBox) findViewById(R.id.cbSiaubo);
        cbFantas = (CheckBox) findViewById(R.id.cbFantastinis);
        cbAnima = (CheckBox) findViewById(R.id.cbAnimacija);
        cbKomed = (CheckBox) findViewById(R.id.cbKomedija);


        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                String director;
                String actors;
                int rating;
                int ratingPoints;
                int votes;
                String spinnerText = "";


                film = new Film();
                DatabaseSQLiteFilm db = new DatabaseSQLiteFilm(NewFilmActivity.this);

                if (etName.getText().toString().equals("") || !Validation.isValidName(etName.getText().toString())) {
                    etName.requestFocus();
                    etName.setError(getResources().getString(R.string.invalid_name));
                }else if (etDirector.getText().toString().equals("") || !Validation.isValidFullName(etDirector.getText().toString())) {
                    etDirector.requestFocus();
                    etDirector.setError(getResources().getString(R.string.invalid_director));
                }else if (etActors.getText().toString().equals("") || !Validation.isValidFullName(etActors.getText().toString())) {
                    etActors.requestFocus();
                    etActors.setError(getResources().getString(R.string.invalid_actors));
                }else if (!(cbAnima.isChecked() || cbVeiks.isChecked() || cbTriler.isChecked() || cbSiaubo.isChecked() || cbLiet.isChecked() ||
                        cbKomed.isChecked() || cbFantas.isChecked() || cbIstor.isChecked() || cbDrama.isChecked() || cbDokum.isChecked())) {
                    tvGenre.requestFocus();
                    tvGenre.setError(getResources().getString(R.string.invalid_genre));
                }else if (etLength.getText().toString().equals("") || !Validation.isValidTime(etLength.getText().toString())) {
                    etLength.requestFocus();
                    etLength.setError(getResources().getString(R.string.invalid_length));
                }else if (etDirector.getText().toString().equals("") || !Validation.isValidDate(etDate.getText().toString())) {
                    etDate.requestFocus();
                    etDate.setError(getResources().getString(R.string.invalid_date));
                } else {
                    name = etName.getText().toString();
                    film.setName(name);
                    director = etDirector.getText().toString();
                    film.setDirector(director);
                    actors = etActors.getText().toString();
                    film.setActors(actors);

                    votes = 1;
                    if(rbBad.isChecked()){
                        rating = 2;
                        ratingPoints = 2;
                    }else if (rbBest.isChecked()) {
                        rating = 10;
                        ratingPoints = 10;
                    } else if (rbGood.isChecked()) {
                        rating = 7;
                        ratingPoints = 7;
                    } else {
                        rating = 0;
                        ratingPoints = 0;
                        votes = 0;
                    }

                    film.setRating(rating);
                    film.setVotes(votes);
                    film.setRatingPoints(ratingPoints);

                    String checkboxText = "";

                    if (cbAnima.isChecked()) {
                        checkboxText = checkboxText + "Animacija, ";
                    }
                    if (cbDokum.isChecked()) {
                        checkboxText = checkboxText + "Dokumentinis, ";
                    }
                    if (cbDrama.isChecked()) {
                        checkboxText = checkboxText + "Drama, ";
                    }
                    if (cbFantas.isChecked()) {
                        checkboxText = checkboxText + "Fantastinis, ";
                    }
                    if (cbIstor.isChecked()) {
                        checkboxText = checkboxText + "Istorinis, ";
                    }
                    if (cbKomed.isChecked()) {
                        checkboxText = checkboxText + "Komedija, ";
                    }
                    if (cbLiet.isChecked()) {
                        checkboxText = checkboxText + "Lietuviškas, ";
                    }
                    if (cbSiaubo.isChecked()) {
                        checkboxText = checkboxText + "Siaubo, ";
                    }
                    if (cbTriler.isChecked()) {
                        checkboxText = checkboxText + "Trileris, ";
                    }
                    if (cbVeiks.isChecked()) {
                        checkboxText = checkboxText + "Veiksmo, ";
                    }
                    film.setGenre(checkboxText);

                    film.setLength(Integer.parseInt(etLength.getText().toString()));
                    film.setDate(etDate.getText().toString());

                    spinnerText = spinner.getSelectedItem().toString();
                    film.setCenzas(spinnerText);

                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        username = bundle.getString("username");
                        film.setUsername(username);
                        db.addFilm(film);
                    }

                    Intent intent2 = new Intent(NewFilmActivity.this, FilmTableActivity.class);
                    intent2.putExtra("username", username);
                    startActivity(intent2);
                    NewFilmActivity.this.finish();

                    Toast.makeText(NewFilmActivity.this, "'"+film.getName()+"' \n sėkmingai sukurtas filmo įrašas",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }
        Intent intent2 = new Intent(NewFilmActivity.this, FilmTableActivity.class);
        intent2.putExtra("username", username);
        startActivity(intent2);
        NewFilmActivity.this.finish();
    }

}

