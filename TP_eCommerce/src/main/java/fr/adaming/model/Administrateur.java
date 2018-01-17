package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "administrateurs")
public class Administrateur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_a;

	private String mail;
	private String mdp;

	public Administrateur() {
		super();
	}

	public Administrateur(String mail, String mdp) {
		super();
		this.mail = mail;
		this.mdp = mdp;
	}


	public Administrateur(int id_a, String mail, String mdp) {
		super();
		this.id_a = id_a;
		this.mail = mail;
		this.mdp = mdp;
	}

	public int getId_a() {
		return id_a;
	}

	public void setId_a(int id_a) {
		this.id_a = id_a;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	@Override
	public String toString() {
		return "Administrateur [id_a=" + id_a + ", mail=" + mail + ", mdp=" + mdp + "]";
	}
	
	

}
