package com.example.vidas.androidproject;


public class Film {

    private int id;
    private String name;
    private String director;
    private String actors;
    private String genre;
    private int length;
    private int rating;
    private int ratingPoints;
    private int votes;
    private String date;
    private String cenzas;
    private String username;

    public Film(){}

    public Film(int id, String name, String director, String actors, String genre, int length, String date,
                int rating, int ratingPoints, int votes, String cenzas, String username) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.actors = actors;
        this.genre = genre;
        this.length = length;
        this.rating = rating;
        this.ratingPoints = ratingPoints;
        this.votes = votes;
        this.date = date;
        this.cenzas = cenzas;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name;}

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingPoints() {
        return ratingPoints;
    }

    public void setRatingPoints(int ratingPoints) {
        this.ratingPoints = ratingPoints;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCenzas() {
        return cenzas;
    }

    public void setCenzas(String cenzas) {
        this.cenzas = cenzas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id + '\n' +
                ", name='" + name + '\n' +
                ", director='" + director + '\n' +
                ", actors='" + actors + '\n' +
                ", genre='" + genre + '\n' +
                ", length=" + length + '\n' +
                ", rating=" + rating + '\n' +
                ", ratingPoints=" + ratingPoints + '\n' +
                ", votes=" + votes + '\n' +
                ", date='" + date + '\n' +
                ", cenzas='" + cenzas + '\n' +
                '}';
    }
}
