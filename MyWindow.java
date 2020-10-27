package app;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.MyWindow</code> that defines the main application window.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
public class MyWindow extends JFrame implements ActionListener
{
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;
    private Logger logger;
    private final JPanel conPane;
    private BarChart barChart =null;
    private JTaskPane taskPane;
    private JButton submitButton1,submitButton2;
    private Window window;
    /**
     * Variable that creates Help app.Window.
     */
    HelpWindow helpWindow = null;
    /**
     * Variable that creates About app.Window.
     */
    AboutWindow aboutWindow = null;
    /**
     * Variable that creates Info Bottom Panel.
     */
    InfoBottomPanel infoPanel = null;
    /**
     * Variable that creates Center Panel.
     */
    CenterPanel centerPanel = null;
    /**
     * Variables that make up the menu.
     */
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, viewMenu, helpMenu;
    JMenuItem  fileWriteMenuItem,fileChartMenuItem,filePrintMenuItem, fileExitMenuItem,arrayAddMenuItem,
            arrayAnnulMenuItem,arrayWriteMenuItem,helpContextMenutem, helpAboutMenuItem;
    JCheckBoxMenuItem viewStatusBarMenuItem, viewJToolBarMenuItem;

    /**
     * Variables that make up the toolbar.
     */
    JToolBar jToolBar;
    JButton jtbWrite,jtbChart,jtbPrint, jtbExit,jtbAdd,jtbAnnul,jtbArWrite, jtbHelp, jtbAbout;

    /**
     * Definition of text labels.
     */
    String[] labelMenu = {"Plik", "Edycja", "Widok", "Pomoc"};
    String[] labelFileMenuItem = {"Zapisz","Wykres","Drukuj", "Zamknij"};
    String[] labelEditionMenuItem = {"Dodaj","Wyzeruj","Wypełnij"};
    String[] labelViewMenuItem = {"Ukryj pasek statusu","Ukryj pasek narzedziowy"};
    String[] labelHelpMenuItem = {"Kontekst pomocy", "Informacje o programie"};

    /**
     *  Definition of labels describing in so-called the cloud.
     */
    String[] tooltipFileMenu = {"Zapis","Drukowanie", "Zamkniecie aplikacji"};
    String[] tooltipHelpMenu = {"Uruchomienie pomocy", "Informacje o programie","Wykres"};
    /**
     * Variables that create icons.
     */
    Icon iconWrite,iconPrint, iconExit, iconHelpContext, iconAbout,iconChart;
    Icon mIconWrite,mIconChart,mIconPrint, mIconExit,mIconAdd,mIconAnnul,mIconArWrite, mIconHelpContext, mIconAbout;

