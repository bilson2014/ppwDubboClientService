package com.paipianwang.pat.facade.user.service.dao;

import java.util.List;
import java.util.Map;

import com.paipianwang.pat.common.core.dao.BaseDao;
import com.paipianwang.pat.facade.user.entity.PmsUser;

/**
 * user表数据访问接口
 *
 */
public interface PmsUserDao extends BaseDao<PmsUser>{

	/**
	 * 检查手机号或者用户名是否存在
	 * 
	 * @param telephone
	 *            手机号码
	 *  @param loginName
	 *  		  登录名          
	 * @return 存在个数
	 */
	public int validationPhone(final Map<String, Object> paramMap);
	/**
	 * 注册用户
	 */
	public long save(PmsUser user);
	/**
	 * 验证 通过第三方登录的用户是否存在
	 */
	public List<PmsUser> verificationUserExistByThirdLogin(final PmsUser user);
	/**
	 * 根据用户ID获取用户
	 * 
	 * @param id
	 *            用户唯一编号
	 * @return 用户信息
	 */
	public PmsUser findUserById(final Long id);
	/**
	 * 根据用户手机号获取第三方用户信息
	 * 
	 * @param telephone
	 * @return 用户信息
	 */
	public PmsUser findUserByPhone(final String telephone);
	/**
	 * 保存通过第三方登录的用户信息
	 */
	public long saveByThirdLogin(final PmsUser user);
	/**
	 * 密码相同的前提下，通过用户名称获取用户
	 * 
	 * @param user
	 *            用户密码 以及 用户名称
	 * @return 用户信息
	 */
	public PmsUser findUserByAttr(final PmsUser user);
	/**
	 * 根据用户id删除用户
	 * @param id
	 * @return
	 */
	public long delete(final Long id);
	
	public long modifyUserPassword(final PmsUser user);
	
	public long modifyUserLoginName(final PmsUser user);
	/**
	 * 根据loginName和密码查询用户
	 */
	public PmsUser findUserByLoginNameAndPwd(final PmsUser user);
	
	/**
	 * 根据昵称查询用户
	 */
	public List<PmsUser> findUserByUserName(final PmsUser user);
	/**
	 * 修改 用户基本信息(昵称、性别、真实姓名、电子邮件、QQ)
	 */
	public long modifyUserInfo(final PmsUser user);
	/**
	 * 修改 用户头像
	 */
	public long modifyUserPhoto(final PmsUser user);
	/**
	 * 修改 用户手机号码
	 */
	public long modifyUserPhone(final PmsUser user);
	/**
	 * 用户个人资料页面解除绑定第三方
	 */
	public long unBindThird(final PmsUser user);
	
	public List<PmsUser> all();
	
	public List<PmsUser> findUserByName(final PmsUser user);
	
	public long findUserByName();
	
	public List<PmsUser> findUserByIds(List<Long> userIds);
	
	public List<PmsUser> getDroplist();

	
}
