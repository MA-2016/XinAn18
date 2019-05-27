package com.mobileai.luncert.model.core;

public interface MatchResult {

    // 匹配成功，节点不是Scene节点
    public static final int MATCH_SUCCESS = 0;

    // 匹配成功，节点是Scene节点
    public static final int MATCH_SCENE_SUCCESS = 1;

    // 匹配失败，节点不是Scene节点
    public static final int MATCH_FAILED = 2;
    
    // 匹配失败，节点是Scene节点，应交由KnowledgeGraph学习发现未知病毒模式
    public static final int MATCH_SCENE_FAILED = 3;
    
}