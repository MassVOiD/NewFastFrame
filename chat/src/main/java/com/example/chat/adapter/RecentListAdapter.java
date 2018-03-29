package com.example.chat.adapter;

import com.example.chat.R;
import com.example.chat.base.Constant;
import com.example.chat.manager.UserDBManager;
import com.example.chat.util.FaceTextUtil;
import com.example.chat.util.TimeUtil;
import com.example.commonlibrary.baseadapter.adapter.BaseSwipeRecyclerAdapter;
import com.example.commonlibrary.baseadapter.viewholder.BaseWrappedViewHolder;
import com.example.commonlibrary.bean.chat.GroupTableEntity;
import com.example.commonlibrary.bean.chat.RecentMessageEntity;
import com.example.commonlibrary.bean.chat.UserEntity;


/**
 * 项目名称:    TestChat
 * 创建人:        陈锦军
 * 创建时间:    2017/5/23      9:06
 * QQ:             1981367757
 */

public class RecentListAdapter extends BaseSwipeRecyclerAdapter<RecentMessageEntity, BaseWrappedViewHolder> {

        @Override
        protected int getLayoutId() {
                return R.layout.fragment_recent_item;
        }

        @Override
        protected void convert(BaseWrappedViewHolder holder, RecentMessageEntity data, boolean isSwipeItem) {
               String avatar;
               String name;
                long unReadCount;
                if (data.getType()==RecentMessageEntity.TYPE_PERSON){
                        UserEntity userEntity= UserDBManager.getInstance().getUser(data.getId());
                        avatar=userEntity.getAvatar();
                        name=userEntity.getName();
                        unReadCount=UserDBManager.getInstance()
                                .getUnReadChatMessageSize(userEntity.getUid());
                }else {
                        GroupTableEntity groupTableEntity=UserDBManager
                                .getInstance().getGroupTableEntity(data.getId());
                        avatar=groupTableEntity.getGroupAvatar();
                        name=groupTableEntity.getGroupName();
                        unReadCount=UserDBManager.getInstance().getUnReadGroupChatMessageSize(groupTableEntity
                        .getGroupId());
                }
                holder.setText(R.id.tv_recent_name, name);
                holder.setText(R.id.tv_recent_content, FaceTextUtil.toSpannableString(holder.itemView.getContext(), data.getContent()))
                        .setText(R.id.tv_recent_time, TimeUtil.getTime(data.getCreatedTime()))
                        .setImageUrl(R.id.riv_recent_avatar,avatar)
                .setOnItemClickListener();
                int contentType = data.getContentType();
                if (contentType == Constant.TAG_MSG_TYPE_LOCATION) {
                        holder.setText(R.id.tv_recent_content, "[位置]");
                } else if (contentType == Constant.TAG_MSG_TYPE_IMAGE) {
                        holder.setText(R.id.tv_recent_content, "[图片]");
                } else if (contentType == Constant.TAG_MSG_TYPE_VOICE) {
                        holder.setText(R.id.tv_recent_content, "[语音]");
                } else if (contentType == Constant.TAG_MSG_TYPE_TEXT) {
                        holder.setText(R.id.tv_recent_content, FaceTextUtil.toSpannableString(holder.itemView.getContext(), data.getContent()));
                } else {
                        holder.setText(R.id.tv_recent_content, "[未知类型]");
                }


                if (unReadCount > 0) {
                        holder.setVisible(R.id.tv_recent_unread, true);
                        holder.setText(R.id.tv_recent_unread, unReadCount + "");
                } else {
                        holder.setVisible(R.id.tv_recent_unread, false);
                }
        }



}