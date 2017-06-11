package com.lab.gruszczynski.mplayer.model;

/**
 * Created by maciej on 05.06.17.
 */
public class Track {
    private String performer;
    private String album;
    private String title;
    private int cover;
    private int sound;

    public Track(String performer, String album, String title, int cover, int sound) {
        this.performer = performer;
        this.album = album;
        this.title = title;
        this.cover = cover;
        this.sound = sound;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }
}
