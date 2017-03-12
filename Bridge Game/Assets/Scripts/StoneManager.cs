using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using LitJson;
using System.IO;

public class StoneManager : MonoBehaviour {
	private const float BOUNDS_SIZE = 3.5f;
	private const float STACK_MOVING_SPEED = 5.0f;
	private const float ERROR_MARGIN = 0.25f; //difference between current stone and the lat stone that we accept
	private const float STACK_BOUNDS_GAIN = 0.3f;
	private const int COMBO_START_GAIN = 3;

	private GameObject[] stones;
	private Vector2 stoneBounds = new Vector2 (BOUNDS_SIZE, BOUNDS_SIZE);

	private int score = 0; //in this game score = height
	private int stoneIndex;
	private int combo;

	private float stoneTransition = 0.0f;
	private float stoneSpeed = 2.5f;
	private float secondaryPosition;
	private bool isMovingOnX = true;
	private bool gameOver = false;
	private string jsonString;

	private string jsonTest;
	private PlayerState playerState;
	public GameObject obj; //object that holds PlayerState

	private Vector3 desiredPosition;
	private Vector3 lastStonePosition;

	public Text scoreText;
	public GameObject restartButton;
	public GameObject backButton;
	public GameObject confirmQuit;
    public Material[] newMaterial;
	public GUILayer gui;
	

	// Use this for initialization
	void Start () {
		stones = new GameObject[transform.childCount];
		for (int i = 0; i < transform.childCount; i++)
			stones [i] = transform.GetChild (i).gameObject; //get each stone
		stoneIndex = transform.childCount - 1;
		gui = Camera.main.GetComponent (typeof(GUILayer)) as GUILayer;


		
	}
	
	// Update is called once per frame
	void Update () {
		GUIElement element = gui.HitTest (Input.mousePosition);


		if(Input.GetMouseButtonDown(0)) {
			if(element == null){
			//if (Input.mousePosition.x >= 150 && Input.mousePosition.x <= 750) {

				if (PlaceStone ()) {
					SpawnStone ();
					score++;
					UpdateScore ();
					print ("Click");
				} else {
					EndGame ();
				}
			//}
			}
		}
		MoveStone ();
		// move stack
		transform.position = Vector3.Lerp(transform.position, desiredPosition,STACK_MOVING_SPEED * Time.deltaTime);
	}

	void MoveStone(){
        
		if (gameOver)
			return;
		
		stoneTransition += Time.deltaTime * stoneSpeed;

		if(isMovingOnX) //original is  stoneIndex].transform.localPosition = new Vector3 ( Mathf.Sin (stoneTransition) * BOUNDS_SIZE, score, secondaryPosition);
			stones [stoneIndex].transform.localPosition = new Vector3 (Mathf.Sin (stoneTransition) * BOUNDS_SIZE, score, secondaryPosition); //Slide in x axis
		else
			stones [stoneIndex].transform.localPosition = new Vector3 (secondaryPosition, score, Mathf.Sin (stoneTransition) * BOUNDS_SIZE); //Slide in z axis
	}

	//create the cut stone falling to ground
	void CreateRubble(Vector3 pos, Vector3 scale){
		GameObject fall = GameObject.CreatePrimitive(PrimitiveType.Cube);
		fall.transform.localPosition = pos;
		fall.transform.localScale = scale;
        Renderer rend = fall.GetComponent<Renderer>();
        if (rend != null)
        {
            rend.material = newMaterial[stoneIndex]; //set material of the rubble
            print("Index = " + stoneIndex);
        }
        fall.AddComponent<Rigidbody>();
		


	}

	void SpawnStone() {
		lastStonePosition = stones [stoneIndex].transform.position; //position of the stone below
		stoneIndex--;
		if (stoneIndex < 0)
			stoneIndex = transform.childCount - 1; //stone become same value as the start
		
		desiredPosition = (Vector3.down) * score; //stack not floating when click
		stones [stoneIndex].transform.localPosition = new Vector3 (0, score, 0);
		stones [stoneIndex].transform.localScale = new Vector3 (stoneBounds.x, 1, stoneBounds.y); //size of stone change
	} 


