package com.zyfgoup.common.dto;

import com.zyfgoup.entity.Department;
import com.zyfgoup.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Zyfgoup
 * @Date 2020/7/28 15:56
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfo {
    private Employee employee;
    private Department department;
}
