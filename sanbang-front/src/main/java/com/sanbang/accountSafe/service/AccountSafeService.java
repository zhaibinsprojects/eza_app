package com.sanbang.accountSafe.service;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface AccountSafeService {

	Result checkAccountStatus(HttpServletRequest request, ezs_user upi);

	Result getSecuratePhone(HttpServletRequest request, ezs_user upi);

	Result sendCode(String mobile, String code, String mobileascode, String mobilesendcodeexpir,
			String mobileinterval, String mobilesendtimes, Integer flag, String content);

	Result setOrUpdateSecuratePhone(String mobile, String code, HttpServletRequest request,ezs_user upi);

}