	bool PlaceStone() {
		Transform t = stones [stoneIndex].transform;

		//hard math 
		if (isMovingOnX) {
			float deltaX = lastStonePosition.x - t.position.x; //difference between current stone and the last stone
			if (Mathf.Abs (deltaX) > ERROR_MARGIN) { //x axis
				//cut the stone
				combo = 0;
				stoneBounds.x -= Mathf.Abs (deltaX);
				if (stoneBounds.x <= 0)
					return false;

				float middle = lastStonePosition.x + t.localPosition.x / 2;
				t.localScale = new Vector3 (stoneBounds.x, 1, stoneBounds.y);

				//the cut stone 
				CreateRubble (
					new Vector3 ((t.position.x > 0)
						? t.position.x + (t.localScale.x / 2)
						: t.position.x - (t.localScale.x / 2),
						t.position.y,
						t.position.z),
					new Vector3 (Mathf.Abs (deltaX), 1, t.localScale.z)
				);

				t.localPosition = new Vector3 (middle - (lastStonePosition.x / 2), score, lastStonePosition.z);

			} else { //place the stone successfully

				//if combo greater than 3, stone will be larger
				if (combo > COMBO_START_GAIN) {
					
					if (stoneBounds.x > BOUNDS_SIZE)
						stoneBounds.x = BOUNDS_SIZE;

					stoneBounds.x += STACK_BOUNDS_GAIN;
					float middle = lastStonePosition.x + t.localPosition.x / 2;
					t.localScale = new Vector3 (stoneBounds.x, 1, stoneBounds.y);
					t.localPosition = new Vector3 (middle - (lastStonePosition.x / 2), score, lastStonePosition.z);
				}
				combo++;
				t.localPosition = new Vector3 (lastStonePosition.x, score, lastStonePosition.z);
			}
		}
		else {
				float deltaZ = lastStonePosition.z - t.position.z;
				if (Mathf.Abs (deltaZ) > ERROR_MARGIN) { //x axis
					//cut the stone
					combo = 0;
					stoneBounds.y -= Mathf.Abs (deltaZ);
					if (stoneBounds.y <= 0)
						return false;

					float middle = lastStonePosition.z + t.localPosition.z / 2;
					t.localScale = new Vector3 (stoneBounds.x, 1, stoneBounds.y);

					CreateRubble (
						new Vector3 (
							t.position.x,
							t.position.y,
							(t.position.z > 0)
							? t.position.z + (t.localScale.z / 2)
							: t.position.z - (t.localScale.z / 2)),
					new Vector3 (t.localScale.z, 1, Mathf.Abs (deltaZ))
					);

					t.localPosition = new Vector3 (lastStonePosition.x, score, middle - (lastStonePosition.z / 2));
				} else { //place the stone successfully

						if (combo > COMBO_START_GAIN) {
							
							if (stoneBounds.y > BOUNDS_SIZE)
								stoneBounds.y = BOUNDS_SIZE;
							
							stoneBounds.y += STACK_BOUNDS_GAIN;
							float middle = lastStonePosition.z + t.localPosition.z / 2;
							t.localScale = new Vector3 (stoneBounds.x, 1, stoneBounds.y);
							t.localPosition = new Vector3 (lastStonePosition.x, score, middle - (lastStonePosition.z / 2));
						}
					combo++;
					t.localPosition = new Vector3 (lastStonePosition.x, score, lastStonePosition.z);
				}
			}

		secondaryPosition = (isMovingOnX)
			? t.localPosition.x
			: t.localPosition.z;
		isMovingOnX = !isMovingOnX;
		return true;
		}

		
	void UpdateScore(){
		scoreText.text = "Score:\n" + score;
	}

	void EndGame() {
		print ("End");
		gameOver = true;
		stones [stoneIndex].AddComponent<Rigidbody>(); //make the stone fall to ground

		StartCoroutine (ButtonActive ());
		WriteJson ();
		
	}

	IEnumerator ButtonActive(){
		yield return new WaitForSeconds (2.0f);
		restartButton.SetActive(true);	
		backButton.SetActive(true);	
		confirmQuit.SetActive(true);	
			}

	public int GetScore(){
		return score;
	}

	public bool IsGameOver(){
		return gameOver;
	}

	void WriteJson(){
		//print (score);
		if (gameOver) {
			print ("Over");
		}
		playerState = obj.AddComponent<PlayerState>(); //the same as, class obj = new class
	}
}
