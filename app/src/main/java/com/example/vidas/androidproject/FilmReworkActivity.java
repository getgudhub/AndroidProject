package com.example.vidas.androidproject;


import android.content.DialogInterface;
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

public class FilmReworkActivity extends AppCompatActivity {

    private static final int adminLevel = 9;

    RadioGroup rbGroup;
    RadioButton rbGood, rbBad, rbBest, rbNoVote;
    CheckBox cbDokum, cbDrama, cbIstor, cbLiet, cbVeiks, cbTriler, cbSiaubo, cbFantas, cbKomed, cbAnima;
    Spinner spinner;
    Toolbar toolbar;
    Button btnUpdate, btnDelete;
    EditText etName, etDirector, etActors, etLength, etDate;
    TextView tvGenre, tvUsername, tvUsernameDisplay;
    DatabaseSQLiteFilm db;

    String items[] = {"Nėra", "N-7", "N-9", "N-13", "N-16", "N-18"};

    int id, userlevel, length, rating, ratingPoints, votes;
    String name, date, director;
    String rbActors = "";
    String checkboxText = "";
    String sCenz = "";
    private String filmUsername;
    private String username;

    Film film;
    Film oldFilm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.rework_label);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsernameDisplay = (TextView) findViewById(R.id.tvUsernameDisplay);
        etName = (EditText) findViewById(R.id.etName);
        etDirector = (EditText) findViewById(R.id.etDirector);
        etActors = (EditText) findViewById(R.id.etActors);
        etLength = (EditText) findViewById(R.id.etLength);
        etDate = (EditText) findViewById(R.id.etDate);

        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rbBest = (RadioButton) findViewById(R.id.rbBest);
        rbGood = (RadioButton) findViewById(R.id.rbGood);
        rbBad = (RadioButton) findViewById(R.id.rbBad);
        rbNoVote = (RadioButton) findViewById(R.id.rbNoVote);

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


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            oldFilm = new Film();
            userlevel = bundle.getInt("userlevel");
            id = bundle.getInt("id");
            oldFilm.setId(id);
            name = bundle.getString("name");
            oldFilm.setName(name);
            etName.setText(name);
            checkboxText = bundle.getString("genre");
            oldFilm.setGenre(checkboxText);
            setCheckBox();
            director = bundle.getString("director");
            oldFilm.setDirector(director);
            etDirector.setText(director);
            rbActors = bundle.getString("actors");
            oldFilm.setActors(rbActors);
            etActors.setText(rbActors);
            length = bundle.getInt("length");
            etLength.setText("" + length);
            oldFilm.setLength(length);
            date = bundle.getString("date");
            etDate.setText(date);
            oldFilm.setDate(date);
            sCenz = bundle.getString("cenz");
            oldFilm.setCenzas(sCenz);
            setSpinnerText(sCenz);
            rating = bundle.getInt("rating");

            ratingPoints = bundle.getInt("ratingPoints");

            votes = bundle.getInt("votes");

            filmUsername = bundle.getString("fUsername");

            username = bundle.getString("username");
        }
        if(userlevel == adminLevel){
            tvUsername.setVisibility(View.VISIBLE);
            tvUsernameDisplay.setVisibility(View.VISIBLE);
            tvUsernameDisplay.setText(filmUsername);
        }else{
            tvUsernameDisplay.setVisibility(View.INVISIBLE);
            tvUsername.setVisibility(View.INVISIBLE);
            tvUsernameDisplay.setText(filmUsername);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        updateFilm();

                        db.updateFilm(film);

                        Intent intent2 = new Intent(FilmReworkActivity.this, FilmTableActivity.class);
                        intent2.putExtra("username", username);
                        startActivity(intent2);
                        FilmReworkActivity.this.finish();
                    }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = new DatabaseSQLiteFilm(FilmReworkActivity.this);
                    db.deleteFilm(id);
                    Toast.makeText(FilmReworkActivity.this, "Filmas ištrintas", Toast.LENGTH_SHORT).show();

                    Intent goBack = new Intent(FilmReworkActivity.this, FilmTableActivity.class);
                    goBack.putExtra("username", username);
                    startActivity(goBack);
                    FilmReworkActivity.this.finish();

                }
            });
    }

    public void setCheckBox(){
        if(checkboxText.contains(cbAnima.getText().toString()+", ")){
            cbAnima.setChecked(true);
        }if(checkboxText.contains(cbDokum.getText().toString()+", ")){
            cbDokum.setChecked(true);
        }if(checkboxText.contains(cbIstor.getText().toString()+", ")){
            cbIstor.setChecked(true);
        }if(checkboxText.contains(cbDrama.getText().toString()+", ")){
            cbDrama.setChecked(true);
        }if(checkboxText.contains(cbKomed.getText().toString()+", ")){
            cbKomed.setChecked(true);
        }if(checkboxText.contains(cbLiet.getText().toString()+", ")){
            cbLiet.setChecked(true);
        }if(checkboxText.contains(cbVeiks.getText().toString()+", ")){
            cbVeiks.setChecked(true);
        }if(checkboxText.contains(cbTriler.getText().toString()+", ")){
            cbTriler.setChecked(true);
        }if(checkboxText.contains(cbSiaubo.getText().toString()+", ")){
            cbSiaubo.setChecked(true);
        }if(checkboxText.contains(cbFantas.getText().toString()+", ")){
            cbFantas.setChecked(true);
        }
    }

    public void setSpinnerText(String spinnerText){
        if(spinnerText.equals(items[0])){
            spinner.setSelection(0);
        }else if(spinnerText.equals(items[1])){
            spinner.setSelection(1);
        }else if(spinnerText.equals(items[2])){
            spinner.setSelection(2);
        }else if(spinnerText.equals(items[3])){
            spinner.setSelection(3);
        }else if(spinnerText.equals(items[4])){
            spinner.setSelection(4);
        }else if(spinnerText.equals(items[5])){
            spinner.setSelection(5);
        }
    }

    @Override
    public void onBackPressed(){
            updateFilm();
        if(!(oldFilm.getName().equals(film.getName())&&
                oldFilm.getGenre().equals(film.getGenre())&&
                oldFilm.getDirector().equals(film.getDirector())&&
                oldFilm.getActors().equals(film.getActors())&&
                oldFilm.getLength() == film.getLength() &&
                oldFilm.getDate().equals(film.getDate())&&
                oldFilm.getCenzas().equals(film.getCenzas())&&
                oldFilm.getRating() == film.getRating()))
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Ar norite išeiti neišsaugoję pakitimų?.")
                    .setPositiveButton("Taip", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent goBack = new Intent(FilmReworkActivity.this, FilmTableActivity.class);
                            goBack.putExtra("username", username);
                            startActivity(goBack);
                            FilmReworkActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }else{
            Intent goBack = new Intent(FilmReworkActivity.this, FilmTableActivity.class);
            goBack.putExtra("username", username);
            startActivity(goBack);
            FilmReworkActivity.this.finish();
        }
    }

    public void updateFilm(){
        this.film = new Film();
        db = new DatabaseSQLiteFilm(FilmReworkActivity.this);

        if (etName.getText().toString().equals("") || !Validation.isValidName(etName.getText().toString())) {
            etName.requestFocus();
            etName.setError(getResources().getString(R.string.invalid_name));
        } else if (etDirector.getText().toString().equals("") || !Validation.isValidFullName(etDirector.getText().toString())) {
            etDirector.requestFocus();
            etDirector.setError(getResources().getString(R.string.invalid_director));
        } else if (etActors.getText().toString().equals("") || !Validation.isValidFullName(etActors.getText().toString())) {
            etActors.requestFocus();
            etActors.setError(getResources().getString(R.string.invalid_actors));
        } else if (!(cbAnima.isChecked() || cbVeiks.isChecked() || cbTriler.isChecked() || cbSiaubo.isChecked() || cbLiet.isChecked() ||
                cbKomed.isChecked() || cbFantas.isChecked() || cbIstor.isChecked() || cbDrama.isChecked() || cbDokum.isChecked())) {
            tvGenre.requestFocus();
            tvGenre.setError(getResources().getString(R.string.invalid_genre));
        } else if (etLength.getText().toString().equals("") || !Validation.isValidTime(etLength.getText().toString())) {
            etLength.requestFocus();
            etLength.setError(getResources().getString(R.string.invalid_length));
        } else if (etDirector.getText().toString().equals("") || !Validation.isValidDate(etDate.getText().toString())) {
            etDate.requestFocus();
            etDate.setError(getResources().getString(R.string.invalid_date));
        } else {
            film.setId(id);
            film.setName(etName.getText().toString());
            film.setDirector(etDirector.getText().toString());
            film.setActors(etActors.getText().toString());

            checkboxText = "";
            if (cbAnima.isChecked()) {
                checkboxText += "Animacija, ";
            }
            if (cbDokum.isChecked()) {
                checkboxText += "Dokumentinis, ";
            }
            if (cbDrama.isChecked()) {
                checkboxText += "Drama, ";
            }
            if (cbFantas.isChecked()) {
                checkboxText += "Fantastinis, ";
            }
            if (cbIstor.isChecked()) {
                checkboxText += "Istorinis, ";
            }
            if (cbKomed.isChecked()) {
                checkboxText += "Komedija, ";
            }
            if (cbLiet.isChecked()) {
                checkboxText += "Lietuviškas, ";
            }
            if (cbSiaubo.isChecked()) {
                checkboxText += "Siaubo, ";
            }
            if (cbTriler.isChecked()) {
                checkboxText += "Trileris, ";
            }
            if (cbVeiks.isChecked()) {
                checkboxText += "Veiksmo, ";
            }
            film.setGenre(checkboxText);

            film.setLength(Integer.parseInt(etLength.getText().toString()));
            film.setDate(etDate.getText().toString());

            votes = 1;   //on this app the votes doesn't really matter because it's private
            if (rbBad.isChecked()) {
                rating = 2;
                ratingPoints = 2;
            } else if (rbBest.isChecked()) {
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

            sCenz = spinner.getSelectedItem().toString();
            film.setCenzas(sCenz);

        }
    }

}
