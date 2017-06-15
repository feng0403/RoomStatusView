package com.feng.roomstatusview.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by feng on 16/8/15.
 */
public class Occupation implements Parcelable {

    /**
     * DateType : 0
     * OccupationId : 463296209436673
     * StartTime : /Date(1470736800000-0000)/
     * HasCheckin : false
     * IsBooking : true
     * Locked : false
     * OrderId : 463296205242369
     * Days : 0
     * IsMember : false
     * ChannelName : 酒店前台
     * BookingName : 1101
     * Price : 0
     * HasCheckedout : false
     */

    private int dateType;
    private String occupationId;
    private String startTime;
    private boolean hasCheckin;
    private boolean isBooking;
    private boolean locked;
    private String orderId;
    private int days;
    private boolean isMember;
    private String channelName;
    private String bookingName;
    private double price;
    private boolean hasCheckedout;
    private boolean isStaying;

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public String getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(String occupationId) {
        this.occupationId = occupationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isHasCheckin() {
        return hasCheckin;
    }

    public void setHasCheckin(boolean hasCheckin) {
        this.hasCheckin = hasCheckin;
    }

    public boolean isBooking() {
        return isBooking;
    }

    public void setBooking(boolean booking) {
        isBooking = booking;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasCheckedout() {
        return hasCheckedout;
    }

    public void setHasCheckedout(boolean hasCheckedout) {
        this.hasCheckedout = hasCheckedout;
    }

    public boolean isStaying() {
        return isStaying;
    }

    public void setStaying(boolean staying) {
        isStaying = staying;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dateType);
        dest.writeString(this.occupationId);
        dest.writeString(this.startTime);
        dest.writeByte(this.hasCheckin ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBooking ? (byte) 1 : (byte) 0);
        dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
        dest.writeString(this.orderId);
        dest.writeInt(this.days);
        dest.writeByte(this.isMember ? (byte) 1 : (byte) 0);
        dest.writeString(this.channelName);
        dest.writeString(this.bookingName);
        dest.writeDouble(this.price);
        dest.writeByte(this.hasCheckedout ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isStaying ? (byte) 1 : (byte) 0);
    }

    public Occupation() {
    }

    protected Occupation(Parcel in) {
        this.dateType = in.readInt();
        this.occupationId = in.readString();
        this.startTime = in.readString();
        this.hasCheckin = in.readByte() != 0;
        this.isBooking = in.readByte() != 0;
        this.locked = in.readByte() != 0;
        this.orderId = in.readString();
        this.days = in.readInt();
        this.isMember = in.readByte() != 0;
        this.channelName = in.readString();
        this.bookingName = in.readString();
        this.price = in.readDouble();
        this.hasCheckedout = in.readByte() != 0;
        this.isStaying = in.readByte() != 0;
    }

    public static final Creator<Occupation> CREATOR = new Creator<Occupation>() {
        @Override
        public Occupation createFromParcel(Parcel source) {
            return new Occupation(source);
        }

        @Override
        public Occupation[] newArray(int size) {
            return new Occupation[size];
        }
    };
}
