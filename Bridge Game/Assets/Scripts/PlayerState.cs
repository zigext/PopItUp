using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using LitJson;
using System.IO;

public class PlayerState : MonoBehaviour {

	public int score;
	public int lastScore;
	private bool gameOver;
	private string jsonString;



	void Start () {
		GetScore ();
	}

	void OnTriggerEnter2D(){
		GetScore();
	}

	void OnCollisionEnter2D(Collision2D collision){
		GetScore ();
	}

	void GetScore()
	{
		//get score from Score.cs that attached to HatSprite
		score = GameObject.Find ("Stone").GetComponent<StoneManager>().GetScore();
		//print ("player state score = " + score);
		//print (SaveToString ());
		FindLastScore();
	}

	public string SaveToString(){
		return JsonUtility.ToJson (this);
	} 

	void FindLastScore(){
		gameOver = GameObject.Find ("Stone").GetComponent<StoneManager> ().IsGameOver ();
		print (gameOver);
		if (gameOver) {
			lastScore = score;
			print ("last score = " + lastScore);
			//WriteJson ();
		}
	}

	public int getLastScore(){
		return lastScore;
	}

	void WriteJson(){
		//print (lastScore);
		//lastScoreObject = new LastScore(lastScore);
		//scoreJson = JsonMapper(lastScoreObject);
		//print(scoreJson);

		jsonString = JsonUtility.ToJson (this);
		File.WriteAllText (Application.dataPath + "/CatchScore.json", jsonString);
	}


}
