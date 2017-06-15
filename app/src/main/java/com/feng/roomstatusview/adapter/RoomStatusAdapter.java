package com.feng.roomstatusview.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feng.roomstatusview.R;
import com.feng.roomstatusview.entity.Occupation;
import com.feng.roomstatusview.entity.RoomDetail;
import com.feng.roomstatusview.entity.RoomStatusEntity;
import com.feng.roomstatusview.entity.RoomStatusInfo;
import com.feng.roomstatusview.util.TimeUtils;
import com.feng.roomstatusview.view.MHorizontalScrollView;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
 * Created by feng on 16/8/15.
 */
public class RoomStatusAdapter extends BaseListAdapter<RoomDetail> implements View.OnClickListener {

    private static final int DEFAULT_TIME_MILLISECONDS = 20;
    private final LinearLayout.LayoutParams param;
    private final int roomStatusHeightPixelOffset;
    //初始化时，每个item横向移动的位置
    private int scrollXPx;
    private HashSet<MHorizontalScrollView> observerList;

    private List<RoomDetail> roomDetailList;
    private List<RoomStatusInfo> roomStatusInfoList;
    private OnRoomClickListener onRoomClickListener;

    public RoomStatusAdapter(Context context) {
        super(context);
        int roomStatusWidthPixelOffset =
                mContext.getResources().getDimensionPixelOffset(R.dimen.room_status_width);
        roomStatusHeightPixelOffset =
                mContext.getResources().getDimensionPixelOffset(R.dimen.room_status_height);
        param = new LinearLayout.LayoutParams(roomStatusWidthPixelOffset, roomStatusHeightPixelOffset);
    }

