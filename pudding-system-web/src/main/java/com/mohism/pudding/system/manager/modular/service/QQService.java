package com.mohism.pudding.system.manager.modular.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.social.QQ;
import com.mohism.pudding.system.manager.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * <p>
 * qq登录接口业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-09
 */
public interface QQService extends IService<QQ> {

    /**
     * 通过openId获取
     * @param openId
     * @return
     */
    QQ findByOpenId(String openId);

    /**
     * 通过username获取
     * @param username
     * @return
     */
    QQ findByRelateUsername(String username);

    /**
     * 分页多条件获取
     * @param username
     * @param relateUsername
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<QQ> findByCondition(String username, String relateUsername, SearchVo searchVo, Pageable pageable);

    /**
     * 通过username删除
     * @param username
     */
    boolean deleteByUsername(String username);
}