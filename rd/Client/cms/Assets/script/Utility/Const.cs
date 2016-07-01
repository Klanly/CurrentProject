﻿using UnityEngine;
using System.Collections;

public class Const
{
    /// <summary>
    /// 调试模式
    /// 开启此模式会直接读取streamingassets中的文件
    /// 不开启此模式会将streamingassets下面的文件复制到persistentDataPath中，并从persistentDataPath中读取数据
    /// </summary>
    public static bool DebugMode = true;
    /// <summary>
    /// 更新模式（开始此模式请确保DebugMode为false）
    /// 更新模式下会从web server上获取version list，然后下载更新包更新版本
    /// ios初审时请关闭
    /// </summary>
    public static bool UpdateMode = false;

    public static int TimerInterval = 1;
    //游戏帧频
    public static int GameFrameRate = 30;

    //更新地址
    public static string WebUrl = "http://localhost/versions/";
    public static string UpdateUrl
    {
        get
        {
            string url = WebUrl;
#if UNITY_IOS
            url += "ios/";
#else
            url += "android/";
#endif
            return url;
        }
    }

    //应用程序名称
    public static string AppName = "game"; 
    //应用程序前缀
    public static string AppPrefix = AppName + "_";
    //素材扩展名
    public static string ExtName = ".unity3d";
    //素材目录
    public static string AssetDirname = "assetbundle";
    //游戏管理器object名字
    public static string GameAppName = "GameApp";

    public static int SocketPort = 0;                           //Socket服务器端口
    public static string SocketAddress = string.Empty;          //Socket服务器地址
}