    public void init(RoomStatusEntity roomStatusEntity, HashSet<MHorizontalScrollView> observerList,
                     int scrollXPx, OnRoomClickListener listener) {
        if (roomStatusEntity != null) {
            this.scrollXPx = scrollXPx;
            this.observerList = observerList;
            this.roomDetailList = roomStatusEntity.getRoomDetailList();
            this.roomStatusInfoList = roomStatusEntity.getRoomStatusInfoList();
            this.onRoomClickListener = listener;

            setData(roomDetailList);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_room_list_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
            observerList.add(holder.mHScrollView);
            //统计防态图上下滑动
            holder.mHScrollView.setChangeCallback(new MHorizontalScrollView.OnScrollChangeCallback() {
                @Override
                public void onChange(int w, int h) {
                    scrollXPx = w;
                    for (MHorizontalScrollView scrollView : observerList) {
                        scrollView.scrollTo(w, h);
                    }
                }
            });
            holder.mHScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.mHScrollView.scrollTo(scrollXPx, 0);
                }
            }, DEFAULT_TIME_MILLISECONDS);
        } else {
            holder = (Holder) convertView.getTag();
        }

        RoomDetail item = getItem(position);

        holder.roomName.setText(item.getRoomNumber());
        holder.roomType.setText(item.getRoomTypeName());
        if (item.isDirty()) {
            holder.dirtyImage.setVisibility(View.VISIBLE);
        } else {
            holder.dirtyImage.setVisibility(View.GONE);
        }

        holder.roomLayout.setTag(R.id.tag_room, position);
        holder.roomLayout.setOnClickListener(this);

        holder.layout.removeAllViews();

        if (null != item.getOccupationList() && item.getOccupationList().size() > 0) {
            for (int i = 0, size = roomStatusInfoList.size(); i < size; ) {
                int occupationIndex =
                        getOccupationIndex(roomStatusInfoList.get(i), item.getOccupationList());
                Occupation occupation = null;
                int dayDuration = 0;//占房持续时间

                // 存在这么一种情况
                // 用户选择的查看时间是 10.1 - 10.17
                // 但是有一个占房时间为9.25 - 10.5
                // 所以要算出这个占房在这次选择中所占的长度
                if (occupationIndex == -1 && i == 0) {
                    List<Occupation> occupationList = item.getOccupationList();
                    int calenderPos = getCalenderPos(occupationList, roomStatusInfoList);

                    if (-1 != calenderPos) {
                        Occupation firstOccupation = occupationList.get(0);
                        //正确的占房时间（减去不在显示日期范围内的天数）
                        dayDuration = firstOccupation.getDays() - calenderPos;
                        occupation = firstOccupation;
                    }
                } else {
                    occupation = occupationIndex == -1 ? null : item.getOccupationList().get(occupationIndex);
                    if (occupation != null) {
                        dayDuration = occupation.getDays();
                    }
                }

                //防止add的view的长度大于holder.layout宽度
                //比如用户选择的查看时间是 10.1 - 10.17
                //而有一个占房时间为10.15 - 10.20
                //后面有部分不在显示的时间范围内
                if (i + dayDuration > roomStatusInfoList.size()) {
                    dayDuration = roomStatusInfoList.size() - i;
                }

                drawRoomView(position, i, dayDuration, occupation, holder);

                if (occupation != null) {
                    i += dayDuration;
                } else {
                    i++;
                }
            }
        } else {
            //该房型没有占房信息
            for (int j = 0, size = roomStatusInfoList.size(); j < size; j++) {
                FrameLayout frameLayout = new FrameLayout(mContext);
                frameLayout.setLayoutParams(param);
                frameLayout.setTag(R.id.tag_position, new Pair<Integer, Integer>(position, j));
                frameLayout.setBackgroundResource(R.drawable.selector_room_blank_bg_normal);
                holder.layout.addView(frameLayout);
                frameLayout.setOnClickListener(this);
            }
        }

        return convertView;
    }

    /**
     * 查找occupationList中startTime与roomStatusInfo中date相同的元素位置
     *
     * @param roomStatusInfo 房型，房号相关的bean
     * @param occupationList 占房信息
     * @return -1 没找到
     */
    private int getOccupationIndex(RoomStatusInfo roomStatusInfo, List<Occupation> occupationList) {
        for (int i = 0; i < occupationList.size(); i++) {
            if (occupationList.get(i).getStartTime().split("T")[0].equals(
                    roomStatusInfo.getDate().split("T")[0])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 画出某房型（含有入住/预定）的房态
     *
     * @param position            用于确定房型，房间信息的bean的position
     * @param roomStatusInfoIndex 用于确定日期，星期的bean的position
     * @param dayDuration         占房天数（只包含需要显示的日期范围内的天数）
     * @param occupation          入住/预定相关信息的bean
     * @param holder              listView的holder
     */
    private void drawRoomView(int position, int roomStatusInfoIndex, int dayDuration,
                              Occupation occupation, Holder holder) {
        if (null != occupation) {
            //含有入住/预定占房信息
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(roomStatusHeightPixelOffset * dayDuration,
                            roomStatusHeightPixelOffset);
            View firstView = mInflater.inflate(R.layout.layout_room_order_item, holder.layout, false);
            firstView.setLayoutParams(params);

            if (!occupation.isBooking() && !occupation.isStaying()) {
                firstView.setBackgroundResource(R.drawable.selector_room_grey_bg_normal);
            } else if (occupation.isBooking()) {
                firstView.setBackgroundResource(R.drawable.selector_room_blue_bg_normal);
            } else if (occupation.isStaying()) {
                firstView.setBackgroundResource(R.drawable.selector_room_red_bg_normal);
            }

            ((TextView) firstView.findViewById(R.id.guest_name)).setText(occupation.getBookingName());
            ((TextView) firstView.findViewById(R.id.channel)).setText(occupation.getChannelName());
            (firstView.findViewById(R.id.order_type)).setVisibility(View.GONE);

            firstView.setTag(R.id.tag_position, new Pair<>(position, roomStatusInfoIndex));
            firstView.setOnClickListener(this);

            holder.layout.addView(firstView);
        } else {
            //空房间（不含有入住预定的房间）
            FrameLayout frameLayout = new FrameLayout(mContext);
            frameLayout.setLayoutParams(param);
            frameLayout.setBackgroundResource(R.drawable.selector_room_blank_bg_normal);
            frameLayout.setTag(R.id.tag_position, new Pair<>(position, roomStatusInfoIndex));
            holder.layout.addView(frameLayout);
            frameLayout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getTag(R.id.tag_room) != null) {
            //房型被点击
            int i = (int) view.getTag(R.id.tag_room);
            onRoomClickListener.onRoomClick(getItem(i), i);
        } else {
            //某天的房间被点击
            Object tag = view.getTag(R.id.tag_position);
            if (tag != null) {
                if (!(tag instanceof Pair)) return;
                Pair<Integer, Integer> pair = (Pair<Integer, Integer>) tag;
                RoomDetail roomDetail = roomDetailList.get(pair.first);
                RoomStatusInfo roomStatusInfo = roomStatusInfoList.get(pair.second);

                List<Occupation> occupationList = roomDetail.getOccupationList();
                int occupationIndex = getOccupationIndex(roomStatusInfo, occupationList);

                int calenderPos = -1;
                //处理以下这种情况
                // 用户选择的查看时间是 10.1 - 10.17
                // 但是有一个占房时间为9.25 - 10.5
                if (-1 == occupationIndex && pair.second == 0) {
                    if (null != occupationList && occupationList.size() > 0) {
                        calenderPos = getCalenderPos(occupationList, roomStatusInfoList);
                    }
                }

                Occupation occupation = null;
                if (-1 != occupationIndex) {
                    occupation = occupationList.get(occupationIndex);
                } else if (calenderPos != -1) {
                    occupation = roomDetail.getOccupationList().get(0);
                }
                onRoomClickListener.onRoomStatusClick(roomDetail, roomStatusInfo, occupation);
            }
        }
    }

    /**
     * 用户选择的查看时间是 10.1 - 10.17
     * 但是有一个占房时间为 9.25 - 10.5
     * 这种情况下返回 10.1 在 {9.25,9.26,9.27,9.28,9.29,9.30,10.1,... 10.5}中的position
     *
     * @param occupationList     某个房型占房信息的list
     * @param roomStatusInfoList 日期，星期相关 的list
     * @return -1 如果没找到
     */
    private int getCalenderPos(List<Occupation> occupationList,
                               List<RoomStatusInfo> roomStatusInfoList) {
        Occupation firstOccupation = occupationList.get(0);
        RoomStatusInfo startRoomStatusInfo = roomStatusInfoList.get(0);
        Calendar firstOccupationCalendar = TimeUtils.parse2Calendar(firstOccupation.getStartTime());
        Calendar startRoomStatusInfoCalendar = TimeUtils.parse2Calendar(startRoomStatusInfo.getDate());
        return TimeUtils.getCalenderPos(firstOccupationCalendar, firstOccupation.getDays(),
                startRoomStatusInfoCalendar);
    }

    public interface OnRoomClickListener {
        /**
         * 修改房态（干净房/脏房）
         *
         * @param roomDetail 房型，房间号相关信息
         * @param position   位置
         */
        void onRoomClick(RoomDetail roomDetail, int position);

        /**
         * 房间点击事件
         *
         * @param roomDetail     房型，房间号相关信息
         * @param roomStatusInfo 日期，星期，能否操作信息
         * @param occupation     标识房间是否已经办理入住或者预定
         */
        void onRoomStatusClick(RoomDetail roomDetail, RoomStatusInfo roomStatusInfo,
                               Occupation occupation);
    }

    private class Holder {
        final View roomLayout;
        final TextView roomName;
        final TextView roomType;
        final MHorizontalScrollView mHScrollView;
        final LinearLayout layout;
        final ImageView dirtyImage;
        final ImageView lockImage;
        final ImageView lockedImage;

        Holder(View convertView) {
            roomLayout = convertView.findViewById(R.id.room_layout);
            roomName = (TextView) convertView.findViewById(R.id.room_name);
            roomType = (TextView) convertView.findViewById(R.id.room_type);
            mHScrollView = (MHorizontalScrollView) convertView.findViewById(R.id.h_scroll);
            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            dirtyImage = (ImageView) convertView.findViewById(R.id.dirty_img);
            lockImage = (ImageView) convertView.findViewById(R.id.lock_img);
            lockedImage = (ImageView) convertView.findViewById(R.id.locked_img);
        }
    }
}
