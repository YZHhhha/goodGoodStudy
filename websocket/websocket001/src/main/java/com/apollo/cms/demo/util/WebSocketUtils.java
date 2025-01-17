package com.apollo.cms.demo.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import net.sf.jsqlparser.expression.JsonFunction;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xin.altitude.cms.common.util.JacksonUtils;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author <a href="http://www.altitude.xin" target="_blank">Java知识图谱</a>
 * @author <a href="https://gitee.com/decsa/ucode-cms-vue" target="_blank">UCode CMS</a>
 * @author <a href="https://space.bilibili.com/1936685014" target="_blank">B站视频</a>
 **/
public class WebSocketUtils {
    /**
     * 定义存储session的容器
     */
    private final static CopyOnWriteArraySet<WebSocketSession> SESSION_SETS = new CopyOnWriteArraySet<>();
    
    private WebSocketUtils() {
    }
    
    /**
     * 添加会话
     */
    public static void add(WebSocketSession session) {
        SESSION_SETS.add(session);
    }
    
    /**
     * 移除会话
     */
    public static void remove(WebSocketSession session) {
        SESSION_SETS.remove(session);
    }
    
    /**
     * （群）发送消息
     */
    public static void sendMessage(Object msg) {
        TextMessage textMessage = new TextMessage(JacksonUtils.writeValueAsString(msg));
        try {
            for (WebSocketSession session : SESSION_SETS) {
                System.out.println(JacksonUtils.writeValueAsString(msg));
                session.sendMessage(textMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
