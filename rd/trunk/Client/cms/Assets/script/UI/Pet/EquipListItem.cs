﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;

public interface IClickUsedEquip
{
    void OnSelectEquip(EquipData equip);
    void OnUsedEquip(EquipData equip);
}

public class EquipListItem : MonoBehaviour
{
    public Transform transIcon;
    private ItemIcon equipIcon;
    public Text textName;
    public Text textType;
    public Text textZhanli;
    public Text textDengji;

    public Text textZhanli_lang;
    public Text textDengji_lang;

    public Button btnSelect;
    public Button btnUsed;

    public IClickUsedEquip ClickDelegate;

    public class EquipInfo
    {
        public ItemStaticData itemInfo;
        public EquipProtoData equipInfo;
        public EquipData equipData;
    }
    EquipInfo info=new EquipInfo();

    public Transform content;
    [HideInInspector]
    public List<EquipGemItem> items = new List<EquipGemItem>();
    [HideInInspector]
    public List<EquipGemItem> itemPool = new List<EquipGemItem>();

    void Start()
    {
        textDengji_lang.text = StaticDataMgr.Instance.GetTextByID("equip_List_xianzhidengji");
        textZhanli_lang.text = StaticDataMgr.Instance.GetTextByID("equip_forge_zhanli");
        btnUsed.GetComponentInChildren<Text>().text = StaticDataMgr.Instance.GetTextByID("equip_List_zhuangbei");

        ScrollViewEventListener.Get(btnSelect.gameObject).onClick = OnClickSelectEquip;
        ScrollViewEventListener.Get(btnUsed.gameObject).onClick = OnClickUsedEquip;
    }

    public void ReloadData(EquipData data)
    {
        info.equipData = data;
        info.equipInfo = StaticDataMgr.Instance.GetEquipProtoData(data.equipId, data.stage);
        info.itemInfo = StaticDataMgr.Instance.GetItemData(data.equipId);

        if (equipIcon == null)
        {
            equipIcon = ItemIcon.CreateItemIcon(info.equipData,false);
			UIUtil.SetParentReset(equipIcon.transform,transIcon);
        }
        else
        {
            equipIcon.RefreshWithEquipInfo(info.equipData,false);
        }
        UIUtil.SetStageColor(textName, info.itemInfo.name, info.equipData.stage);
        //TODO:设置战力
        //textZhanli.text=StaticDataMgr.Instance.GetTextByID(itemInfo.z)
        textDengji.text = info.itemInfo.minLevel.ToString();
        UIUtil.SetEquipType(textType, info.itemInfo.subType);

        RefreshGem(data.gemList);
    }

    void RefreshGem(List<GemInfo> gems)
    {
        RemoveAllElement();
        foreach (var info in gems)
        {
            EquipGemItem item = GetElement();
            item.Refresh(info);
            item.transform.SetAsLastSibling();
        }
    }

    public EquipGemItem GetElement()
    {
        EquipGemItem item = null;
        if (itemPool.Count <= 0)
        {
            GameObject go = ResourceMgr.Instance.LoadAsset("EquipGemItem");
            if (go != null)
            {
                go.transform.localScale = Vector3.one;
                go.transform.SetParent(content, false);
                item = go.GetComponent<EquipGemItem>();
            }
        }
        else
        {
            item = itemPool[itemPool.Count - 1];
            item.gameObject.SetActive(true);
            itemPool.Remove(item);
        }
        items.Add(item);
        return item;
    }
    public void RemoveElement(EquipGemItem item)
    {
        if (items.Contains(item))
        {
            item.gameObject.SetActive(false);
            items.Remove(item);
            itemPool.Add(item);
        }
    }
    public void RemoveAllElement()
    {
        items.ForEach(delegate(EquipGemItem item) { item.gameObject.SetActive(false); });
        itemPool.AddRange(items);
        items.Clear();
    }

    void OnClickSelectEquip(GameObject go)
    {
        ClickDelegate.OnSelectEquip(info.equipData);
    }

    void OnClickUsedEquip(GameObject go)
    {
        ClickDelegate.OnUsedEquip(info.equipData);
    }

}
