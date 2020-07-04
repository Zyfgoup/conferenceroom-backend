package com.zyfgoup.service;

import com.zyfgoup.common.exception.SystemException;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/6/26 14:24
 * @Description
 */
public interface EmailService {

    boolean sendStartMail(String to, String subject, Map<String,Object> content) throws SystemException;

    boolean sendAuditMail(String to, String subject, Map<String,Object> content) throws SystemException;
}
