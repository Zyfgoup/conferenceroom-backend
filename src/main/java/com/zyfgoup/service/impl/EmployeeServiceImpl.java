package com.zyfgoup.service.impl;

import com.zyfgoup.entity.Employee;
import com.zyfgoup.mapper.EmployeeMapper;
import com.zyfgoup.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-07-28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
