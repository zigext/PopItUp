using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using LitJson;
using System.IO;

public class PlayerState : MonoBehaviour {

	public int score;
	public int lastScore;
	//public LastScore lastScoreObject;
	private bool gameOver;
	private string jsonString;
	JsonData scoreJson = new JsonData();



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
		score = GameObject.Find ("Player").GetComponent<Score>().GetScore();
		//print ("player state score = " + score);
		//print (SaveToString ());
		FindLastScore();
	}

	public string SaveToString(){
		return JsonUtility.ToJson (this);
	} 

	void FindLastScore(){
		gameOver = GameObject.Find ("GameController").GetComponent<GameController> ().IsGameOver ();
		print (gameOver);
		if (gameOver) {
			lastScore = score;
			print ("last score = " + lastScore);
			WriteJson ();
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

/*public class LastScore{
	public int score;
	public LastScore(int lastScore){
		this.score = lastScore;
	}
}*/
		
