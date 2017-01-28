using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Restart : MonoBehaviour {
	//need to be public
	public void RestartGame(){
		Application.LoadLevel (Application.loadedLevel);
	}
}
