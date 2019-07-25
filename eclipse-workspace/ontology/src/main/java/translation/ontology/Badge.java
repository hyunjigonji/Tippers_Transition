package translation.ontology;

import java.awt.Image;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;

import translation.ontology.OntologyManager;

public class Badge {

	public ArrayList<Image> images;
	public String badge_title;

	public String URI;

	public Badge(ArrayList<Image> images, String badge_title) {
		this.images = images;
		this.badge_title = badge_title;

		this.URI = "Badge_" + hashcode();

	}

	public Set<OWLAxiom> createInstance(OWLDataFactory factory,
			OWLOntology ontology,
			BidirectionalShortFormProvider bidiShortFormProvider) {

		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		OWLClass badge = (OWLClass) bidiShortFormProvider
				.getEntity("Badge_Creator");

		OWLNamedIndividual badgeInd = (OWLNamedIndividual) bidiShortFormProvider
				.getEntity(this.URI.toString());

		if (badgeInd == null) {
			// the instance does not exist in the ontology
			badgeInd = factory.getOWLNamedIndividual(IRI
					.create(OntologyManager.ontologyIRI + this.URI.toString()));
		}

		OWLClassAssertionAxiom classAssertion = factory
				.getOWLClassAssertionAxiom(badge, badgeInd);

		axioms.add(classAssertion);

		// Let's take a look at the object properties

		for (Image img : images) {
			// OBJECT PROPERTY: hasImage
			OWLObjectProperty hasImage = (OWLObjectProperty) bidiShortFormProvider
					.getEntity("hasImage");

			OWLNamedIndividual imgInd = (OWLNamedIndividual) bidiShortFormProvider
					.getEntity(img.toString());

			OWLObjectPropertyAssertionAxiom assertionObjProperty = factory
					.getOWLObjectPropertyAssertionAxiom(hasImage, badgeInd,
							imgInd);

			axioms.add(assertionObjProperty);
		}

		// Now let's take a look at the data properties

		// DATA PROPERTY: badge_title
		OWLDataProperty badge_title = (OWLDataProperty) bidiShortFormProvider
				.getEntity("badge_title");

		OWLDataPropertyAssertionAxiom assertionDataProperty = factory
				.getOWLDataPropertyAssertionAxiom(badge_title, badgeInd,
						this.badge_title);

		axioms.add(assertionDataProperty);

		return axioms;
	}

	public int hashcode() {

		if (images != null)
			return badge_title.hashCode() + images.hashCode();
		else
			return badge_title.hashCode();
	}

}