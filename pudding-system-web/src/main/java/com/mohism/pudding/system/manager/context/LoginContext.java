package com.mohism.pudding.system.manager.context;




import com.mohism.pudding.core.util.HttpContext;
import com.mohism.pudding.core.util.SpringContextHolder;
import com.mohism.pudding.core.util.ToolUtil;
import com.mohism.pudding.kernel.model.api.AuthService;
import com.mohism.pudding.kernel.model.auth.AbstractLoginUser;
import com.mohism.pudding.kernel.model.auth.context.AbstractLoginContext;
import com.mohism.pudding.kernel.model.auth.context.LoginUserHolder;
import com.mohism.pudding.kernel.model.exception.ServiceException;
import com.mohism.pudding.kernel.model.exception.enums.CoreExceptionEnum;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录信息上下文
 *
 * @author real earth
 * @date 2019-06-30 16:58
 */
public class LoginContext implements AbstractLoginContext {

    private AuthService authService;

    public LoginContext(AuthService authService) {
        this.authService = authService;
    }

    public static LoginContext me() {
        return SpringContextHolder.getBean(LoginContext.class);
    }

    /**
     * 获取当前用户的token
     * <p>
     * 先判断header中是否有Authorization字段，
     * 如果header中没有这个字段，则检查请求参数中是否带token，
     * 如果任意一个地方有这个值，则返回这个值
     * 两个地方都没有token，则抛出没有登录用户异常
     */
    @Override
    public String getCurrentUserToken() {
        HttpServletRequest request = HttpContext.getRequest();
        if (request == null) {
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }

        //如果请求是在http环境下，则有request对象
        String authorization = request.getHeader("Authorization");
        if (ToolUtil.isNotEmpty(authorization)) {
            return authorization;
        } else {
            String token = request.getParameter("token");
            if (ToolUtil.isNotEmpty(token)) {
                return token;
            } else {
                throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
            }
        }
    }

    /**
     * 获取当前用户
     * <p>
     * 先从ThreadLocal中拿user，如果有值就直接返回，没取到再去调用远程服务,调用完远程服务把获取到的user放到Threadlocal里
     */
    @Override
    public <T extends AbstractLoginUser> T getLoginUser() {
        AbstractLoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return (T) currentUser;
        } else {
            String token = getCurrentUserToken();
            return (T) this.authService.getLoginUserByToken(token);
        }
    }

    /**
     * 获取当前登录用户的账户id
     */
    public Long getUserAccountId() {
        AbstractLoginUser abstractLoginUser = this.getLoginUser();
        if (abstractLoginUser == null) {
            return null;
        } else {
            return abstractLoginUser.getUserUniqueId();
        }
    }

}
