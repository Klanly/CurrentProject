package com.hawk.game.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.hawk.app.HawkAppObj;
import org.hawk.app.HawkObjModule;
import org.hawk.config.HawkConfigManager;
import org.hawk.db.HawkDBManager;
import org.hawk.log.HawkLog;
import org.hawk.msg.HawkMsg;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.service.HawkServiceProxy;
import org.hawk.xid.HawkXID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawk.game.config.ItemCfg;
import com.hawk.game.entity.EquipEntity;
import com.hawk.game.entity.ItemEntity;
import com.hawk.game.entity.PlayerEntity;
import com.hawk.game.log.BehaviorLogger;
import com.hawk.game.log.BehaviorLogger.Action;
import com.hawk.game.log.BehaviorLogger.Params;
import com.hawk.game.log.BehaviorLogger.Source;
import com.hawk.game.module.PlayerIdleModule;
import com.hawk.game.module.PlayerInstanceModule;
import com.hawk.game.module.PlayerLoginModule;
import com.hawk.game.module.PlayerMonsterModule;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HS;
import com.hawk.game.protocol.SysProtocol.HSErrorCode;
import com.hawk.game.util.ConfigUtil;
import com.hawk.game.util.EquipUtil;
import com.hawk.game.util.GsConst;

/**
 * 玩家对象
 * 
 * @author hawk
 * 
 */
public class Player extends HawkAppObj {
	/**
	 * 协议日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger("Protocol");

	/**
	 * 挂载玩家数据管理集合
	 */
	private PlayerData playerData;

	/**
	 * 组装状态
	 */
	private boolean assembleFinish;

	/**
	 * 构造函数
	 * 
	 * @param xid
	 */
	public Player(HawkXID xid) {
		super(xid);

		initModules();

		playerData = new PlayerData(this);
	}

	/**
	 * 初始化模块
	 * 
	 */
	public void initModules() {
		registerModule(GsConst.ModuleType.LOGIN_MODULE, new PlayerLoginModule(this));
		registerModule(GsConst.ModuleType.MONSTER_MODULE, new PlayerMonsterModule(this));
		registerModule(GsConst.ModuleType.INSTANCE_MODULE, new PlayerInstanceModule(this));

		// 最后注册空闲模块, 用来消息收尾处理
		registerModule(GsConst.ModuleType.IDLE_MODULE, new PlayerIdleModule(this));
	}

	/**
	 * 获取玩家数据
	 * 
	 * @return
	 */
	public PlayerData getPlayerData() {
		return playerData;
	}

	/**
	 * 获取玩家实体
	 * 
	 * @return
	 */
	public PlayerEntity getEntity() {
		return playerData.getPlayerEntity();
	}

	/**
	 * 是否组装完成
	 * 
	 * @return
	 */
	public boolean isAssembleFinish() {
		return assembleFinish;
	}

	/**
	 * 设置组装完成状态
	 * 
	 * @param assembleFinish
	 */
	public void setAssembleFinish(boolean assembleFinish) {
		this.assembleFinish = assembleFinish;
	}

	/**
	 * 通知错误码
	 * 
	 * @param errCode
	 */
	public void sendError(int hpCode, int errCode) {
		HSErrorCode.Builder builder = HSErrorCode.newBuilder();
		builder.setHpCode(hpCode);
		builder.setErrCode(errCode);
		sendProtocol(HawkProtocol.valueOf(HS.sys.ERROR_CODE, builder));
	}

	/**
	 * 通知错误码
	 * 
	 * @param errCode
	 */
	public void sendError(int hpCode, int errCode, int errFlag) {
		HSErrorCode.Builder builder = HSErrorCode.newBuilder();
		builder.setHpCode(hpCode);
		builder.setErrCode(errCode);
		builder.setErrFlag(errFlag);
		sendProtocol(HawkProtocol.valueOf(HS.sys.ERROR_CODE, builder));
	}

	/**
	 * 发送协议
	 * 
	 * @param protocol
	 * @return
	 */
	@Override
	public boolean sendProtocol(HawkProtocol protocol) {
		if (protocol.getSize() >= 2048) {
			logger.info("send protocol size overflow, protocol: {}, size: {}", new Object[] { protocol.getType(), protocol.getSize() });
		}
		return super.sendProtocol(protocol);
	}

