package com.feng.roomstatusview.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by feng on 16/8/17.
 */
public class RoomStatusEntity implements Parcelable {
    private List<RoomStatusInfo> roomStatusInfoList;
    private List<RoomDetail> roomDetailList;

    public List<RoomStatusInfo> getRoomStatusInfoList() {
        return roomStatusInfoList;
    }

    public void setRoomStatusInfoList(List<RoomStatusInfo> roomStatusInfoList) {
        this.roomStatusInfoList = roomStatusInfoList;
    }

    public List<RoomDetail> getRoomDetailList() {
        return roomDetailList;
    }

    public void setRoomDetailList(List<RoomDetail> roomDetailList) {
        this.roomDetailList = roomDetailList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.roomStatusInfoList);
        dest.writeTypedList(this.roomDetailList);
    }

    public RoomStatusEntity() {
    }

    protected RoomStatusEntity(Parcel in) {
        this.roomStatusInfoList = in.createTypedArrayList(RoomStatusInfo.CREATOR);
        this.roomDetailList = in.createTypedArrayList(RoomDetail.CREATOR);
    }

    public static final Creator<RoomStatusEntity> CREATOR = new Creator<RoomStatusEntity>() {
        @Override
        public RoomStatusEntity createFromParcel(Parcel source) {
            return new RoomStatusEntity(source);
        }

        @Override
        public RoomStatusEntity[] newArray(int size) {
            return new RoomStatusEntity[size];
        }
    };
}
