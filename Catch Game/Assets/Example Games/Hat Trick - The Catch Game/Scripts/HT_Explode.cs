using UnityEngine;
using System.Collections;

public class HT_Explode : MonoBehaviour {

	public GameObject explosion;
	public ParticleSystem[] effects;

	void OnCollisionEnter2D (Collision2D collision) {
		if (collision.gameObject.tag == "Hat") { //find tag
			Instantiate (explosion, transform.position, transform.rotation);
			foreach (var effect in effects) { //destroy each effect
				effect.transform.parent = null; //parent is bomb
				effect.Stop (); //stop emitting efect
				Destroy (effect.gameObject, 1.0f); //destroy game object holding the particle
			}
			Destroy (gameObject); //destroy the bomb
		}
	}
}
