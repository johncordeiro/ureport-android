package in.ureport.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

import in.ureport.managers.LocalNotificationManager;
import in.ureport.models.Story;
import in.ureport.models.User;
import in.ureport.models.db.StoryNotification;

/**
 * Created by periclesterto on 20/10/16.
 */
public class StoryNotificationTask extends NotificationTask<Story, Void, Void> {

    private static final String TAG = "StoryNotification";
    public static final String NEW_STORY_NOTIFICATION = "newStoryNotification";

    private Story story;

    public StoryNotificationTask(Context context, Story story) {
        super(context);
    }

    @Override
    protected Void doInBackground(Story... params) {
        try {
            story = params[0];

            StoryNotification storyNotification = buildStoryNotification(story);
            storyNotification.save();

            LocalNotificationManager localNotificationManager = new LocalNotificationManager(context);
            localNotificationManager.sendStoryNotification(storyNotification, story);
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }

    @NonNull
    private StoryNotification buildStoryNotification(Story story) {
        StoryNotification storyNotification = new StoryNotification();
        storyNotification.setDate(new Date());
        storyNotification.setMessage(story.getContent());
        storyNotification.setStoryId(story.getKey());

        User author = story.getUserObject();
        if(author != null) {
            storyNotification.setNickname(author.getNickname());
        }
        return storyNotification;
    }

    @Override
    protected String getNotificationType() {
        return NEW_STORY_NOTIFICATION;
    }
}
