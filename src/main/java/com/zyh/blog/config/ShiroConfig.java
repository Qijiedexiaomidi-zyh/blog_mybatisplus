package com.zyh.blog.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author ZYH
 * @Date 2020/9/10 17:30
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Configuration
public class ShiroConfig {

    /**
     * 1.注入UserRealm
     * @return
     */
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    /**
     * 	创建ShiroFilterFactoryBean过滤器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        //创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全会话管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*
         *	Shiro内置过滤器，可以实现权限相关的拦截器
         *	常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       roles: 该资源必须得到角色权限才可以访问
         */
        //创建Map集合，保存各种需要处理的请求
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        /**********************设置放行anon请求***************************/
        filterChainDefinitionMap.put("/css/**","anon"); //放行静态资源
        filterChainDefinitionMap.put("/images/**","anon"); //放行静态资源
        filterChainDefinitionMap.put("/js/**","anon"); //放行静态资源
        filterChainDefinitionMap.put("/lib/**","anon"); //放行静态资源
        filterChainDefinitionMap.put("/","anon"); //去前台首页，无需登录
        filterChainDefinitionMap.put("/admin", "anon"); //去后台登录界面
        filterChainDefinitionMap.put("/admin/login", "anon"); //登录操作
        filterChainDefinitionMap.put("/listAboutMe/**","anon"); //前台：《关于我》
        filterChainDefinitionMap.put("/listArchive/**","anon"); //前台：《归档》
        filterChainDefinitionMap.put("/listComment/**","anon"); //前台：《评论》
        filterChainDefinitionMap.put("/listNewBlog/**","anon"); //前台：《最新博客》
        filterChainDefinitionMap.put("/search/**","anon"); //前台：《普通搜索》
        filterChainDefinitionMap.put("/elasticsearch/**","anon"); //前台：《elasticsearch搜索》
        filterChainDefinitionMap.put("/blogView/**","anon"); //前台：《博客详情》
        filterChainDefinitionMap.put("/listTag/**","anon"); //前台：《标签》
        filterChainDefinitionMap.put("/listType/**","anon"); //前台：《类型》
        filterChainDefinitionMap.put("/admin/OssUpload","anon"); //测试oss对象存储
        /**********************设置身份验证authc请求***************************/


        //此配置放到最后
        filterChainDefinitionMap.put("/**", "authc");//所有请求必须进行登录

        //设置过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置登录页面请求（不设置默认去到login.jsp页面）
        shiroFilterFactoryBean.setLoginUrl("/admin");

        //返回shiroFilterFactoryBean
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager对象，关联Realm
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm realm) {
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联realm
        defaultWebSecurityManager.setRealm(realm);
        //返回DefaultWebSecurityManager
        return defaultWebSecurityManager;
    }

}
