package app;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.HelpWindow</code> that defines the help window containing
 * application description.
 * @author Anastasiya Prakapovich
 * @version 1.0	01/05/2020
 */
public class HelpWindow extends JDialog {

    private static final long serialVersionUID = 1L;
    private JEditorPane editorPane = null;
    private URL opisUrl = null;

    /**
     * Constructor without parameters app.HelpWindow.
     */
    public HelpWindow() {
        this.setTitle("Pomoc - aplikacja testowa");
        this.setModal(false);
        this.setResizable(true);
        this.setSize(800,600);

        this.addWindowListener	(new WindowAdapter(){ // obsluga zdarzenia okna
            public void windowClosing(WindowEvent e){  // obsluga wcisniecia przycisku zamkniecia okna
                setVisible(false);				// ukrycie obiektu
            }	// koniec metody windowClosing
        });	// koniec obslugi zdarzenia okna

        // pobranie rozmiarow aplikacji
        Dimension dialogSize = getSize();
        // pobranie rozdzielczosci pulpitu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(dialogSize.height > screenSize.height)
            dialogSize.height = screenSize.height;
        if(dialogSize.width > screenSize.width)
            dialogSize.height = screenSize.width;

        // rozmieszczenie aplikacji na srodku ekranu
        setLocation((screenSize.width-dialogSize.width)/2,
                (screenSize.height-dialogSize.height)/2);

        this.setLayout(new BorderLayout());

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        opisUrl = HelpWindow.class.getResource(
                "/doc/index.html");

        // Zaladowanie strony opisUrl do edytora editorPane
        setURLPage();

        // obsluga zdarzenia hyperlink
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        editorPane.setPage(event.getURL());
                    }
                    catch(IOException e) {
                        editorPane.setText("Exception: "+e);
                    }
                }
            }
        });

        this.add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }
    /**
     * The private method that retrieves the descriptive page in html format.
     */
    private void setURLPage() {
        try {
            editorPane.setPage(opisUrl);
        }
        catch(Exception e) {
            editorPane.setText("Nie mozna otworzy plikow z pomoca " + opisUrl);
        }
    }
}
