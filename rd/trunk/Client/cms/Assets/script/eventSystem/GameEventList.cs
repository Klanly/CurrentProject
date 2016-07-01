using UnityEngine;
using System.Collections;

public class GameEventList
{
    //Login
    public static string LoginClick = "LoginClick";//no param

    //Build
    public static string BattleBtnClick = "BattleBtnClick";

    //////////////////////////////////////////////////////////////////////////
    //Battle
    public static string StartBattle = "StartBattle";  //param: PbStartBattle    

    //process
    public static string SwitchPet = "SwitchPet";//param <int,int>
	public static string ShowSwitchPetUI = "ShowSwitchPetUI";//param  int
	public static string HideSwitchPetUI = "HideSwitchPetUI";//no param
    
    //spell
    public static string FireSpell = "FireSpell";
    public static string LifeChange = "LifeChange";
    public static string EnergyChange = "EnergyChange";
    public static string UnitDead = "UnitDead";
    public static string Buff = "Buff";


    //////////////////////////////////////////////////////////////////////////

    //Data
    public static string LifeDataChanged = "LifeDataChanged"; //float


    public static string StaminaRepair = "StaminaRepair";//object

    //Ui
    public static string RestartGame = "RestartGame";
    public static string RefreshBattleResultUI = "RefreshBattleResultUI";//UIData
    public static string ResetOperHabit = "ResetOperHabit"; //设置操作习惯

    //Net
    public static string NetRequestState = "NetRequestState";//object
    public static string OnLoginMsg = "OnLoginMsg";

}
