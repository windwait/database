package com.wang.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2022/10/19
 * @change 2022/10/19 by wangxiaofei for init
 **/
public class HostInfoUtils {
    private static final Logger log = LoggerFactory.getLogger(HostInfoUtils.class);
    private static String IP_VALUE_UNKNOWN = "unknown";

    public HostInfoUtils() {
    }

    public static String getLocalIP() throws UnknownHostException, SocketException {
        return isWindowsOS() ? InetAddress.getLocalHost().getHostAddress() : getLinuxLocalIp();
    }

    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }

        return isWindowsOS;
    }

    public static String getLocalHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";

        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            label43:
            while(true) {
                NetworkInterface intf;
                String name;
                do {
                    do {
                        if (!en.hasMoreElements()) {
                            break label43;
                        }

                        intf = (NetworkInterface)en.nextElement();
                        name = intf.getName();
                    } while(name.contains("docker"));
                } while(name.contains("lo"));

                Enumeration enumIpAddr = intf.getInetAddresses();

                while(enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                            ip = ipaddress;
                            log.info(ipaddress);
                        }
                    }
                }
            }
        } catch (SocketException var7) {
            log.error("获取ip地址异常", var7);
            ip = "127.0.0.1";
            var7.printStackTrace();
        }

        log.info("IP:" + ip);
        return ip;
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = null;
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || IP_VALUE_UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || IP_VALUE_UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || IP_VALUE_UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || IP_VALUE_UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("X-Real-IP");
        }

        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        if (ip == null || ip.length() == 0 || IP_VALUE_UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
