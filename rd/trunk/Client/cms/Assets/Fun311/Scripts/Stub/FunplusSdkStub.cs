﻿using UnityEngine;
using System.Collections;

namespace Funplus {

	public class FunplusSdkStub : BaseSdkWrapper {

		private static FunplusSdkStub instance;
		private static readonly object locker = new object ();

		private bool installed;

		public static FunplusSdkStub GetInstance () {
			if (instance == null) {
				lock (locker) {
					instance = new FunplusSdkStub ();
				}
			}
			return instance;
		}

		public override void SetGameObject (string gameObjectName) {
			Debug.Log ("Calling FunplusSdkStub.SetGameObject ().");
		}

		public override bool IsSdkInstalled () {
			return installed;
		}
		
		public override void Install (string gameId, string gameKey, string environment) {
			installed = true;
			FunplusSdk.GetInstance ().OnFunplusSdkInstallSuccess (null);
		}
		
		public override bool IsFirstLaunch () {
			return false;
		}

		public override void SetDebug (bool isDebug) {
		}
		
		public override void LogUserLogin (string uid) {
			Debug.Log ("Calling FunplusSdkStub.LogUserLogin ().");
		}
		
		public override void LogNewUser (string uid) {
			Debug.Log ("Calling FunplusSdkStub.LogNewUser ().");
		}
		
		public override void LogUserLogout () {
			Debug.Log ("Calling FunplusSdkStub.LogUserLogout ().");
		}
		
		public override void LogUserInfoUpdate (string serverId, string userId, string userName, string userLevel, string userVipLevel, bool isPaidUser) {
			Debug.Log ("Calling FunplusSdkStub.LogUserInfoUpdate ().");
		}
		
		public override void LogPayment (string productId, string throughCargo, string purchaseData) {
			Debug.Log ("Calling FunplusSdkStub.LogPayment ().");
		}
	}

}