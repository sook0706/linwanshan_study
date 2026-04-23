package com.lostfound.lostfound.service;

import com.lostfound.lostfound.common.R;

public interface AdminService {
    R getUserList();
    R banUser(Long userId);
    R unbanUser(Long userId);
    R deleteItem(Long itemId);
    R getStatistics();
}