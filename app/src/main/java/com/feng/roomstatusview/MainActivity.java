package com.feng.roomstatusview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feng.roomstatusview.adapter.RoomStatusAdapter;
import com.feng.roomstatusview.entity.Occupation;
import com.feng.roomstatusview.entity.RoomDetail;
import com.feng.roomstatusview.entity.RoomStatusEntity;
import com.feng.roomstatusview.entity.RoomStatusInfo;
import com.feng.roomstatusview.view.CustomListView;
import com.feng.roomstatusview.view.MHorizontalScrollView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RoomStatusAdapter.OnRoomClickListener {

    @Bind(R.id.date_layout)
    LinearLayout dateLayout;

    @Bind(R.id.date_scrollview)
    MHorizontalScrollView dateScrollView;

    @Bind(R.id.list)
    CustomListView listView;

    private HashSet<MHorizontalScrollView> observerList = new HashSet<>();
    private RoomStatusAdapter adapter;
    private RoomStatusEntity roomStatusEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ButterKnife.bind(this);

        adapter = new RoomStatusAdapter(this);
        listView.setAdapter(adapter);
        generateData();
        updateRoomView(roomStatusEntity);
    }

    private void generateData() {
        roomStatusEntity = new RoomStatusEntity();
        List<RoomStatusInfo> roomStatusInfoList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            RoomStatusInfo roomStatusInfo = new RoomStatusInfo();
            roomStatusInfo.setDate("2016-06-" + (((i + 1) < 10) ? ("0" + (i + 1)) : (i + 1)) + "T00:00:00GMT+08:00");
            roomStatusInfo.setDateString("6." + (i + 1));
            if (i % 5 == 0) {
                roomStatusInfo.setAvaliableRoomCount(0);
            } else {
                roomStatusInfo.setAvaliableRoomCount(10);
            }
            roomStatusInfo.setToday(true);
            roomStatusInfo.setWeekString("六");
            roomStatusInfoList.add(roomStatusInfo);
        }


        List<RoomDetail> roomDetailList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            RoomDetail roomDetail = new RoomDetail();
            roomDetail.setRoomNumber("100" + (i + 1));
            roomDetail.setRoomTypeName("大床房");
            if (i == 1) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-05T00:00:00GMT+08:00");
                occupation.setBooking(true);
                occupation.setDays(4);
                occupation.setChannelName(getString(R.string.ctrip));
                occupation.setBookingName(getString(R.string.guest_name_1));
                occupationList.add(occupation);

                Occupation occupation2 = new Occupation();
                occupation2.setStartTime("2016-06-13T00:00:00GMT+08:00");
                occupation2.setStaying(true);
                occupation2.setDays(4);
                occupation2.setChannelName(getString(R.string.meituan));
                occupation2.setBookingName(getString(R.string.guest_name_2));
                occupationList.add(occupation2);

                Occupation occupation3 = new Occupation();
                occupation3.setStartTime("2016-06-18T00:00:00GMT+08:00");
                occupation3.setDays(1);
                occupation3.setChannelName(getString(R.string.meituan));
                occupation3.setBookingName(getString(R.string.guest_name_4));
                occupationList.add(occupation3);

                roomDetail.setOccupationList(occupationList);
            } else if (i == 2) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-01T00:00:00GMT+08:00");
                occupation.setDays(3);
                occupation.setChannelName(getString(R.string.meituan));
                occupation.setBookingName(getString(R.string.guest_name_3));
                occupationList.add(occupation);

                roomDetail.setOccupationList(occupationList);
            } else if (i == 3) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-02T00:00:00GMT+08:00");
                occupation.setBooking(true);
                occupation.setDays(3);
                occupation.setChannelName(getString(R.string.ctrip));
                occupation.setBookingName(getString(R.string.guest_name_1));
                occupationList.add(occupation);

                Occupation occupation2 = new Occupation();
                occupation2.setStartTime("2016-06-07T00:00:00GMT+08:00");
                occupation2.setStaying(true);
                occupation2.setDays(1);
                occupation2.setChannelName(getString(R.string.meituan));
                occupation2.setBookingName(getString(R.string.guest_name_2));
                occupationList.add(occupation2);

                roomDetail.setOccupationList(occupationList);
            } else if (i == 10) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-05T00:00:00GMT+08:00");
                occupation.setBooking(true);
                occupation.setDays(4);
                occupation.setChannelName(getString(R.string.ctrip));
                occupation.setBookingName(getString(R.string.guest_name_1));
                occupationList.add(occupation);

                Occupation occupation2 = new Occupation();
                occupation2.setStartTime("2016-06-13T00:00:00GMT+08:00");
                occupation2.setStaying(true);
                occupation2.setDays(4);
                occupation2.setChannelName(getString(R.string.meituan));
                occupation2.setBookingName(getString(R.string.guest_name_2));
                occupationList.add(occupation2);

                Occupation occupation3 = new Occupation();
                occupation3.setStartTime("2016-06-18T00:00:00GMT+08:00");
                occupation3.setDays(1);
                occupation3.setChannelName(getString(R.string.meituan));
                occupation3.setBookingName(getString(R.string.guest_name_4));
                occupationList.add(occupation3);

                roomDetail.setOccupationList(occupationList);
            } else if (i == 13) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-01T00:00:00GMT+08:00");
                occupation.setDays(3);
                occupation.setChannelName(getString(R.string.meituan));
                occupation.setBookingName(getString(R.string.guest_name_3));
                occupationList.add(occupation);

                roomDetail.setOccupationList(occupationList);
            } else if (i == 15) {
                List<Occupation> occupationList = new ArrayList<>();
                Occupation occupation = new Occupation();
                occupation.setStartTime("2016-06-02T00:00:00GMT+08:00");
                occupation.setBooking(true);
                occupation.setDays(3);
                occupation.setChannelName(getString(R.string.ctrip));
                occupation.setBookingName(getString(R.string.guest_name_1));
                occupationList.add(occupation);

                Occupation occupation2 = new Occupation();
                occupation2.setStartTime("2016-06-07T00:00:00GMT+08:00");
                occupation2.setStaying(true);
                occupation2.setDays(1);
                occupation2.setChannelName(getString(R.string.meituan));
                occupation2.setBookingName(getString(R.string.guest_name_2));
                occupationList.add(occupation2);

                roomDetail.setOccupationList(occupationList);
            } else {
                roomDetail.setOccupationList(new ArrayList<Occupation>());
            }
            if (i % 4 == 0) {
                roomDetail.setRoomStatus(RoomDetail.VACANT_DIRTY);
            } else {
                roomDetail.setRoomStatus(RoomDetail.VACANT_CLEAN);
            }
            roomDetailList.add(roomDetail);
        }


        roomStatusEntity.setRoomStatusInfoList(roomStatusInfoList);
        roomStatusEntity.setRoomDetailList(roomDetailList);

    }

    public void updateRoomView(RoomStatusEntity roomStatusEntity) {
        int scrollX = dateScrollView.getScrollX();

        updateDateView(roomStatusEntity.getRoomStatusInfoList());
        observerList.add(dateScrollView);

        dateScrollView.setChangeCallback(new MHorizontalScrollView.OnScrollChangeCallback() {
            @Override
            public void onChange(int w, int h) {
                for (MHorizontalScrollView scrollView : observerList) {
                    scrollView.scrollTo(w, h);
                }
            }
        });

        dateScrollView.scrollTo(scrollX, 0);
        adapter.init(roomStatusEntity, observerList, scrollX, this);
    }


    private void updateDateView(List<RoomStatusInfo> roomStatusInfoList) {
        dateScrollView.setChangeCallback(null);
        dateLayout.removeAllViews();
        dateScrollView.scrollTo(0, 0);
        if (null != roomStatusInfoList) {
            for (RoomStatusInfo roomStatusInfo : roomStatusInfoList) {
                View view =
                        LayoutInflater.from(this).inflate(R.layout.layout_room_date_item, dateLayout, false);
                ((TextView) view.findViewById(R.id.date)).setText(roomStatusInfo.getDateString());
                ((TextView) view.findViewById(R.id.day_of_week)).setText(roomStatusInfo.getWeekString());
                if (roomStatusInfo.getAvaliableRoomCount() > 0) {
                    ((TextView) view.findViewById(R.id.remain)).setText(getString(R.string.room_remain, roomStatusInfo.getAvaliableRoomCount() + ""));
                } else {
                    ((TextView) view.findViewById(R.id.remain)).setText(getText(R.string.room_ful));
                }
                if (roomStatusInfo.isToday() || roomStatusInfo.isWeekEnd()) {
                    ((TextView) view.findViewById(R.id.day_of_week)).setTextColor(
                            getResources().getColor(R.color.color_accent_red));
                    ((TextView) view.findViewById(R.id.date)).setTextColor(
                            getResources().getColor(R.color.color_accent_red));
                }
                dateLayout.addView(view);
            }
        }
    }


    @Override
    public void onRoomClick(RoomDetail roomDetail, int position) {
        Toast.makeText(this, roomDetail.getRoomTypeName() + " " + roomDetail.getRoomNumber() + " " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRoomStatusClick(RoomDetail roomDetail, RoomStatusInfo roomStatusInfo, Occupation occupation) {
        Toast.makeText(this, roomDetail.getRoomNumber() + " " + roomDetail.getRoomTypeName() + " " + roomStatusInfo.getDateString(), Toast.LENGTH_SHORT).show();
    }
}
