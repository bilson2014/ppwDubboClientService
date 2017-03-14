package com.paipianwang.pat.facade.user.service.biz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paipianwang.pat.common.entity.DataGrid;
import com.paipianwang.pat.common.entity.PageParam;
import com.paipianwang.pat.common.util.DataUtil;
import com.paipianwang.pat.common.util.ValidateUtil;
import com.paipianwang.pat.facade.user.entity.PmsUser;
import com.paipianwang.pat.facade.user.entity.ThirdBind;
import com.paipianwang.pat.facade.user.service.dao.PmsUserDao;
/**
 * user--服务层接口
 * 事物管理层
 */
@Transactional
@Service
public class PmsUserBiz {

	@Autowired
	private PmsUserDao pmsUserDao;

	public int validationPhone(final String telephone,final String loginName) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("telephone", telephone);
		paramMap.put("loginName", loginName);
		return pmsUserDao.validationPhone(paramMap);
	}

	public long save(PmsUser user) {
		return pmsUserDao.save(user);
	}

	public boolean userInfoBind(PmsUser u) {
		// 查询第三方是不是存在绑定
		List<PmsUser> list = pmsUserDao.verificationUserExistByThirdLogin(u);
		if (list.size() > 0) {
			return false;// 已经存在绑定
		} else {
			PmsUser user = pmsUserDao.findUserById(u.getId());
			if (u.getlType().equals("qq")) {
				user.setQqUnique(u.getUniqueId());
			} else if (u.getlType().equals("weibo")) {
				user.setWbUnique(u.getUniqueId());
			} else if (u.getlType().equals("wechat")) {
				user.setWechatUnique(u.getUniqueId());
			}
			if (!ValidateUtil.isValid(user.getUserName())) {
				user.setUserName(u.getUserName());
			}
			pmsUserDao.update(user);
			return true;
		}
	}

	public List<PmsUser> verificationUserExistByThirdLogin(PmsUser user) {
		final List<PmsUser> users = pmsUserDao.verificationUserExistByThirdLogin(user);
		return users;
	}

	public PmsUser findUserByPhone(final String telephone) {
		final PmsUser user = pmsUserDao.findUserByPhone(telephone);
		return user;
	}

	/**
	 * 三方用户不存在
	 * 		 1.手机号没注册过 phoneStatus 
	 * 		 2.手机号注册过,但是未绑定第三方 thirdStatus
	 * 		 3.手机号注册过,且绑定了第三方
	 * 三方用户已经存在 ,但是未绑定手机 
	 * 		 4.手机号没注册过
	 * 		 5.手机号注册过,但是未绑定第三方
	 * 		 6.手机号注册了,也绑定了第三方
	 */
	public Map<String, Object> bindThird(ThirdBind bind) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "1");
		PmsUser user = new PmsUser();
		if (bind.getCode() == 0) {// 用户不存在
			if (bind.getPhoneStatus().equals("noregister")) {// 注册新用户,绑定第三方
				user.setUserName(bind.getUserName());
				user.setImgUrl(bind.getImgUrl());
				user.setPassword(DataUtil.md5("123456"));
				user.setTelephone(bind.getTelephone());
				if (bind.getType().equals("qq")) {
					user.setQqUnique(bind.getUnique());
				} else if (bind.getType().equals("wechat")) {
					user.setWechatUnique(bind.getUnique());
				} else if (bind.getType().equals("wb")) {
					user.setWbUnique(bind.getUnique());
				}
				// 过滤掉Emoji表情
				boolean isdefault = false;
				String filterEmoji = ValidateUtil.filterEmoji(user.getUserName());
				if (ValidateUtil.isValid(filterEmoji)) {
					user.setUserName(filterEmoji);
				} else {
					// 添加默认 用户名
					user.setUserName("用户");
					isdefault = true;
				}
				pmsUserDao.saveByThirdLogin(user);
				if (isdefault) {
					user.setUserName(user.getUserName() + user.getId());
					pmsUserDao.update(user);
				}
				map.put("user", user);
				map.put("msg", "绑定成功");
			}
			if (bind.getPhoneStatus().equals("register") && bind.getThirdStatus() == 0) {// 更新phone的账号,绑定第三方
				user.setTelephone(bind.getTelephone());
				user = pmsUserDao.findUserByAttr(user);
				if (null == user.getUserName() || "".equals(user.getUserName())) {
					user.setUserName(bind.getUserName());
				}
				if (null == user.getImgUrl() || "".equals(user.getImgUrl())) {
					user.setImgUrl(bind.getImgUrl());
				}
				if (bind.getType().equals("qq")) {
					user.setQqUnique(bind.getUnique());
				} else if (bind.getType().equals("wechat")) {
					user.setWechatUnique(bind.getUnique());
				} else if (bind.getType().equals("wb")) {
					user.setWbUnique(bind.getUnique());
				}
				pmsUserDao.update(user);
				map.put("user", user);
				map.put("msg", "绑定成功");
			}
			if (bind.getPhoneStatus().equals("register") && bind.getThirdStatus() == 1) {// 提示手机号被占用
				map.put("code", "0");
				map.put("msg", "手机号被占用");
			}
		} else if (bind.getCode() == 1) {// 第三方用户存在
			if (bind.getPhoneStatus().equals("noregister")) {// 手机无注册,更新手机到第三方账户
				user.setUniqueId(bind.getUnique());
				user = pmsUserDao.verificationUserExistByThirdLogin(user).get(0);
				user.setTelephone(bind.getTelephone());
				pmsUserDao.update(user);
				map.put("msg", "绑定成功");
				map.put("user", user);
			}
			if (bind.getPhoneStatus().equals("register") && bind.getThirdStatus() == 0) {// 删除第三方账户,更新phone用户
				user.setUniqueId(bind.getUnique());
				user = pmsUserDao.verificationUserExistByThirdLogin(user).get(0);
				pmsUserDao.delete(user.getId());
				user = new PmsUser();
				user.setTelephone(bind.getTelephone());
				user = pmsUserDao.findUserByAttr(user);
				if (bind.getType().equals("qq")) {
					user.setQqUnique(bind.getUnique());
				} else if (bind.getType().equals("wechat")) {
					user.setWechatUnique(bind.getUnique());
				} else if (bind.getType().equals("wb")) {
					user.setWbUnique(bind.getUnique());
				}
				pmsUserDao.update(user);
				map.put("msg", "绑定成功");
				map.put("user", user);
			}
			if (bind.getPhoneStatus().equals("register") && bind.getThirdStatus() == 1) {// 提示手机号被占用
				map.put("code", "0");
				map.put("msg", "手机号被占用");
			}
		}
		return map;
	}

	public long modifyUserPassword(final PmsUser user) {
		final long ret = pmsUserDao.modifyUserPassword(user);
		return ret;
	}

	public long modifyUserLoginName(final PmsUser user) {
		return pmsUserDao.modifyUserLoginName(user);
	}

	public PmsUser findUserByAttr(final PmsUser user) {
		return pmsUserDao.findUserByAttr(user);
	}

	public PmsUser findUserByLoginNameAndPwd(final PmsUser user) {
		return pmsUserDao.findUserByLoginNameAndPwd(user);
	}

	public PmsUser findUserById(Long userId) {
		return pmsUserDao.findUserById(userId);
	}

	public boolean uniqueUserName(final PmsUser user) {
		if (null != user) {
			List<PmsUser> list = pmsUserDao.findUserByUserName(user);
			if (list.size() == 0) {
				return true;
			}
			if (list.size() == 1) {
				PmsUser u = list.get(0);
				if (null == u || u.getId().equals(user.getId())) {// 是自身
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public long modifyUserInfo(final PmsUser user) {
		return pmsUserDao.modifyUserInfo(user);
	}

	public long modifyUserPhoto(final PmsUser user) {
		return pmsUserDao.modifyUserPhoto(user);
	}

	public long modifyUserPhone(final PmsUser user) {
		return pmsUserDao.modifyUserPhone(user);
	}

	public Map<String, Object> thirdStatus(final PmsUser u) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qq", "0");
		map.put("wechat", "0");
		map.put("wb", "0");
		PmsUser user = pmsUserDao.findUserById(u.getId());
		if (null != user) {
			if (ValidateUtil.isValid(user.getQqUnique())) {
				map.put("qq", "1");
			}
			if (ValidateUtil.isValid(user.getWechatUnique())) {
				map.put("wechat", "1");
			}
			if (ValidateUtil.isValid(user.getWbUnique())) {
				map.put("wb", "1");
			}
		}
		return map;
	}

	public boolean userInfoUnBind(final PmsUser user) {
		pmsUserDao.unBindThird(user);
		return true;
	}

	public DataGrid<PmsUser> listWithPagination(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsUserDao.listWithPagination(pageParam,paramMap);
	}

	public long update(final PmsUser user) {
		return pmsUserDao.update(user);
	}

	public long delete(long[] ids) {
		if (ids.length > 0) {
			for (final long id : ids) {
				pmsUserDao.delete(id);
			}
		} else {
			throw new RuntimeException("Delete User error ...");
		}
		return 0l;
	}

	public List<PmsUser> all() {
		return pmsUserDao.all();
	}

	public List<PmsUser> findUserByName(final PmsUser user) {
		return pmsUserDao.findUserByName(user);
	}

	public long findUnlevelUsers() {
		final long count = pmsUserDao.findUserByName();
		return count;
	}

	
	
}
