import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.service.IAdministrateurService;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "adminMB")
@RequestScoped
public class AdministrateurManagedBean implements Serializable {

	@ManagedProperty(value="#{adminService}")
	private IAdministrateurService administrateurService;
	@ManagedProperty(value="#{categorieService}")
	private ICategorieService categorieService;

	private Administrateur administrateur;

	private List<Categorie> listeCategorie;

	private HttpSession maSession;

	public AdministrateurManagedBean() {
		this.administrateur = new Administrateur();
	}

	public IAdministrateurService getAdministrateurService() {
		return administrateurService;
	}

	public void setAdministrateurService(IAdministrateurService administrateurService) {
		this.administrateurService = administrateurService;
	}

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	public String identifierAdmin() {
		try {
			Administrateur aOut = administrateurService.isExist(this.administrateur);

			if (aOut != null) {

				List<Categorie> listOut = categorieService.getAllCategorie();
				this.listeCategorie = new ArrayList<>();

				for (Categorie element : listOut) {
					if (element.getPhoto() == null) {
						element.setImage(null);
					} else {
						element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
					}
					this.listeCategorie.add(element);
				}

				// Ajouter la liste dans la session
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList",
						listeCategorie);
				return "accueilAdmin";
			}
		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("L'admin n'existe pas / erreur chargement de la liste des categories"));
		}
		return "failure";
	}

	public String testAffichage() {

		List<Categorie> listOut = categorieService.getAllCategorie();
		for (Categorie categorie : listOut) {
			System.out.println(categorie);
		}
		this.listeCategorie = new ArrayList<>();

		for (Categorie element : listOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}
			this.listeCategorie.add(element);
		}

		// Ajouter la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList", listeCategorie);
		return "accueil";

	}

}
