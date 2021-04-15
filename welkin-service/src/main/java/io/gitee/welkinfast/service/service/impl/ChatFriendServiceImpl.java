package io.gitee.welkinfast.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gitee.welkinfast.service.mapper.ChatFriendMapper;
import io.gitee.welkinfast.service.mapper.SysUserMapper;
import io.gitee.welkinfast.service.mapper.dao.ChatFriend;
import io.gitee.welkinfast.service.mapper.dao.SysUser;
import io.gitee.welkinfast.service.service.ChatFriendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/18 17:30
 * @Version 1.0.0
 */
@Service
public class ChatFriendServiceImpl extends ServiceImpl<ChatFriendMapper, ChatFriend> implements ChatFriendService {

    @Autowired
    private ChatFriendMapper chatFriendMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ChatFriend> eq = Wrappers.<ChatFriend>lambdaQuery()
                .eq(ChatFriend::getUserId, userId);
        List<ChatFriend> chatFriends = chatFriendMapper.selectList(eq);
        if (CollectionUtils.isEmpty(chatFriends)) {
            return Collections.emptyList();
        }
        List<String> friendIds = chatFriends.stream().map(ChatFriend::getFriendId).collect(Collectors.toList());
        LambdaQueryWrapper<SysUser> sysUserEq = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getId, SysUser::getNickName, SysUser::getAvatar)
                .in(SysUser::getId, friendIds);
        return sysUserMapper.selectList(sysUserEq);
    }
}
