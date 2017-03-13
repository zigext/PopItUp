using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using LitJson;
using System.IO;

public class PlayerState : MonoBehaviour {

	public int score;
	private string scorejson;

	void Start () {
		score = GameObject.Find ("Stone").GetComponent<StoneManager> ().GetScore ();
		print ("score in player state = " + score);
		scorejson = JsonUtility.ToJson (this);
		print ("scorejson = " + scorejson);
		File.WriteAllText (Application.dataPath + "/BridgeScore.json", scorejson);
	}
}