    /**
     * Constructor without parameters app.MyWindow.
     */
    public MyWindow()
    {
        logger = Logger.getLogger(this.getClass().getName());
        DOMConfigurator.configure("config/log4j-conf.xml");
        logger.info("Start Aplikacji");
        this.setTitle("Moje Okno");
        // definicja zdarzenia zamkniecia okna
        this.addWindowListener	(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                logger.info("Zamkniecie aplikacji");
                dispose();
                System.exit(0);
            }
        });
        // Rozmieszczenie okna na srodku ekranu
        Dimension frameSize = new Dimension(WIDTH,HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//pobranie rozdzielczosci pulpitu
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if(frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        setSize(frameSize);
        setLocation((screenSize.width-frameSize.width)/2,
                (screenSize.height-frameSize.height)/2);

        // Utworzenie glownego kontekstu (ContentPane)
        conPane = (JPanel) this.getContentPane();
        conPane.setLayout(new BorderLayout());
        // utworzenie GUI w watku zdarzeniowym
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createIcons();
                    createMenu();
                    try {
                        createGUI();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TipOfDay();
                }
            });
        }
        catch(Exception e) {
            logger.error("Blad podczas tworzenia GUI aplikacji");
            System.out.println("ERROR - Blad podczas tworzenia GUI aplikacji");
        }
    }
    /**
     * The method that creates icons used in the program
     * and in other methods.
     */
    private void createIcons() {
        try {
            // Utworzenie ikon 24 * 24 px dla paska toolbar
            iconWrite = createMyIcon("zapis.jpg");
            iconPrint = createMyIcon("print.jpg");
            iconExit = createMyIcon("close.jpg");
            iconHelpContext = createMyIcon("help_context.jpg");
            iconAbout = createMyIcon("about.jpg");
            iconChart=createMyIcon("chat.jpg");

            // Utworzenie ikon 16 * 16 px dla MenuItem
            mIconWrite = createMyIcon("zapisz1.jpg");
            mIconChart = createMyIcon("wykres1.jpg");
            mIconPrint = createMyIcon("min_print.jpg");
            mIconExit = createMyIcon("min_close.jpg");
            mIconAdd = createMyIcon("dodaj1.jpg");
            mIconAnnul = createMyIcon("wyzeruj1.jpg");
            mIconArWrite = createMyIcon("wypelnij1.jpg");
            mIconHelpContext = createMyIcon("min_help_context.jpg");
            mIconAbout = createMyIcon("min_about.jpg");

        }
        catch(Exception e) {
            logger.error("Blad tworzenia ikon");
            System.out.println("ERROR - Blad tworzenia ikon");

        }
    }
    /**
     * The method that creates the main window menu.
     */
    private void createMenu() {
        // Utworzenie paska menu
        menuBar = new JMenuBar();
        // Utworzenie pol menu glownego
        fileMenu = createJMenu(labelMenu[0], KeyEvent.VK_P, true);
        editMenu = createJMenu(labelMenu[1], KeyEvent.VK_E, true);
        viewMenu = createJMenu(labelMenu[2], KeyEvent.VK_W, true);
        helpMenu = createJMenu(labelMenu[3], KeyEvent.VK_O, true);

        // Utworzenie pol podmenu
        fileWriteMenuItem = createJMenuItem(labelFileMenuItem[0],mIconWrite,
                KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK),true);
        fileChartMenuItem = createJMenuItem(labelFileMenuItem[1],mIconChart,
                KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK),true);
        filePrintMenuItem = createJMenuItem(labelFileMenuItem[2],mIconPrint,
                KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.ALT_MASK),true);
        fileExitMenuItem = createJMenuItem(labelFileMenuItem[3],mIconExit,
                KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.ALT_MASK),true);

        // utworzenie MenuItem dla editorMenu
        arrayAddMenuItem = createJMenuItem(labelEditionMenuItem[0],mIconAdd,
                KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.ALT_MASK),true);
        arrayAnnulMenuItem = createJMenuItem(labelEditionMenuItem[1],mIconAnnul,
                KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.ALT_MASK),true);
        arrayWriteMenuItem = createJMenuItem(labelEditionMenuItem[2],mIconArWrite,
                KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.ALT_MASK),true);
        //

        // utworzenie MenuItem dla viewMenu
        viewStatusBarMenuItem = createJCheckBoxMenuItem(
                labelViewMenuItem[0],false);
        viewJToolBarMenuItem = createJCheckBoxMenuItem(
                labelViewMenuItem[1],false);
        //


        // utworzenie obiektow MenuItem dla helpMenu
        helpContextMenutem = createJMenuItem(labelHelpMenuItem[0],
                mIconHelpContext,KeyStroke.getKeyStroke(KeyEvent.VK_H,
                        ActionEvent.ALT_MASK),true);
        helpAboutMenuItem = createJMenuItem(labelHelpMenuItem[1],mIconAbout,
                KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.ALT_MASK),true);

        // dodanie utworzonych elementow menu dopaska menu JMenuBar
        fileMenu.add(fileWriteMenuItem);
        fileMenu.add(fileChartMenuItem);
        fileMenu.add(filePrintMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(fileExitMenuItem);
        editMenu.add(arrayAddMenuItem);
        editMenu.add(arrayAnnulMenuItem);
        editMenu.add(arrayWriteMenuItem);
        viewMenu.add(viewStatusBarMenuItem);
        viewMenu.add(viewJToolBarMenuItem);
        helpMenu.add(helpContextMenutem);
        helpMenu.add(helpAboutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
    }
    /**
     * The GUI creation method.
     */
    private void createGUI() throws IOException {

        // Utworzenie paska narzedziowego
        jToolBar = new JToolBar();
        createJToolBar(jToolBar);
        // Utworzenie panelu informacyjnego umieszczonego na dole okna
        infoPanel = new InfoBottomPanel();
        // Utworzenie panelu centralnego
        centerPanel = new CenterPanel();
        centerPanel.setVisible(true);
        TaskPane();
        conPane.add(jToolBar, BorderLayout.NORTH);
        conPane.add(infoPanel, BorderLayout.SOUTH);
        conPane.add(centerPanel, BorderLayout.CENTER);
        conPane.add(taskPane, BorderLayout.WEST);
    }
    /**
     * The method that creates the toolbar.
     * @param cjtb type variable JToolBar.
     */
    public void createJToolBar(JToolBar cjtb) {
        cjtb.setFloatable(false);
        cjtb.add(Box.createHorizontalStrut(5));

        // Utworzenie przycisk�w paska narz�dziowego
        jtbWrite = createJButtonToolBar(tooltipFileMenu[0],iconWrite,true);
        jtbPrint = createJButtonToolBar(tooltipFileMenu[1],iconPrint,true);
        jtbExit = createJButtonToolBar(tooltipFileMenu[2],iconExit,true);
        jtbHelp = createJButtonToolBar(tooltipHelpMenu[0],iconHelpContext,true);
        jtbAbout = createJButtonToolBar(tooltipHelpMenu[1],iconAbout,true);
        jtbChart=createJButtonToolBar(tooltipHelpMenu[2],iconChart,true);
        // dodanie przycisk�w do paska narz�dziowego
        cjtb.add(jtbChart);
        cjtb.add(jtbWrite);
        cjtb.add(jtbPrint);
        cjtb.addSeparator();
        cjtb.add(jtbHelp);
        cjtb.add(jtbAbout);
        cjtb.add(jtbExit);

    }
    /**
     * The method that creates the type object  Icon.
     * @param file variable specifying the file name.
     * @return object of type Icon.
     */
    public Icon createMyIcon(String file) {
        String name = "/grafika/"+file;
        Icon icon = new ImageIcon(getClass().getResource(name));
        return icon;
    }
    /**
     * The method that creates the type object JMenu.
     * @param name name variable.
     * @param keyEvent variable that specifies the shortcut key.
     * @param enable boolean variable that indicates whether the menu is active.
     * @return the created type object jMenu.
     */
    public JMenu createJMenu(String name, int keyEvent, boolean enable) {
        JMenu jMenu = new JMenu(name);
        jMenu.setMnemonic(keyEvent);
        jMenu.setEnabled(enable);
        return jMenu;
    }
    /**
     * The method that creates the type object JMenuItem.
     * @param name name variable.
     * @param icon variable specifying the icon displayed along with the name.
     * @param key variable specifying the acceleration keys for access.
     * @param enable boolean variable that indicates whether the submenu is active.
     * @return the created type object JMenuItem.
     */
    public JMenuItem createJMenuItem(String name, Icon icon, KeyStroke key,
                                     boolean enable) {
        JMenuItem jMI;
        if(icon != null)
            jMI = new JMenuItem(name,icon);
        else jMI = new JMenuItem(name);
        jMI.setAccelerator(key);
        jMI.addActionListener(this);
        jMI.setEnabled(enable);
        return jMI;
    }
    /**
     * The method that creates the JCheckBoxMenuItem type object.
     * @param name name variable.
     * @param enable boolean variable that indicates whether the submenu is active.
     * @return the created type object JCheckBoxMenuItem.
     */
    public JCheckBoxMenuItem createJCheckBoxMenuItem(String name,
                                                     boolean enable) {
        JCheckBoxMenuItem jcbmi = new JCheckBoxMenuItem(name,enable);
        jcbmi.addActionListener(this);
        jcbmi.setEnabled(true);
        return jcbmi;
    }
    /**
     * The method that creates a JButton object for the toolbar.
     * @param tooltip variable specifying the hint for the button.
     * @param icon variable specifying the image assigned to the button.
     * @param enable boolean variable determining whether the button is active.
     * @return the created type object JButton.
     */
    public JButton createJButtonToolBar(String tooltip,Icon icon,boolean enable) {
        JButton jb = new JButton("",icon);
        jb.setToolTipText(tooltip);
        jb.addActionListener(this);
        jb.setEnabled(enable);
        return jb;
    }

    /**
     * Methods for creating tips of the day.
     */
    private void TipOfDay()  {
        DefaultTipModel model=new DefaultTipModel();
       /* model.add(new DefaultTip("tip1","   Program pozwala na wypełnienie tablicy losowymi liczbami,dodanie pojedynczej " +
                "liczby do tabeli,wyzerowanie całej tabeli i zapisanie jej do pliku." + "\n" +
                "   Możemy również  obliczyć sumę oraz średnią elementów," +
                "znaleść ich max i min wartość, oraz wyświetlić wykres."));*/
       // String filename = "grafika\\about.jpg";
        //Image image = ImageIO.read(new File(filename));
        JLabel lIcon,lIcon1,lIcon2,lIcon3,lIcon4,lIcon5,lIcon6,lIcon7,lIcon8,lIcon9,lIcon10,lIcon11,lIcon12,lIcon13,lIcon14,lIcon15;
        try {
            new ImageIcon(getClass().getResource("/grafika/dodaj.jpg"));
            lIcon = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/program.PNG")));
            lIcon1 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/menu1.PNG")));
            lIcon2 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/plik1.PNG")));
            lIcon3 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/edycja1.PNG")));
            lIcon4 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/widok1.PNG")));
            lIcon5 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/pomoc1.PNG")));
            lIcon6 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/plik1.PNG")));
            lIcon7 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/edycja1.PNG")));
            lIcon8 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/widok1.PNG")));
            lIcon9 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/pomoc1.PNG")));
            lIcon10 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/tabela1.PNG")));
            lIcon11 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/oblicz1.PNG")));
            lIcon12 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/data1.PNG")));
            lIcon13 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/rezultat1.PNG")));
            lIcon14 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/status1.PNG")));
            lIcon15 = new JLabel(new ImageIcon(
                    this.getClass().getResource("/grafika/menu3.PNG")));
        }
        catch(Exception e) {
            lIcon = new JLabel();
            lIcon1 = new JLabel();
            lIcon2 = new JLabel();
            lIcon3 = new JLabel();
            lIcon4 = new JLabel();
            lIcon5 = new JLabel();
            lIcon6 = new JLabel();
            lIcon7 = new JLabel();
            lIcon8 = new JLabel();
            lIcon9 = new JLabel();
            lIcon10 = new JLabel();
            lIcon11 = new JLabel();
            lIcon12 = new JLabel();
            lIcon13 = new JLabel();
            lIcon14 = new JLabel();
            lIcon15 = new JLabel();
        }
        model.add(new DefaultTip("tip2",lIcon));
        model.add(new DefaultTip("tip2",lIcon1));
        model.add(new DefaultTip("tip3",lIcon2));
        model.add(new DefaultTip("tip4",lIcon3));
        model.add(new DefaultTip("tip5",lIcon4));
        model.add(new DefaultTip("tip5",lIcon5));
        model.add(new DefaultTip("tip3",lIcon6));
        model.add(new DefaultTip("tip4",lIcon7));
        model.add(new DefaultTip("tip5",lIcon8));
        model.add(new DefaultTip("tip5",lIcon9));
        model.add(new DefaultTip("tip5",lIcon10));
        model.add(new DefaultTip("tip4",lIcon11));
        model.add(new DefaultTip("tip5",lIcon12));
        model.add(new DefaultTip("tip5",lIcon13));
        model.add(new DefaultTip("tip5",lIcon14));
        model.add(new DefaultTip("tip5",lIcon15));
        JTipOfTheDay tip=new JTipOfTheDay(model);
        tip.setPreferredSize(new Dimension(800,500));
        tip.showDialog(this);
    }
    /**
     * The method that creates variables for west location.
     * Method creating a JTaskPane.
     */
    private void TaskPane(){
        taskPane=new JTaskPane();
        JTaskPaneGroup group1=new JTaskPaneGroup();
        JTaskPaneGroup group2=new JTaskPaneGroup();
        group1.setTitle("JPEG Wykresu");
        submitButton2 = new JButton("Otworz JPEG",new ImageIcon(this.getClass().getResource("/grafika/ikon.jpg")));
        submitButton2.addActionListener(this);
        group2.setTitle("Plik");
        submitButton1 = new JButton("Otworz plik",new ImageIcon(this.getClass().getResource("/grafika/folder.jpg")));
        submitButton1.addActionListener(this);
        group1.add(submitButton2);
        group2.add(submitButton1);
        taskPane.add(group1);
        taskPane.add(group2);
        taskPane.setBackground(new Color(125,212,227));
    }
    /**
     * Method that prints a list of data about ...
     */
    public void printListForm() {
        try {
            //	setPrintArrayList();
            // Glowny obiekt odpowiedzialny za drukowanie
            PrinterJob job = PrinterJob.getPrinterJob();
            // Obiekt odpowiedzialny za rozmiar i orientacje drukowanej strony
            PageFormat pf = new PageFormat();
            // Wywolanie okna ustawien drukowania
            job.pageDialog(pf);
            // job.setPrintable(ListFrame, pf);
            if(job.printDialog()) {
                job.print();	// Jesli uzytkownik wybral ok drukujemy panel
                InfoBottomPanel.setInfoString("Wydrukowanie listy");
            }
        }
        catch(Exception exc) {
            logger.error("Blad drukowania");
            System.out.println("Blad drukowania...");
        }
    }
    /**
     * Public interface method <code>ActionListener</code>
     * that handles the action event <code>ActionEvent</code>.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if((ae.getSource() == filePrintMenuItem) ||
                (ae.getSource() == jtbPrint)) {
            // Otwarcie okna wydruku
            printListForm();
        }
        else if((ae.getSource() == fileWriteMenuItem)||(ae.getSource()==jtbWrite)){
            try {
                centerPanel.tableModel.WriteToTheFile();
                centerPanel.resultTextArea.append("Poprawnie zapisane do pliku"+"\n");
                logger.info("Poprawnie zapisane do pliku");
            } catch (IOException e) {
                logger.error("Blad zapisu do pliku");
                JOptionPane.showMessageDialog(this,"Blad zapisu do pliku ","Uwaga",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if((ae.getSource() == fileChartMenuItem)||(ae.getSource()==jtbChart)){
            barChart=new BarChart(centerPanel.tableModel);
            barChart.setVisible(true);
            try {
                barChart.saveChartAsJPEG();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if((ae.getSource() == fileExitMenuItem) ||
                (ae.getSource() == jtbExit)) {
            // Zamkniecie aplikacji
            logger.info("Zamkni�cie aplikacji");
            dispose();
            System.exit(0);
        }
        else if((ae.getSource() == arrayAddMenuItem)||(ae.getSource()==jtbAdd)){
            try {
                int value = Integer.parseInt(centerPanel.paramTextField.getText());
                int row = (int) centerPanel.slider.getValue();
                int col = (int) centerPanel.slider1.getValue();
                centerPanel.tableModel.setValue(value, row, col);
                centerPanel.resultTextArea.append(" Value-" + value + "\n");
            } catch (NumberFormatException ex) {
                logger.error("Blad dodawania licby do tabeli");
                JOptionPane.showMessageDialog(this,"Blad dodawania licby do tabeli ","Uwaga",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if((ae.getSource() == arrayAnnulMenuItem)||(ae.getSource()==jtbAnnul)){
            centerPanel.tableModel.setZeroTable();
        }
        else if((ae.getSource() == arrayWriteMenuItem)||(ae.getSource()==jtbArWrite)){
            centerPanel.tableModel.setRandomTable();
        }
        else if(ae.getSource() == viewStatusBarMenuItem) {
            boolean stan = viewStatusBarMenuItem.getState();
            if(stan) infoPanel.setVisible(false);
            else infoPanel.setVisible(true);
        }
        else if(ae.getSource() == viewJToolBarMenuItem) {
            boolean stan = viewJToolBarMenuItem.getState();
            if(stan) jToolBar.setVisible(false);
            else jToolBar.setVisible(true);
        }
        else if((ae.getSource() == helpContextMenutem) ||
                (ae.getSource() == jtbHelp)) {
            if(helpWindow != null) helpWindow.setVisible(true);
            else {
                helpWindow = new HelpWindow();
                helpWindow.setVisible(true);
            }
        }
        else if((ae.getSource() == helpAboutMenuItem) ||
                (ae.getSource() == jtbAbout)) {
            if(aboutWindow != null) aboutWindow.setVisible(true);
            else {
                aboutWindow = new AboutWindow();
                aboutWindow.setVisible(true);
            }
        }
        else if (ae.getSource()==jtbChart){
            barChart=new BarChart(centerPanel.tableModel);
            barChart.setVisible(true);
        }
        else if(ae.getSource()==submitButton1){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                Desktop d;
                if (Desktop.isDesktopSupported()) {

                    d = Desktop.getDesktop();

                    try {
                        d.open(chooser.getSelectedFile());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        else if(ae.getSource()==submitButton2){
            /*String imagePath = "PieChart.jpeg";
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File(imagePath));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);*/
            window=new Window();
            window.setVisible(true);

        }
    }
    /**
     * Method describing the start of the application.
     * @param args console arguments.
     */
    public static void main(String args[])
    {
        //System.out.println("Start Aplikacji");
        MyWindow f = new MyWindow();
        f.setVisible(true);

    }
}