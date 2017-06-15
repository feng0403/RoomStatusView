package com.feng.roomstatusview.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

/**
 * Created by feng on 16/8/15.
 */
public class RoomDetail implements Parcelable {

    public static final String VACANT_CLEAN = "VC";
    public static final String VACANT_DIRTY = "VD";
    public static final String OCCUPIED_CLEAN = "OC";
    public static final String OCCUPIED_DIRTY = "OD";

    @StringDef({VACANT_CLEAN, VACANT_DIRTY, OCCUPIED_CLEAN, OCCUPIED_DIRTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoomStatus {
    }

    /**
     * RoomId : 445946021478401
     * RoomNumber : 1101
     * RoomTypeId : 4E2ORV7BB5
     * Occupation : [{"DateType":0,"OccupationId":463296209436673,"StartTime":"/Date(1470736800000-0000)/","HasCheckin":false,"IsBooking":true,"Locked":false,"OrderId":463296205242369,"Days":0,"IsMember":false,"ChannelName":"酒店前台","BookingName":"1101","Price":0,"HasCheckedout":false}]
     * IsDirty : false
     */

    private long roomId;
    private String roomNumber;
    private String roomTypeId;
    private String roomTypeName;
    private String roomStatus;
    private List<Occupation> occupationList;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(@RoomStatus String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public List<Occupation> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<Occupation> occupationList) {
        this.occupationList = occupationList;
    }

    public boolean isDirty() {
        return (Objects.equals(roomStatus, OCCUPIED_DIRTY) || Objects.equals(roomStatus, VACANT_DIRTY));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.roomId);
        dest.writeString(this.roomNumber);
        dest.writeString(this.roomTypeId);
        dest.writeString(this.roomTypeName);
        dest.writeString(this.roomStatus);
        dest.writeTypedList(this.occupationList);
    }

    public RoomDetail() {
    }

    protected RoomDetail(Parcel in) {
        this.roomId = in.readLong();
        this.roomNumber = in.readString();
        this.roomTypeId = in.readString();
        this.roomTypeName = in.readString();
        this.roomStatus = in.readString();
        this.occupationList = in.createTypedArrayList(Occupation.CREATOR);
    }

    public static final Creator<RoomDetail> CREATOR = new Creator<RoomDetail>() {
        @Override
        public RoomDetail createFromParcel(Parcel source) {
            return new RoomDetail(source);
        }

        @Override
        public RoomDetail[] newArray(int size) {
            return new RoomDetail[size];
        }
    };
}
