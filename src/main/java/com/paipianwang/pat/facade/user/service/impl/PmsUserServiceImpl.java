package com.paipianwang.pat.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paipianwang.pat.common.entity.DataGrid;
import com.paipianwang.pat.common.entity.PageParam;
import com.paipianwang.pat.facade.user.entity.PmsUser;
import com.paipianwang.pat.facade.user.entity.ThirdBind;
import com.paipianwang.pat.facade.user.service.PmsUserFacade;
import com.paipianwang.pat.facade.user.service.biz.PmsUserBiz;

/**
 * user Dubbo服务接口实现
 */
@Service("pmsUserFacade")
public class PmsUserServiceImpl implements PmsUserFacade {

	@Autowired
	private final PmsUserBiz biz = null;

	@Override
	public int validationPhone(final String telephone, final String loginName) {
		int num = biz.validationPhone(telephone, loginName);
		return num;
	}

	@Override
	public PmsUser register(final PmsUser user) {
		final long ret = biz.register(user);
		user.setVerification_code("");
		if (ret > 0){
			// 保存成功
			user.setUserId(user.getId());
			return user;
		} else if(ret == -1) {
			PmsUser pmsUser = new PmsUser();
			pmsUser.setUserId(-1);
			return pmsUser;
		}
		return null;
	}

	@Override
	public boolean userInfoBind(final PmsUser user) {
		return biz.userInfoBind(user);
	}

	@Override
	public List<PmsUser> verificationUserExistByThirdLogin(final PmsUser user) {
		return biz.verificationUserExistByThirdLogin(user);
	}

	@Override
	public PmsUser threeLoginPhone(final String telephone) {
		final PmsUser user = biz.findUserByPhone(telephone);
		return user;
	}

	@Override
	public Map<String, Object> bindThird(final ThirdBind bind) {
		return biz.bindThird(bind);
	}

	@Override
	public long modifyUserPassword(final PmsUser user) {
		return biz.modifyUserPassword(user);
	}

	@Override
	public long modifyUserLoginName(final PmsUser user) {
		return biz.modifyUserLoginName(user);
	}

	@Override
	public PmsUser findUserByAttr(final PmsUser user) {
		final PmsUser u = biz.findUserByAttr(user);
		return u;
	}

	@Override
	public PmsUser findUserByLoginNameAndPwd(final PmsUser user) {
		return biz.findUserByLoginNameAndPwd(user);
	}

	@Override
	public PmsUser findUserById(final Long userId) {
		return biz.findUserById(userId);
	}

	@Override
	public boolean uniqueUserName(final PmsUser user) {
		return biz.uniqueUserName(user);
	}

	@Override
	public long modifyUserInfo(final PmsUser user) {
		return biz.modifyUserInfo(user);
	}

	@Override
	public long modifyUserPhoto(final PmsUser user) {
		return biz.modifyUserPhoto(user);
	}

	@Override
	public long modifyUserPhone(final PmsUser user) {
		return biz.modifyUserPhone(user);
	}

	@Override
	public Map<String, Object> thirdStatus(final PmsUser user) {
		return biz.thirdStatus(user);
	}

	@Override
	public boolean userInfoUnBind(final PmsUser user) {
		return biz.userInfoUnBind(user);
	}

	@Override
	public DataGrid<PmsUser> listWithPagination(PageParam pageParam, Map<String, Object> paramMap) {
		return biz.listWithPagination(pageParam, paramMap);
	}

	@Override
	public long save(final PmsUser user) {
		return biz.save(user);
	}

	@Override
	public long update(final PmsUser user) {
		return biz.update(user);
	}

	@Override
	public long delete(long[] ids) {
		return biz.delete(ids);
	}

	@Override
	public List<PmsUser> all() {
		return biz.all();
	}

	@Override
	public List<PmsUser> findUserByName(final PmsUser user) {
		return biz.findUserByName(user);
	}

	@Override
	public long findUnlevelUsers() {
		return biz.findUnlevelUsers();
	}

}
