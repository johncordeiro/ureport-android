package in.ureport.models.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by periclesterto on 20/10/16.
 */
@Table(name = "StoryNotification")
public class StoryNotification extends Model {

    @Column(name = "storyId")
    private String storyId;

    @Column(name = "picture")
    private String picture;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;

    public StoryNotification(String storyId, String picture, String nickname, String message, Date date) {
        this.storyId = storyId;
        this.picture = picture;
        this.nickname = nickname;
        this.message = message;
        this.date = date;
    }

    public StoryNotification() {}

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}