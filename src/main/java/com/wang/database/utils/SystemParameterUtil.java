package com.wang.database.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2022/10/19
 * @change 2022/10/19 by wangxiaofei for init
 **/
@Order(-3)
@Configuration
@Component("systemParameterUtil")
public class SystemParameterUtil {
    private static final Logger log = LoggerFactory.getLogger(SystemParameterUtil.class);
    private static String applicationVersion;
    private static String applicationDesc;
    private static String redisHost = null;
    private static Integer redisPort = null;
    private static String redisPassword = null;
    private static Integer redisTimeout = null;
    private static String DEFAULT_REDIS_HOST = "127.0.0.1";
    private static Integer DEFAULT_REDIS_PORT = 6379;
    private static Integer DEFAULT_REDIS_TIMEOUT = 9999;
    private static String DEFAULT_TIME_ZONE = "GMT+8";
    private static String APPLICATION_DESC_PARAMETER_NAME = "@project.description@";
    private static String APPLICATION_VERSION_PARAMETER_NAME = "@project.version@";
    private static String defaultTimeZone;
    private static String applicationName = null;
    private static String applicationPort = null;
    private static String contextPath = null;
    private static int threadPoolCorePoolSize;
    private static int threadPoolMaxPoolSize;
    private static int threadPoolQueueCapacity;
    private static int threadPoolKeepAliveSeconds;
    private static String errorRedirectUrl;
    private static boolean loadingTrackLogPrintFlag = false;
    private static int CORE_POOL_SIZE = 50;
    private static int MAX_POOL_SIZE = 200;
    private static int QUEUE_CAPACITY = 1000;
    private static int KEEP_ALIVE_SECONDS = 300;
    private static String ERROR_REDIRECT_URL = "";

    public SystemParameterUtil() {
    }

    public static Integer getRedisTimeout() {
        return redisTimeout != null ? redisTimeout : DEFAULT_REDIS_TIMEOUT;
    }

    @Value("${spring.application.name:null}")
    private void setApplicationName(String applicationName) {
        SystemParameterUtil.applicationName = applicationName;
        refreshHostName();
    }

    public static String getApplicationName() {
        return applicationName;
    }

    @Value("${server.servlet.context-path:}")
    private void setContextPath(String contextPath) {
        SystemParameterUtil.contextPath = contextPath;
    }

    public static String getContextPath() {
        return contextPath;
    }

    @Value("${server.port:null}")
    private void setApplicationPort(String applicationPort) {
        SystemParameterUtil.applicationPort = applicationPort;
        refreshHostName();
    }
    public static void refreshHostName() {
        String localhostName = System.getenv("LOCALHOST_NAME");
        if (StringUtils.isBlank(localhostName)) {
            localhostName = getApplicationIp();
        }

        String serviceName = "%s-%s";
        serviceName = String.format(serviceName, getApplicationName(), getApplicationPort());
        if (StringUtils.isNotBlank(localhostName)) {
            MDC.put("localhostName", localhostName);
            MDC.put("serviceName", serviceName);
        }

    }
    public static String getApplicationPort() {
        return applicationPort;
    }

    public static String getApplicationIp() {
        try {
            return HostInfoUtils.getLocalIP();
        } catch (SocketException var1) {
            var1.printStackTrace();
            return "unknown";
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
            return "unknown";
        }
    }

    @Value("${mcp.common.threadPool.corePoolSize:null}")
    private void setThreadPoolCorePoolSize(String threadPoolCorePoolSize) {
        if (!StringUtils.isBlank(threadPoolCorePoolSize) && !"null".equals(threadPoolCorePoolSize)) {
            SystemParameterUtil.threadPoolCorePoolSize = Integer.parseInt(threadPoolCorePoolSize);
        } else {
            SystemParameterUtil.threadPoolCorePoolSize = CORE_POOL_SIZE;
        }

    }

    public static int getThreadPoolCorePoolSize() {
        return threadPoolCorePoolSize;
    }

    @Value("${mcp.common.threadPool.maxPoolSize:null}")
    private void setThreadPoolMaxPoolSize(String threadPoolMaxPoolSize) {
        if (!StringUtils.isBlank(threadPoolMaxPoolSize) && !"null".equals(threadPoolMaxPoolSize)) {
            SystemParameterUtil.threadPoolMaxPoolSize = Integer.parseInt(threadPoolMaxPoolSize);
        } else {
            SystemParameterUtil.threadPoolMaxPoolSize = MAX_POOL_SIZE;
        }

    }

    public static int getThreadPoolMaxPoolSize() {
        return threadPoolMaxPoolSize;
    }

    @Value("${mcp.common.threadPool.queueCapacity:null}")
    private void setThreadPoolQueueCapacity(String threadPoolQueueCapacity) {
        if (!StringUtils.isBlank(threadPoolQueueCapacity) && !"null".equals(threadPoolQueueCapacity)) {
            SystemParameterUtil.threadPoolQueueCapacity = Integer.parseInt(threadPoolQueueCapacity);
        } else {
            SystemParameterUtil.threadPoolQueueCapacity = QUEUE_CAPACITY;
        }

    }

    public static int getThreadPoolQueueCapacity() {
        return threadPoolQueueCapacity;
    }

    @Value("${mcp.common.threadPool.keepAliveSeconds:null}")
    private void setThreadPoolKeepAliveSeconds(String threadPoolKeepAliveSeconds) {
        if (!StringUtils.isBlank(threadPoolKeepAliveSeconds) && !"null".equals(threadPoolKeepAliveSeconds)) {
            SystemParameterUtil.threadPoolKeepAliveSeconds = Integer.parseInt(threadPoolKeepAliveSeconds);
        } else {
            SystemParameterUtil.threadPoolKeepAliveSeconds = KEEP_ALIVE_SECONDS;
        }

    }

    public static String getApplicationVersion() {
        return applicationVersion;
    }

    @Value("${spring.application.version:}")
    public void setApplicationVersion(String applicationVersion) {
        if (APPLICATION_VERSION_PARAMETER_NAME.equals(applicationVersion)) {
            SystemParameterUtil.applicationVersion = "";
        } else {
            SystemParameterUtil.applicationVersion = applicationVersion;
        }

    }

    public static String getApplicationDesc() {
        return applicationDesc;
    }

    @Value("${spring.application.description:}")
    public void setApplicationDesc(String applicationDesc) {
        if (APPLICATION_DESC_PARAMETER_NAME.equals(applicationDesc)) {
            SystemParameterUtil.applicationDesc = "";
        } else {
            SystemParameterUtil.applicationDesc = applicationDesc;
        }

    }
}
