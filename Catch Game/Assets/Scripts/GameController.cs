using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class GameController : MonoBehaviour {

	public Camera cam;
	public GameObject[] stones;
	public Renderer rend;

	public float timeLeft;
	public Text timerText;
	public GameObject restartButton;
	public GameObject backButton;
	public GameObject confirmBack;
	public GameObject gameOverText;
	public GameObject startButton;
	public GameObject splashScreen;
	public PlayerController playerController;

	private float maxWidth;
	private bool playing;
	private float speed;
	private bool gameOver;

	// Use this for initialization
	void Start () {
		//Dont forget to add your camera and Player to PlayerController 
		if (cam == null) {
			cam = Camera.main;
		}
		gameOver = false;
		playing = false;
		speed = 1.0f;
		rend = stones[0].GetComponent<Renderer>();
			
		Vector3 upperCorner = new Vector3 (Screen.width, Screen.height, 0.0f);
		Vector3 targetWidth = cam.ScreenToWorldPoint(upperCorner);


		float stoneWidth = rend.bounds.extents.x;
		maxWidth = targetWidth.x - stoneWidth;
		StartCoroutine (Spawn ());
		UpdateText ();
	}

	void FixedUpdate(){
		if (playing) {
			timeLeft -= Time.deltaTime;
			if (timeLeft < 0)
				timeLeft = 0;
			UpdateText ();
		}
	}

	//create stones
	IEnumerator Spawn(){
		yield return new WaitForSeconds (2.0f); //wait 2 sec before the stones start falling
		playing = true;
		while (timeLeft > 0) {
			speed -= 0.1f;
			GameObject stone = stones [Random.Range (0, stones.Length)];
			Vector3 spawnPosition = new Vector3 (
				                        Random.Range (-maxWidth, maxWidth), //random x position from left to right
				                        transform.position.y, //y from position of stone
				                        0.0f //z
			                        );
			Quaternion spawnRotation = Quaternion.identity; //no rotation
			Instantiate (stone, spawnPosition, spawnRotation); //create stone
			if (timeLeft > 5)
				yield return new WaitForSeconds (Random.Range (1.0f, 2.0f)); //wait 
			else if (timeLeft <= 5) {
				yield return new WaitForSeconds (Time.deltaTime * speed); //last 5 sec
				//print (Time.deltaTime);
				//print ("speed = " + speed);
			}
		}
		gameOver = true;

		yield return new WaitForSeconds (2.0f);
		gameOverText.SetActive (true);
		yield return new WaitForSeconds (2.0f);
		restartButton.SetActive (true);
		backButton.SetActive (true);
		confirmBack.SetActive (true);
	}


	public void StartGame(){
			splashScreen.SetActive (false);
			startButton.SetActive (false);
			playerController.ToggleControl (true); //user can move player
		
	}

	void UpdateText(){
		timerText.text = "Time Left:\n" + Mathf.RoundToInt (timeLeft);
	}

	public bool IsGameOver(){
		return gameOver;
	}
		
}