	/**
	 * 踢出玩家
	 * @param reason
	 */
	public void kickout(int reason) {
		session = null;
		saveRoleData();
	}
	/**
	 * 玩家消息预处理
	 * 
	 * @param msg
	 * @return
	 */
	private boolean onPlayerMessage(HawkMsg msg) {
		// 优先服务拦截
		if (HawkServiceProxy.onMessage(this, msg)) {
			return true;
		}

		// 系统级消息, 所有模块都进行处理的消息
		if (msg.getMsg() == GsConst.MsgType.PLAYER_LOGIN) {
			for (Entry<Integer, HawkObjModule> entry : objModules.entrySet()) {
				PlayerModule playerModule = (PlayerModule) entry.getValue();
				playerModule.onPlayerLogin();
			}
			return true;
		} else if (msg.getMsg() == GsConst.MsgType.PLAYER_ASSEMBLE) {
			for (Entry<Integer, HawkObjModule> entry : objModules.entrySet()) {
				PlayerModule playerModule = (PlayerModule) entry.getValue();
				playerModule.onPlayerAssemble();
			}
			return true;
		} else if (msg.getMsg() == GsConst.MsgType.SESSION_CLOSED) {
			if (isAssembleFinish()) {
				for (Entry<Integer, HawkObjModule> entry : objModules.entrySet()) {
					PlayerModule playerModule = (PlayerModule) entry.getValue();
					playerModule.onPlayerLogout();
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 玩家协议预处理
	 * 
	 * @param protocol
	 * @return
	 */
	private boolean onPlayerProtocol(HawkProtocol protocol) {
		// 优先服务拦截
		if (HawkServiceProxy.onProtocol(this, protocol)) {
			return true;
		}

		// 玩家不在线而且不是登陆协议(非法协议时机)
		if (!isOnline() && !protocol.checkType(HS.code.LOGIN_C)) {
			HawkLog.errPrintln(String.format("player is offline, session: %s, protocol: %d", protocol.getSession().getIpAddr(), protocol.getType()));
			return true;
		}

		// 玩家未组装完成
		if (!isAssembleFinish() && !protocol.checkType(HS.code.LOGIN_C)) {
			HawkLog.errPrintln(String.format("player assemble unfinish, session: %s, protocol: %d", protocol.getSession().getIpAddr(), protocol.getType()));
			return true;
		}

		return false;
	}

	/**
	 * 帧更新
	 */
	@Override
	public boolean onTick() {
		// 玩家未组装完成直接不走时钟tick机制
		if (!isAssembleFinish()) {
			return true;
		}
		return super.onTick();
	}

	/**
	 * 消息响应
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public boolean onMessage(HawkMsg msg) {
		if (onPlayerMessage(msg)) {
			return true;
		}
		return super.onMessage(msg);
	}

	/**
	 * 协议响应
	 * 
	 * @param protocol
	 * @return
	 */
	@Override
	public boolean onProtocol(HawkProtocol protocol) {
		if (onPlayerProtocol(protocol)) {
			return true;
		}
		return super.onProtocol(protocol);
	}

	/**
	 * 获取玩家id
	 * 
	 * @return
	 */
	public int getId() {
		return playerData.getPlayerEntity().getId();
	}

	/**
	 * 获取puid
	 * 
	 * @return
	 */
	public String getPuid() {
		return playerData.getPlayerEntity().getPuid();
	}

	/**
	 * 获取设备
	 * 
	 * @return
	 */
	public String getDevice() {
		return playerData.getPlayerEntity().getDevice();
	}

	/**
	 * 获取平台
	 * 
	 * @return
	 */
	public String getPlatform() {
		return playerData.getPlayerEntity().getPlatform();
	}

	/**
	 * 获取手机信息
	 * @return
	 */
	public String getPhoneInfo() {
		return playerData.getPlayerEntity().getPhoneInfo();
	}

	/**
	 * 获取钻石
	 * 
	 * @return
	 */
	public int getGold() {
		return 0;
	}

	/**
	 * 获取金币
	 * 
	 * @return
	 */
	public long getCoin() {
		return playerData.getPlayerEntity().getCoin();
	}

	/**
	 * 获取玩家vip等级
	 * 
	 * @return
	 */
	public int getVipLevel() {
		return playerData.getPlayerEntity().getVipLevel();
	}

	/**
	 * 获取玩家名字
	 * 
	 * @return
	 */
	public String getName() {
		return "";
	}

	/**
	 * 获取玩家等级
	 * 
	 * @return
	 */
	public int getLevel() {
		return 0;
	}

	/**
	 * 获取经验
	 * @return
	 */
	public int getExp() {
		return 0;
	}

	/**
	 * 获取会话ip地址
	 * 
	 * @return
	 */
	public String getIp() {
		if (session != null) {
			return session.getIpAddr();
		}
		return null;
	}
	
	/**
	 * 增加钻石
	 * 
	 * @param gold
	 * @param action
	 */
	public void increaseGold(int gold, Action action) {
		if (gold <= 0) {
			throw new RuntimeException("increaseGold");
		}

		playerData.getPlayerEntity().setGold(playerData.getPlayerEntity().getGold() + gold);
		playerData.getPlayerEntity().notifyUpdate(true);

		BehaviorLogger.log4Service(this, Source.PLAYER_ATTR_CHANGE, action, 
				Params.valueOf("playerAttr", Const.playerAttr.GOLD_VALUE), 
				Params.valueOf("add", gold), 
				Params.valueOf("after", getGold()));
		
		
		BehaviorLogger.log4Platform(this, action, Params.valueOf("playerAttr", Const.playerAttr.GOLD_VALUE), 
				Params.valueOf("add", gold), 
				Params.valueOf("after", getGold()));
	}

	/**
	 * 消耗钻石
	 * 
	 * @param gold
	 * @param action
	 */
	public void consumeGold(int gold, Action action) {
		if (gold <= 0 || gold > getGold()) {
			throw new RuntimeException("consumeGold");
		}

		playerData.getPlayerEntity().setGold(playerData.getPlayerEntity().getGold() - gold);
		playerData.getPlayerEntity().notifyUpdate(true);

		BehaviorLogger.log4Service(this, Source.PLAYER_ATTR_CHANGE, action, 
				Params.valueOf("playerAttr", Const.playerAttr.GOLD_VALUE), 
				Params.valueOf("sub", gold), 
				Params.valueOf("after", getGold()));
		
		BehaviorLogger.log4Platform(this, Action.GOLD_COST, Params.valueOf("money", gold),
				Params.valueOf("wpnum", 1), Params.valueOf("price", gold),
				Params.valueOf("wpid", 0), Params.valueOf("wptype", action.name()));
	}

	/**
	 * 增加金币
	 * 
	 * @param coin
	 * @param action
	 */
	public void increaseCoin(int coin, Action action) {
		if (coin <= 0) {
			throw new RuntimeException("increaseCoin");
		}

		playerData.getPlayerEntity().setCoin(playerData.getPlayerEntity().getCoin() + coin);
		playerData.getPlayerEntity().notifyUpdate(true);

		BehaviorLogger.log4Service(this, Source.PLAYER_ATTR_CHANGE, action, 
				Params.valueOf("playerAttr", Const.playerAttr.COIN_VALUE), 
				Params.valueOf("add", coin), 
				Params.valueOf("after", getCoin()));
	}
	
	/**
	 * 消费金币
	 * 
	 * @param coin
	 * @param action
	 */
	public void consumeCoin(long coin, Action action) {
		if (coin <= 0 || coin > getCoin()) {
			throw new RuntimeException("consumeCoin");
		}

		playerData.getPlayerEntity().setCoin(playerData.getPlayerEntity().getCoin() - coin);
		playerData.getPlayerEntity().notifyUpdate(true);

		BehaviorLogger.log4Service(this, Source.PLAYER_ATTR_CHANGE, action, 
				Params.valueOf("playerAttr", Const.playerAttr.COIN_VALUE), 
				Params.valueOf("sub", coin), 
				Params.valueOf("after", getCoin()));
		
		BehaviorLogger.log4Platform(this, Action.COIN_COST, Params.valueOf("money", coin),
				Params.valueOf("wpnum", 1), Params.valueOf("price", coin),
				Params.valueOf("wpid", 0), Params.valueOf("wptype", action.name()));
	}
	
	/**
	 * 增加vip等级
	 * 
	 * @param level
	 */
	public void setVipLevel(int level, Action action) {
		if (level <= 0) {
			throw new RuntimeException("increaseLevel");
		}
	}

	
	/**
	 * 增加等级
	 * 
	 * @param level
	 */
	public void increaseLevel(int level, Action action) {
		if (level <= 0) {
			throw new RuntimeException("increaseLevel");
		}
	}
	
	/**
	 * 增加经验
	 * 
	 * @param exp
	 */
	public void increaseExp(int exp, Action action) {
		if (exp <= 0) {
			throw new RuntimeException("increaseExp");
		}

	}
	
	/**
	 * 增加物品
	 */
	public ItemEntity increaseTools(int itemId, int itemCount, Action action) {
		if(!ConfigUtil.check(Const.itemType.ITEM_VALUE, itemId)) {
			return null;
		}
		
		ItemEntity itemEntity = playerData.getItemByItemId(itemId);
		if (itemEntity == null) {
			itemEntity = new ItemEntity();
			itemEntity.setItemId(itemId);
			itemEntity.setCount(itemCount);
			itemEntity.setPlayerId(getId());
			if (HawkDBManager.getInstance().create(itemEntity)) {
				playerData.addItemEntity(itemEntity);
			}
		} else {
			itemEntity.setCount(itemEntity.getCount() + itemCount);
			itemEntity.notifyUpdate(true);
		}

		if (itemEntity.getId() > 0) {

			BehaviorLogger.log4Service(this, Source.TOOLS_ADD, action, 
					Params.valueOf("itemId", itemId), 
					Params.valueOf("id", itemEntity.getId()), 
					Params.valueOf("add", itemCount), 
					Params.valueOf("after", itemEntity.getCount()));
	
			return itemEntity;
		}
		return null;
	}

	/**
	 * 消耗物品
	 */
	public ItemEntity consumeTools(int itemId, int itemCount, Action action) {
		ItemEntity itemEntity = playerData.getItemByItemId(itemId);
		if (itemEntity != null && itemEntity.getCount() >= itemCount) {
			itemEntity.setCount(itemEntity.getCount() - itemCount);
			itemEntity.notifyUpdate(true);
			
			BehaviorLogger.log4Service(this, Source.TOOLS_REMOVE, action, 
					Params.valueOf("itemId", itemId), 
					Params.valueOf("id", itemEntity.getId()), 
					Params.valueOf("sub", itemCount), 
					Params.valueOf("after", itemEntity.getCount()));

			return itemEntity;
		}
		return null;
	}

	/**
	 * 增加装备
	 */
	public EquipEntity increaseEquip(int equipId, Action action) {
		return increaseEquip(equipId,0,0,action);
	}
	
	/**
	 * 增加装备
	 */
	public EquipEntity increaseEquip(int equipId, int stage, int level, Action action) {
		if(!ConfigUtil.check(Const.itemType.EQUIP_VALUE, equipId)) {
			return null;
		}
		
		EquipEntity equipEntity = EquipUtil.generateEquip(this, equipId, stage, level);
		if (equipEntity != null) {
			if (HawkDBManager.getInstance().create(equipEntity)) {
				playerData.addEquipEntity(equipEntity);
	
				BehaviorLogger.log4Service(this, Source.EQUIP_ADD, action, 
						Params.valueOf("equipId", equipId), 
						Params.valueOf("id", equipEntity.getId()));
				return equipEntity;
			}
		}
		return null;
	}

	/**
	 * 消耗装备
	 */
	public boolean consumeEquip(long id, Action action) {
		EquipEntity equipEntity = playerData.getEquipById(id);
		if (equipEntity != null) {
			playerData.removeEquipEntity(equipEntity);
			equipEntity.delete();

			BehaviorLogger.log4Service(this, Source.EQUIP_REMOVE, action, 
					Params.valueOf("equipId", equipEntity.getItemId()), 
					Params.valueOf("id", equipEntity.getId()));

			return true;
		}
		return false;
	}

	/**
	 * 批量消耗装备
	 * 
	 * @return 消耗失败的装备Id
	 */
	public List<Integer> consumeEquip(List<Integer> ids, Action action) {
		List<Integer> removeFailEquipIds = new LinkedList<>();
		for (Integer id : ids) {
			if (!consumeEquip(id, action)) {
				removeFailEquipIds.add(id);
			}
		}
		return removeFailEquipIds;
	}

	
	/**
	 * 角色数据落地
	 *
	 */
	 public void saveRoleData(){	 

	 }
	
	/**
	 * 重新选择角色
	 *
	 */	 
	 public void ReselectRole() {

	}
}
