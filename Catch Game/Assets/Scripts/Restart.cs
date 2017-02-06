using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Restart : MonoBehaviour {
	//need to be public
	public void RestartGame(){
		SceneManager.LoadScene ("Main");
	}
}
