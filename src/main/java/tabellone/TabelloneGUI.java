package tabellone;

import javax.swing.*;

import personaggi.PersonaggiCreati;
import personaggi.Personaggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe che rappresenta l'interfaccia grafica del tabellone di gioco.
 */
public class TabelloneGUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Position, JButton> cells = new HashMap<>();
    private Tabellone tabellone;
    private String personaggioDaIndovinare;

    /**
     * Costruttore della classe TabelloneGUI.
     * @param size La dimensione del tabellone (numero di righe/colonne).
     */
    public TabelloneGUI(int difficolta) {
        int sizeX = 0;
        int sizeY = 0;
        switch (difficolta) {
            case 1:
                sizeX = 3; // Livello difficile - imposto la larghezza a 3
                sizeY = 3; // Livello difficile - imposto l'altezza a 3
                break;
            case 2:
                sizeX = 4; // Livello difficile - imposto la larghezza a 4
                sizeY = 4; // Livello difficile - imposto l'altezza a 4
                break;
            case 3:
                sizeX = 4; // Livello difficile - imposto la larghezza a 6
                sizeY = 6; // Livello difficile - imposto l'altezza a 4
                break;
            default:
                // Gestione di un livello di difficoltà non valido
                JOptionPane.showMessageDialog(this, "Livello di difficoltà non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(160 * sizeX, 130 * sizeY);
        // Creazione di un'istanza di TabelloneImpl
        this.tabellone = new TabelloneImpl(sizeX, sizeY); 

        JPanel panel = new JPanel(new GridLayout(sizeX, sizeY));
        this.getContentPane().add(panel);

        // Inizializzazione del tabellone con i personaggi iniziali
        tabellone.inizializzaTabellone(sizeX, sizeY);
        //Ottiene il personaggio da indovinare
        personaggioDaIndovinare = tabellone.getPersonaggioDaIndovinare();

        // ActionListener personalizzato per i pulsanti
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton jb = (JButton) e.getSource();
                // Disabilita il pulsante cliccato
                jb.setEnabled(false); 

                // Ottieni la posizione del pulsante cliccato
                Position pos = null;
                for (Map.Entry<Position, JButton> entry : cells.entrySet()) {
                    if (entry.getValue() == jb) {
                        pos = entry.getKey();
                        break;
                    }
                }

                // Ottieni il personaggio corrispondente alla posizione
                Personaggio personaggioCliccato = tabellone.getTabellone().get(pos);

                // Controlla se il personaggio cliccato corrisponde al personaggio da indovinare
                if (personaggioCliccato.getNome().equals(personaggioDaIndovinare)) {
                    JOptionPane.showMessageDialog(TabelloneGUI.this, "Vittoria");
                    // Chiudi la finestra
                    dispose(); 
                }
            }
        };

        // Inizializzazione dei pulsanti e associazione dell'ActionListener
        List<Personaggio> personaggi = PersonaggiCreati.creaPersonaggi();
        int index = 0;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Position pos = new Position(j, i);
                Personaggio personaggio = personaggi.get(index++);
                JButton jb = new JButton();
                
                // Ottieni l'immagine del personaggio
                ImageIcon icon = personaggio.getImmagine();
                // Ridimensiona l'immagine per adattarla alla dimensione del pulsante
                Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                // Imposta l'immagine ridimensionata come icona del pulsante
                jb.setIcon(scaledIcon);
                
                cells.put(pos, jb);
                jb.addActionListener(al); 
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }
}