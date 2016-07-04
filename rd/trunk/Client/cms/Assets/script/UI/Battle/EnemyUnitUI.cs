﻿using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class EnemyUnitUI : MonoBehaviour 
{
	public Text unitName;
	public Text unitLevel;
	public LifeBarUI lifeBar;
	public int	slot = 0;

    public GameUnit Unit { get; set; }
    private UIBuffView buffView;
	//RectTransform rectTrans;

    void Start()
    {
        buffView = gameObject.GetComponent<UIBuffView>();
        buffView.Init();
		//rectTrans = transform as RectTransform;
    }

	public void	UpdateShow(GameUnit battleUnit)
	{
        lifeBar.LifeTarget = battleUnit;

		if (null == battleUnit || battleUnit.gameObject == null)
		{
			//gameObject.SetActive(false);
			//return;
		}
        buffView.SetTargetUnit(battleUnit);

		//Vector3 viewPosition = BattleCamera.Instance.CameraAttr.WorldToViewportPoint (battleUnit.gameObject.transform.position);
		//Vector3 screenPosition = UICamera.Instance.CameraAttr.ViewportToScreenPoint (viewPosition);
		//rectTrans.anchoredPosition = screenPosition;

		unitName.text = battleUnit.name;
        if (battleUnit.isBorn)
        {
		    lifeBar.value = battleUnit.curLife / (float)battleUnit.maxLife;
            battleUnit.isBorn = false;
        }
		unitLevel.text = battleUnit.pbUnit.level.ToString ();
	}
}
