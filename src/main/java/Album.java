import org.sql2o.*;
import java.util.*;

public class Album {
  private String name;
  private int release;
  private String genre;
  private int id;
  private int artist_id;

  public Album(String name, int release, String genre, int artist_id) {
    this.name = name;
    this.release = release;
    this.genre = genre;
    this.artist_id = artist_id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO albums(name, release, genre, artist_id) VALUES (:name, :release, :genre, :artist_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("release", this.release)
        .addParameter("genre", this.genre)
        .addParameter("artist_id", this.artist_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Album> all() {
    String sql = "SELECT id, name, release, genre, artist_id FROM albums";

    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Album.class);
    }
  }

  public static Album find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM albums WHERE id=:id";
      Album album = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Album.class);
    return album;
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE albums SET name = :name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateReleaseYear(int release) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE albums SET release = :release WHERE id=:id";
      con.createQuery(sql)
        .addParameter("release", release)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM albums WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public static List<Album> lookupByArtist(int artist_id) {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT * FROM albums WHERE artist_id=:artist_id";
  //     Album album = con.createQuery(sql)
  //     .addParameter("artist_id", artist_id)
  //     .executeAndFetch(Album.class);
  //   return album;
  //   }
  // }

  public String getName() {
    return name;
  }
  public int getReleaseYear() {
    return release;
  }
  public String getGenre() {
    return genre;
  }
  public int getId() {
    return id;
  }
  public int getArtistId() {
    return artist_id;
  }

  @Override
  public boolean equals(Object otherAlbum) {
    if(!(otherAlbum instanceof Album)) {
      return false;
    } else {
      Album newAlbum = (Album) otherAlbum;
      return this.getId() == newAlbum.getId() &&
             this.getName().equals(newAlbum.getName()) &&
             this.getReleaseYear() == newAlbum.getReleaseYear() &&
             this.getGenre().equals(newAlbum.getGenre()) &&
             this.getArtistId() == newAlbum.getArtistId();
    }
  }
}
