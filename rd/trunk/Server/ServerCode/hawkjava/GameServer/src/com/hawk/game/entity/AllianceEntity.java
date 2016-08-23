package com.hawk.game.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hawk.db.HawkDBEntity;
import org.hibernate.annotations.GenericGenerator;


/**
 * @author zs
 *系统公会实体
 */
@Entity
@Table(name = "alliance")
public class AllianceEntity extends HawkDBEntity {
	@Id
	@GenericGenerator(name = "AUTO_INCREMENT", strategy = "native")
	@GeneratedValue(generator = "AUTO_INCREMENT")
	@Column(name = "id", unique = true)
	private int id = 0;
	
	@Column(name = "playerId")
	private int playerId = 0;
	
	@Column(name = "playerName")
	private String playerName = "";
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "level")
	private int level = 0;
	
	@Column(name = "memLevel")
	private int memLevel = 0;
	
	@Column(name = "expLevel")
	private int expLevel = 0;
	
	@Column(name = "coinLevel")
	private int coinLevel = 0;
	
	@Column(name = "contribution")
	private int contribution = 0;

	@Column(name = "contribution0")
	private int contribution0 = 0;
	
	@Column(name = "contribution1")
	private int contribution1 = 0;
	
	@Column(name = "contribution2")
	private int contribution2 = 0;
	
	@Column(name = "contribution3")
	private int contribution3 = 0;
	
	@Column(name = "notice")
	private String notice;
	
	@Column(name = "autoAccept")
	private boolean autoAccept = false;

	@Column(name = "minLevel")
	private int minLevel = 0;
	
	@Column(name = "refreshTime")
	protected int refreshTime = 0;
	
	@Column(name = "isDelete")
	private boolean isDelete = false;
	
	@Column(name = "createTime", nullable = false)
	protected int createTime = 0;
	
	@Column(name = "updateTime")
	protected int updateTime;
	
	/**
	 * 0:正常,1:为删除状态
	 */
	@Column(name = "invalid")
	protected boolean invalid;
	
	/**
	 * 成员列表
	 */
	@Transient
	private Map<Integer, PlayerAllianceEntity> memberList;
	
	/**
	 * 申请列表
	 */
	@Transient
	private Map<Integer, AllianceApplyEntity> applyList;

	/**
	 * 组队列表
	 */
	@Transient
	private Map<Integer, Integer> playerTeamMap;
	
	/**
	 * 组队列表
	 */
	@Transient
	private Map<Integer, AllianceTeamEntity> finishTeamList;
	
	/**
	 * 组队任务列表
	 */
	@Transient
	private Map<Integer, AllianceTeamEntity> unfinishTeamList;

	/**
	 * 刷新时间
	 */
	@Transient
	private int lastRefreshTime = 0;
	
	public AllianceEntity() {
		memberList = new HashMap<Integer, PlayerAllianceEntity>();
		applyList = new TreeMap<Integer, AllianceApplyEntity>();
		playerTeamMap = new HashMap<Integer, Integer>();
		finishTeamList = new HashMap<>();
		unfinishTeamList = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMemLevel() {
		return memLevel;
	}

	public void setMemLevel(int memLevel) {
		this.memLevel = memLevel;
	}

	public int getExpLevel() {
		return expLevel;
	}

	public void setExpLevel(int expLevel) {
		this.expLevel = expLevel;
	}

	public int getCoinLevel() {
		return coinLevel;
	}

	public void setCoinLevel(int coinLevel) {
		this.coinLevel = coinLevel;
	}

	public int getContribution() {
		return contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public void addContribution(int contribution) {
		this.contribution += contribution;
		this.contribution0 += contribution;
	}

	public int getContribution0() {
		return contribution0;
	}

	public void setContribution0(int contribution0) {
		this.contribution0 = contribution0;
	}

	public int getContribution1() {
		return contribution1;
	}

	public void setContribution1(int contribution1) {
		this.contribution1 = contribution1;
	}

	public int getContribution2() {
		return contribution2;
	}

	public void setContribution2(int contribution2) {
		this.contribution2 = contribution2;
	}

	public int getContribution3() {
		return contribution3;
	}

	public void setContribution3(int contribution3) {
		this.contribution3 = contribution3;
	}

	public int get3DaysContribution()
	{
		return contribution1 + contribution2 + contribution3;
	}
	
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public void setMemberList(HashMap<Integer, PlayerAllianceEntity> memberList) {
		this.memberList = memberList;
	}

	public void setApplyList(ConcurrentHashMap<Integer, AllianceApplyEntity> applyList) {
		this.applyList = applyList;
	}

	public Map<Integer, PlayerAllianceEntity> getMemberList() {
		return memberList;
	}

	public void addMember(int memberId, PlayerAllianceEntity playerAlliance) {
		if (memberId != 0 && playerAlliance != null) {
			memberList.put(memberId, playerAlliance);
		}
	}

	public void removeMember(int memberId) {
		memberList.remove(memberId);
	}

	public PlayerAllianceEntity getMember(int memberId) {
		return memberList.get(memberId);
	}
	
	public Map<Integer, AllianceApplyEntity> getApplyList() {
		return applyList;
	}
	
	public void addApply(AllianceApplyEntity apply){
		applyList.put(apply.getPlayerId(), apply);
	}
	
	public AllianceApplyEntity removeApply(int playerId){
		return	applyList.remove(playerId);
	}

	public Map<Integer, AllianceTeamEntity> getFinishTeamList() {
		return finishTeamList;
	}
	
	public Map<Integer, AllianceTeamEntity> getUnfinishTeamList() {
		return unfinishTeamList;
	}
	
	/**
	 * 获取玩家所在的队伍
	 * @param playerId
	 * @return
	 */
	public AllianceTeamEntity getTeamEntity(int playerId) {
		AllianceTeamEntity teamEntity = null;
		if (playerTeamMap.get(playerId) != null) {
			teamEntity = unfinishTeamList.get(playerTeamMap.get(playerId));
			if (teamEntity == null) {
				teamEntity = finishTeamList.get(playerTeamMap.get(playerId));
			}
		}
		return teamEntity;
	}

	public void addAllianceTeamEntity(AllianceTeamEntity teamEntity){
		if (teamEntity.isTaskFinish() == true) {
			finishTeamList.put(teamEntity.getId(), teamEntity);
		}
		else{
			unfinishTeamList.put(teamEntity.getId(), teamEntity);
		}
		
		if (teamEntity.getCaptain() != 0) {
			addPlayerTeamMap(teamEntity.getCaptain(), teamEntity.getId());
		}
		if (teamEntity.getMember1() != 0) {
			addPlayerTeamMap(teamEntity.getMember1(), teamEntity.getId());
		}
		if (teamEntity.getMember2() != 0) {
			addPlayerTeamMap(teamEntity.getMember2(), teamEntity.getId());
		}
		if (teamEntity.getMember3() != 0) {
			addPlayerTeamMap(teamEntity.getMember3(), teamEntity.getId());
		}
	}
	
	public void addPlayerTeamMap(int playerId, int teamId){
		playerTeamMap.put(playerId, teamId);
	}

	public void removePlayerTeamMap(int playerId){
		playerTeamMap.remove(playerId);
	}
	
	public Map<Integer, Integer> getPlayerTeamMap(){
		return playerTeamMap;
	}
	
	public void setTaskFinish(int taskId){
		AllianceTeamEntity teamEntity = unfinishTeamList.get(taskId);
		if (teamEntity != null) {
			unfinishTeamList.remove(taskId);
			finishTeamList.put(taskId, teamEntity);
		}
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public boolean isAutoAccept() {
		return autoAccept;
	}

	public void setAutoAccept(boolean autoAccept) {
		this.autoAccept = autoAccept;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public void dailyRefresh()
	{
		contribution3 = contribution2;
		contribution2 = contribution1;
		contribution1 = contribution0;
		contribution0 = 0;
	}
	
	public void refreshTeamEntity(int nowSeconds)
	{
		if (nowSeconds > lastRefreshTime + 60) {
			lastRefreshTime = nowSeconds;
			Iterator<Map.Entry<Integer, AllianceTeamEntity>> it = unfinishTeamList.entrySet().iterator();  
	        while(it.hasNext()){  
	            Map.Entry<Integer, AllianceTeamEntity> entry = it.next();  
	            AllianceTeamEntity teamEntity = entry.getValue();  
	            if(teamEntity.getCreateTime() + teamEntity.getOverTime() < nowSeconds){  
	            	it.remove();
	            	teamEntity.clearTeam();
	            }
	        }
		}
		
		
	}
	
	@Override 
	public int hashCode() {
		return Objects.hashCode(name);
	};
	
	@Override
	public int getCreateTime() {
		return createTime;
	}
	
	@Override
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public int getUpdateTime() {
		return updateTime;
	}
	
	@Override
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean isInvalid() {
		return invalid;
	}

	@Override
	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
	
	@Override
	public void notifyUpdate(boolean async) {
		super.notifyUpdate(async);
	}
}
