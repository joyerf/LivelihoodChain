package com.joyerf.livelihoodchain.datastruct;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jiezongchang on 2018/2/7.
 */
@Entity
public class Announcement {
    @Id(autoincrement = true)
    private Long _id;

    @Unique
    @NotNull
    public  int id = -1;

    @Property(nameInDb = "activityTitle")
    public String activityTitle;

    @Property(nameInDb = "activityContext")
    public String activityContext;

    @Property(nameInDb = "effectiveDate")
    public String effectiveDate;

    @Property(nameInDb = "createDate")
    public String createDate;

    @Generated(hash = 236821557)
    public Announcement(Long _id, int id, String activityTitle,
            String activityContext, String effectiveDate, String createDate) {
        this._id = _id;
        this.id = id;
        this.activityTitle = activityTitle;
        this.activityContext = activityContext;
        this.effectiveDate = effectiveDate;
        this.createDate = createDate;
    }

    @Generated(hash = 2100938548)
    public Announcement() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityTitle() {
        return this.activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityContext() {
        return this.activityContext;
    }

    public void setActivityContext(String activityContext) {
        this.activityContext = activityContext;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "ID=" + _id +
                ", id=" + id +
                ", activityTitle='" + activityTitle + '\'' +
                ", activityContext='" + activityContext + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
