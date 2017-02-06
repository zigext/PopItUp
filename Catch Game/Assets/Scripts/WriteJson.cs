using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using LitJson;
using System.IO;

public class WriteJson : MonoBehaviour {

	public int lastScore;
	JsonData scoreJson;

	void Start () {
		//lastScore = GameObject.Find ("Player").GetComponent<PlayerState> ().getLastScore ();
		//scoreJson = JsonMapper (lastScore);
	}

}
