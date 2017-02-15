package com.paipianwang.pat.facade.user.service.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.paipianwang.pat.common.core.dao.impl.BaseDaoImpl;
import com.paipianwang.pat.facade.user.entity.PmsUser;
import com.paipianwang.pat.facade.user.service.dao.PmsUserDao;
@Repository
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {

	public static final String SQL_VALIDATION_PHONE= "validationPhone";
	public static final String SQL_SAVE= "save";
	public static final String SQL_VERIFICATION_USER_EXIST_BY_THIRDLOGIN= "verificationUserExistByThirdLogin";
	public static final String SQL_FIND_USER_BYID= "findUserById";
	public static final String SQL_FIND_USER_BYPHONE= "findUserByPhone";
	public static final String SQL_SAVE_BY_THIRD_LOGIN= "saveByThirdLogin";
	public static final String SQL_FIND_USER_BY_ATTR= "findUserByAttr";
	public static final String SQL_DELETE = "delete";
	public static final String SQL_MODIFY_USER_PASSWORD = "modifyUserPassword";
	public static final String SQL_MODIFY_USER_LOGINNAME = "modifyUserLoginName";
	public static final String SQL_FIND_USER_BY_LOGINNAME_PWD = "findUserByLoginNameAndPwd";
	public static final String SQL_FIND_USER_BY_USERNAME = "findUserByUserName";
	public static final String SQL_MODIFY_USER_INFO = "modifyUserInfo";
	public static final String SQL_MODIFY_USER_PHOTO = "modifyUserPhoto";
	public static final String SQL_MODIFY_USER_PHONE = "modifyUserPhone";
	public static final String SQL_UNBIND_THIRD = "unBindThird";
	@Autowired
	private SqlSessionTemplate sessionTemplate = null;

	
	
	@Override
	public int validationPhone(final Map<String, Object> paramMap) {
		return sessionTemplate.selectOne(getStatement(SQL_VALIDATION_PHONE), paramMap);
	}



	@Override
	public long save(final PmsUser user) {
		return sessionTemplate.insert(getStatement(SQL_SAVE), user);
	}



	@Override
	public List<PmsUser> verificationUserExistByThirdLogin(final PmsUser user) {
		return sessionTemplate.selectList(getStatement(SQL_VERIFICATION_USER_EXIST_BY_THIRDLOGIN), user);
	}



	@Override
	public PmsUser findUserById(final Long id) {
		return sessionTemplate.selectOne(getStatement(SQL_FIND_USER_BYID), id);
	}



	@Override
	public PmsUser findUserByPhone(final String telephone) {
		return sessionTemplate.selectOne(getStatement(SQL_FIND_USER_BYPHONE), telephone);
	}



	@Override
	public long saveByThirdLogin(final PmsUser user) {
		return sessionTemplate.insert(getStatement(SQL_SAVE_BY_THIRD_LOGIN), user);
	}



	@Override
	public PmsUser findUserByAttr(final PmsUser user) {
		return sessionTemplate.selectOne(getStatement(SQL_FIND_USER_BY_ATTR), user);
	}



	@Override
	public long delete(final Long id) {
		return sessionTemplate.delete(getStatement(SQL_DELETE), id);
	}



	@Override
	public long modifyUserPassword(final PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_MODIFY_USER_PASSWORD), user);
	}



	@Override
	public long modifyUserLoginName(final PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_MODIFY_USER_LOGINNAME), user);
	}



	@Override
	public PmsUser findUserByLoginNameAndPwd(final PmsUser user) {
		return sessionTemplate.selectOne(getStatement(SQL_FIND_USER_BY_LOGINNAME_PWD), user);
	}



	@Override
	public List<PmsUser> findUserByUserName(final PmsUser user) {
		return sessionTemplate.selectList(getStatement(SQL_FIND_USER_BY_USERNAME), user);
	}



	@Override
	public long modifyUserInfo(final PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_MODIFY_USER_INFO), user);
	}



	@Override
	public long modifyUserPhoto(final PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_MODIFY_USER_PHOTO), user);
	}



	@Override
	public long modifyUserPhone(final PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_MODIFY_USER_PHONE), user);
	}



	@Override
	public long unBindThird(PmsUser user) {
		return sessionTemplate.update(getStatement(SQL_UNBIND_THIRD), user);
	}

	
}
