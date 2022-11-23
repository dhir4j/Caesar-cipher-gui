package multifile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class gui extends JFrame implements ActionListener {
	public static final String alpha = "abcdefghijklmnopqrstuvwxyz";

	int[][] puzzle = new int[9][9];
	FlowLayout experimentLayout = new FlowLayout();
	private JPanel controls = new JPanel();
	private JPanel write = new JPanel();
	JTextArea canvas = new JTextArea();
	JTextField CipherKeyIn = new JTextField(10);
	JFileChooser fc = new JFileChooser();
	private JButton decrypt = new JButton("Decrypt Text");
	private JButton encrypt = new JButton("Encrypt text");
	private JButton save = new JButton("Save");
	private JButton load = new JButton("Load");
	private JButton rules = new JButton("Rules");
	JScrollPane sp = new JScrollPane(write);
	JScrollBar bar = new JScrollBar();
	String Location = null;  
	boolean justRead = false;
	boolean justSolved = false;
	boolean justGen = false;
	String Error = "NA";
         
    public gui() {
	    getContentPane().setLayout(new BorderLayout());
	    encrypt.addActionListener(this);
	    decrypt.addActionListener(this);
	    save.addActionListener(this);
	    load.addActionListener(this);
	    rules.addActionListener(this);
	    controls.add(encrypt);
	    controls.add(decrypt);
	    controls.add(save);
	    controls.add(load);
	    
	    JScrollPane scrollPane = new JScrollPane(canvas);
	    setPreferredSize(new Dimension(6000, 6000));	   
	    JPanel pane = new JPanel();
	    JPanel encryptionControls = new JPanel();
	    encryptionControls.setLayout(new FlowLayout());
	    pane.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    if (true) {
	    //natural height, maximum width
	    c.fill = GridBagConstraints.HORIZONTAL;
	    }
	 
	    JLabel cipherKey = new JLabel("Cipher Key:");
	   //c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.NORTH;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(10,0,0,0);
	    pane.add(cipherKey, c);
	    CipherKeyIn = new JTextField(10);
	    // c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.BASELINE;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 0;
	    c.insets = new Insets(10,10,0,0);
	    pane.add(CipherKeyIn, c);
	    encryptionControls.add(pane);
	    
        add(scrollPane, BorderLayout.CENTER);    
        canvas.setBorder(BorderFactory.createTitledBorder("Text Input"));
        canvas.setPreferredSize(new Dimension (250, 250));
        //this will enable the word wrapping feature
        canvas.setLineWrap(true);
        canvas.setWrapStyleWord(true);
       //write.add(canvas);
       //getContentPane().add("North", write);
        getContentPane().add("South", controls);
        getContentPane().add("East", encryptionControls);
        
        getContentPane().setSize(400, 700);

    } // init()
	
	
	public void actionPerformed(ActionEvent e) {
		String encryptedText;
		String plainText;
		String currentKey = CipherKeyIn.getText();
		int shiftKey = Integer.parseInt(currentKey);
		if(e.getSource() == encrypt){
            plainText = "";
			plainText = canvas.getText();
			//plainText = plainText.replaceAll(" ", "");
			encryptedText = "";
			cipherEnc encryptor = new cipherEnc();
			encryptedText = encryptor.enc(plainText, shiftKey);
			canvas.setText(encryptedText);
			
		}if(e.getSource() == decrypt){
			
			encryptedText = canvas.getText();
			cipherDec decryptor = new cipherDec();
			plainText = decryptor.dec(encryptedText, shiftKey);
			canvas.setText(plainText);
		}else if(e.getSource() == load){
			
			int returnVal = fc.showOpenDialog(gui.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String local = file.getAbsolutePath();
                String loaded = "";
                try {
					loaded =readFile(local, Charset.defaultCharset());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                canvas.setText(loaded);
            }
			
		}
		else if(e.getSource() == save){
			JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new File(""));
		    int retrival = chooser.showSaveDialog(null);
		    if (retrival == JFileChooser.APPROVE_OPTION) {
		        try(FileWriter fw = new FileWriter(chooser.getSelectedFile())) {
		            fw.write(canvas.getText());
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		}
		
	}

	static String readFile(String path, Charset encoding)throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	public static void main(String[] args){
		gui maingui = new gui();
            
        maingui.setSize(750,500);
        maingui.setVisible(true);
        maingui.addWindowListener(new WindowAdapter() {// Quit the application
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
//@dhir4j <3!