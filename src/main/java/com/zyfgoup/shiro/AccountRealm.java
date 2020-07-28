package com.zyfgoup.shiro;

import cn.hutool.core.bean.BeanUtil;

import com.zyfgoup.entity.Admin;
import com.zyfgoup.entity.Department;
import com.zyfgoup.entity.Employee;
import com.zyfgoup.service.AdminService;
import com.zyfgoup.service.DepartmentService;
import com.zyfgoup.service.EmployeeService;
import com.zyfgoup.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AdminService adminService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //强制转换 会报错 通过工具类拷贝
        Object principal = principals.getPrimaryPrincipal();
        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(principal,accountProfile);

        //在认证的时候存的是只有id 和 role  取出来role放进去即可
        //System.out.println("授权:"+accountProfile);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(accountProfile.getRole());
        return simpleAuthorizationInfo;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;

        //有可能是部门id  也可能是admin id 也有可能是员工
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        //判断账号是否存在
        Department department = departmentService.getById(userId);
        if(department==null){
            Employee employee = employeeService.getById(userId);
            if(employee ==null){

               Admin admin = adminService.getById(Long.valueOf(userId));
                if(admin == null) {

                    throw new UnknownAccountException("用户不存在");
                }else{
                    //管理员

                    AccountProfile profile = new AccountProfile();
                    profile.setId(String.valueOf(admin.getId()));
                    profile.setRole(admin.getRole());

                    return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
                }
            }else{
                //员工
                System.out.println(employee);
                AccountProfile profile = new AccountProfile();
                profile.setId(String.valueOf(employee.getEId()));
                profile.setRole(employee.getRole());

                return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
            }

        }else{
            AccountProfile profile = new AccountProfile();
            profile.setId(department.getDepId());
            profile.setRole(department.getRole());
            return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
        }


    }
}
