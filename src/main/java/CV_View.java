import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import resume.Competence;
import resume.Competences_Info;
import resume.Ecole;
import resume.Entreprise;
import resume.Langue;
import resume.Langues;
import resume.Resume;

public class CV_View {
	private JFrame frame, parent;
	private Resume cv;
	
	public CV_View(Resume cv, JFrame parent) {
		this.cv = cv;
        this.parent = parent;
		createView();
		placeComponents();
		createController();
	}

	private void createController() {
	}

	private void placeComponents() {
		JPanel p = new JPanel(); {
			p.add(new JLabel("Curriculum vitae de " + cv.getFirstName() + " " + cv.getLastName()));			
		}
		frame.add(p, BorderLayout.NORTH);
		
		p = new JPanel (new GridLayout(0,1)); {
			JPanel q;
			if (cv.getExperiences().getListExp().size() != 0) {
				 q = new JPanel(new GridLayout(cv.getExperiences().getListExp().size() + 1, 1)); {
					q.add(new JLabel("Experience professionnelle : "));
					for (Entreprise entre : cv.getExperiences().getListExp()) {
						q.add(new JLabel("- " + entre.getNom() + ", "+ entre.getPoste() +"(" +entre.getDate() + ")"));
					}
				}
				p.add(q);
			}
			if (cv.getCompetences_info().getCompetences().size() != 0) {
				q = new JPanel(new GridLayout(cv.getCompetences_info().getCompetences().size() + 1, 1)); {
					q.add(new JLabel("Competences : "));
					for (Competence cp : cv.getCompetences_info().getCompetences()) {
						q.add(new JLabel("- " + cp.getNom() + " (" +cp.getNiveau() + ")"));
					}
				}
				p.add(q);
			}
			if (cv.getScolarite().getListEcole().size() != 0) {
				q = new JPanel(new GridLayout(cv.getScolarite().getListEcole().size() + 1, 1)); {
					q.add(new JLabel("Scolarite : "));
					for (Ecole e : cv.getScolarite().getListEcole()) {
						q.add(new JLabel("- " + e.getDiplome() + " " + e.getNom()  + " ( "+ e.getVille()+ ", " + e.getDate() + ")") );
					}
				}
				p.add(q);
			}
			if (cv.getLangues().getLangueList().size() != 0) {
				q = new JPanel(new GridLayout(cv.getLangues().getLangueList().size() + 1, 1)); {
					q.add(new JLabel("Langues : "));
					for (Langue l : cv.getLangues().getLangueList()) {
						q.add(new JLabel("- " + l.getNom() + " (" + l.getNiveau() + ")"));
					}
				}
				p.add(q);
			}
		}
		frame.add(p, BorderLayout.CENTER);
		
	}

	private void createView() {
		// TODO Auto-generated method stub
		frame = new JFrame("CV");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
            //force chaque composant de la fenêtre à appeler sa méthode updateUI
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {}
		frame.setSize(300,400);
		frame.setLocationRelativeTo(parent);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Resume cv = new Resume();
            	cv.setFirstName("Jeremy");
            	cv.setLastName("Hebert");
            	List<Competence> lCp = new ArrayList<Competence>();
            	lCp.add(new Competence("Java" , "bon"));
            	lCp.add(new Competence("C" , "moyen"));
            	lCp.add(new Competence("Php" , "bon"));
            	lCp.add(new Competence("SQL" , "bon"));
            	lCp.add(new Competence("CSS" , "moyen +"));
            	lCp.add(new Competence("HTML" , "bon"));
            	Competences_Info ci = new Competences_Info(lCp);
            	cv.setCompetences_info(ci);
            	
            	List<Langue> langues = new ArrayList<Langue>();
            	langues.add(new Langue("Anglais" , "bon"));
            	langues.add(new Langue("Espagnol" , "moyen"));
            	cv.setLangues(new Langues(langues));
                new CV_View(cv, null);
            }
        });
    }
}
