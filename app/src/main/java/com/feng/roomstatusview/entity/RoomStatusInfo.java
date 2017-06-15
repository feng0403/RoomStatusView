package com.feng.roomstatusview.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.feng.roomstatusview.R;
import com.feng.roomstatusview.util.ResourceUtils;


/**
 * Created by feng on 16/8/15.
 */
public class RoomStatusInfo implements Parcelable {

    /**
     * DateColor : #333333
     * DateType : 0
     * IsToday : false
     * Date : 2016-08-16T00:00:00GMT+08:00
     * DateString : null
     * WeekString : null
     * FullDateString : null
     * AvaliableRoomCount : 27
     */

    private String dateColor;
    private int dateType;
    private boolean isToday;
    private String date;
    private String dateString;
    private String weekString;
    private String fullDateString;
    private int avaliableRoomCount;
    private boolean canOperate;

    public String getDateColor() {
        return dateColor;
    }

    public void setDateColor(String dateColor) {
        this.dateColor = dateColor;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getWeekString() {
        return weekString;
    }

    public void setWeekString(String weekString) {
        this.weekString = weekString;
    }

    public String getFullDateString() {
        return fullDateString;
    }

    public void setFullDateString(String fullDateString) {
        this.fullDateString = fullDateString;
    }

    public int getAvaliableRoomCount() {
        return avaliableRoomCount;
    }

    public void setAvaliableRoomCount(int avaliableRoomCount) {
        this.avaliableRoomCount = avaliableRoomCount;
    }

    public boolean isCanOperate() {
        return canOperate;
    }

    public void setCanOperate(boolean canOperate) {
        this.canOperate = canOperate;
    }

    public boolean isWeekEnd() {
        if (TextUtils.isEmpty(weekString)) {
            return false;
        } else {
            return weekString.equals(ResourceUtils.getString(R.string.sat)) || weekString.equals(
                    ResourceUtils.getString(R.string.sun));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateColor);
        dest.writeInt(this.dateType);
        dest.writeByte(this.isToday ? (byte) 1 : (byte) 0);
        dest.writeString(this.date);
        dest.writeString(this.dateString);
        dest.writeString(this.weekString);
        dest.writeString(this.fullDateString);
        dest.writeInt(this.avaliableRoomCount);
        dest.writeByte(this.canOperate ? (byte) 1 : (byte) 0);
    }

    public RoomStatusInfo() {
    }

    protected RoomStatusInfo(Parcel in) {
        this.dateColor = in.readString();
        this.dateType = in.readInt();
        this.isToday = in.readByte() != 0;
        this.date = in.readString();
        this.dateString = in.readString();
        this.weekString = in.readString();
        this.fullDateString = in.readString();
        this.avaliableRoomCount = in.readInt();
        this.canOperate = in.readByte() != 0;
    }

    public static final Creator<RoomStatusInfo> CREATOR = new Creator<RoomStatusInfo>() {
        @Override
        public RoomStatusInfo createFromParcel(Parcel source) {
            return new RoomStatusInfo(source);
        }

        @Override
        public RoomStatusInfo[] newArray(int size) {
            return new RoomStatusInfo[size];
        }
    };
}
