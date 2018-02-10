package com.joyerf.livelihoodchain.model;

import com.joyerf.livelihoodchain.datastruct.Announcement;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.utils.PreferenceUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiezongchang on 2018/2/7.
 */

public class GetAnnouncementModel extends BaseModel implements Constants {
    public interface OnGetAnnouncementListener {
        void onGetAnnouncement(Announcement announcement);
    }

    public void getAnnouncement(final OnGetAnnouncementListener listener){
        final Request request = new Request.Builder()
                .url(DOMAIN_URL + NEW_ACTIVITY)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onGetAnnouncement(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Logger.t("getAnnouncement").json(responseStr);
                Announcement announcement = gson.fromJson(responseStr, Announcement.class);
                if(announcement != null && announcement.id > 0){
                    listener.onGetAnnouncement(announcement);
                    PreferenceUtils.getInstance().setAnnouncement(responseStr);
                    GreenDaoManager.getInstance().getDaoSession().insertOrReplace(announcement);
                } else {
                    listener.onGetAnnouncement(null);
                }
            }
        });
    }
}
