using UnityEngine;
using System.Collections;
using System.Collections.Generic;



public class HatController : MonoBehaviour {

	public Vector2 velocity;
	public Rigidbody2D rb2D;
	public Camera cam;
	private float maxWidth;
	private bool canControl;
	public Renderer rend;



	// Use this for initialization
	void Start () {
		//Dont forget to add your camera and HatSprite to HatController 
		if (cam == null) {
			cam = Camera.main;
		}
		canControl = true; //actually set it to false
		rb2D = GetComponent<Rigidbody2D>();
		rend = GetComponent<Renderer>();

		//get the corner of the screen
		Vector3 upperCorner = new Vector3 (Screen.width, Screen.height, 0.0f);
		Vector3 targetWidth = cam.ScreenToWorldPoint(upperCorner);

		float hatWidth = rend.bounds.extents.x;
		maxWidth = targetWidth.x - hatWidth;
	}
	
	// Update is called once per physics timestep
	void FixedUpdate () {
		if (canControl) {
			//get position of mouse position or touch screen
			Vector3 rawPosition = cam.ScreenToWorldPoint (Input.mousePosition);
			Vector3 targetPosition = new Vector3 (rawPosition.x, 0.0f, 0.0f);

			//clamp targetPosition between naxWidth left and right
			//can move hat only in the screen
			float targetWidth = Mathf.Clamp (targetPosition.x, -maxWidth, maxWidth);
			targetPosition = new Vector3 (targetWidth, targetPosition.y, targetPosition.z);
			//print ("rawPosition = " + rawPosition.ToString("F4"));
			//move object to target Position
			rb2D.MovePosition (targetPosition); 
		}
	}

	public void ToggleControl(bool toggle){
		canControl = toggle;
	}


}
