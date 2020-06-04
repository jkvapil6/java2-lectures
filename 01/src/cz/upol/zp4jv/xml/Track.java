package cz.upol.zp4jv.xml;


/**
 * Trida reprezentujici info o videozaznamu
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-17
 */
public class Track {

    private String location;
    private String duration;
    private String name;

    public Track() {
        location = "none";
        duration = "none";
        name = "none";
    }

    public Track(String location, String duration, String name) {
        this.location = location;
        this.duration = duration;
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }
}
