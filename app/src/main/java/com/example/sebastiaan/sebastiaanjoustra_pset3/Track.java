package com.example.sebastiaan.sebastiaanjoustra_pset3;

import java.io.Serializable;

/**
 * Created by Sebastiaan on 19-Sep-17.
 */

public class Track implements Serializable{
    private String songName;
    private String artist;
    private String imageLink;
    private String lastFMLink;

    public Track(String aSongName, String anArtist, String anImageLink, String aLastFMLink) {
        songName = aSongName;
        artist = anArtist;
        imageLink = anImageLink;
        lastFMLink = aLastFMLink;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getLastFMLink() {
        return lastFMLink;
    }
}
