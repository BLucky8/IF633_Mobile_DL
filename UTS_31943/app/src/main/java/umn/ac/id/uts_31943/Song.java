package umn.ac.id.uts_31943;

public class Song {
    private String path;
    private String title;
    private String artist;
    private String album;
    private String duration;

    public Song(String path, String title, String artist, String album, String duration) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

}

