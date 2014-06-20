package com.github.languagemanager.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import com.github.languagemanager.xml.Language;


public class LanguageGUI extends JDialog implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel titre1;
	private JLabel titre2;
	private JLabel titre3;
	private JButton buttonAdd;
	private JButton buttonDelete;
	private JButton buttonNewLang;
	private JButton buttonSaveLang;
	
	JTextField langField;
	
	private JDialog newLangDialog;
	
	private JComboBox<Object> keyList;
	private JComboBox<Object> langList;
	
	private JTextArea content;
	private JTextArea keySelected;
	
	private Language la;
	
	/**
	 * <p>
	 * <b>Object  : com.github.languagemanager.gui.LanguageGUI</b></br>
	 * This Object is used to edit with a graphical user interface the project lang file (lang.com.github.languagemanager.xml).</br>
	 * You may edit a specified key from the file, for each languages. You can also create a new key using this tool.</br>
	 * @param language The instance of com.github.languagemanager.xml.Language used in your application.
	 * </p>
	 */
	public LanguageGUI(Language language)
	{
		this.la = language;
		this.createWindow();
	}
	
	/**
	 * <p>
	 * <b>Method : createWindow</b></br>
	 * Create the GUI.
	 * </p>
	 */
	private void createWindow()
	{
		this.setBounds(200, 200, 700, 500);
		this.setTitle("com.github.languagemanager.xml.Language Editor");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.container = new JPanel();
		
		this.keyList = new JComboBox<Object>(la.getLanguage().getChildren().toArray());
		this.keyList.addActionListener(this);
		this.keyList.setActionCommand("keyListClicked");
		this.langList = new JComboBox<Object>(la.getRoot().getChildren().toArray());
		this.langList.addActionListener(this);
		
		this.buttonAdd = new JButton ("+");
		this.buttonAdd.addActionListener(this);
		this.buttonDelete = new JButton ("-");
		this.buttonDelete.addActionListener(this);
		this.buttonNewLang = new JButton (la.getText("lang_newlanguage"));
		this.buttonNewLang.addActionListener(this);
		
		this.titre1 = new JLabel("Key");
		this.titre2 = new JLabel("lang");
		this.titre3 = new JLabel("Content");
		
		this.content = new JTextArea(((Element) keyList.getSelectedItem()).getText(), 20, 60);
		this.content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.content.setLineWrap(true);
		this.content.setWrapStyleWord(true);
		this.keySelected = new JTextArea(((Element) keyList.getSelectedItem()).getName(),1,10);
		this.keySelected.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.keySelected.setLineWrap(true);
		
		this.container.add(this.titre2);
		this.container.add(this.langList);
		this.container.add(this.titre1);
		this.container.add(this.keyList);
		this.container.add(this.keySelected);
		this.container.add(this.titre3);
		this.container.add(this.content);
		this.container.add(this.buttonAdd);
		this.container.add(this.buttonDelete);
		this.container.add(this.buttonNewLang);
		
		this.setContentPane(this.container);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.keyList))
		//if(e.getActionCommand().equals("keyListClicked"))
		{
			this.keyListClicked();
		}
		if(e.getSource().equals(this.langList))
		{
			this.langListClicked();
		}
		if(e.getSource().equals(this.buttonAdd))
		{
			this.buttonAddClicked();
		}
		if(e.getSource().equals(this.buttonDelete))
		{
			this.buttonDeleteClicked();
		}
		if(e.getSource().equals(this.buttonNewLang))
		{
			this.buttonNewLangClicked();
		}
		if(e.getSource().equals(this.buttonSaveLang))
		{
			this.buttonSaveLangClicked();
		}
	}
	
	/**
	 * <p>
	 * <b>Method : refreshKeyList</b></br>
	 * This method refresh the key list with actual values of the XML file for the following language selected.
	 * </p>
	 */
	private void refreshKeyList()
	{
		String lang = ((Element) langList.getSelectedItem()).getName();
		
		String[] newLocale = lang.split("-", 2);
		
		la.setLang(new Locale(newLocale[0],newLocale[1]));
		
		keyList.removeActionListener(this);	//on enleve le listener pour ne pas cr�er de nullpointer dans l'actionperformed
		keyList.removeAllItems();
		for(Object item : la.getLanguage().getChildren())
		{
			keyList.addItem(item);
		}
		
		keyList.updateUI();
		keyList.addActionListener(this);
		
		//met � jours les zone de text
		this.keySelected.setText(((Element) keyList.getSelectedItem()).getName());
		this.content.setText(((Element) keyList.getSelectedItem()).getText());
	}
	
	/**
	 * <p>
	 * <b>Method : keyListClicked</b></br>
	 * This method is called when keyList is performed.
	 * </p>
	 */
	private void keyListClicked()
	{
		this.keySelected.setText(((Element) keyList.getSelectedItem()).getName());
		this.content.setText(((Element) keyList.getSelectedItem()).getText());
	}
	
	/**
	 * <p>
	 * <b>Method : langListClicked</b></br>
	 * This method is called when langList is performed.
	 * </p>
	 */
	private void langListClicked()
	{
		this.refreshKeyList();
	}
	
	/**
	 * <p>
	 * <b>Method : BouttonAddClicked</b></br>
	 * This method is called when the add button is performed.</br>
	 * This method save the current edited key in the XML file.
	 * </p>
	 */
	private void buttonAddClicked()
	{
			String key = this.keySelected.getText();
			if(key!=null)
			{
				String actualLanguage = (((Element) langList.getSelectedItem()).getName());
				
				String contentStr = content.getText();
				
				Element text = new Element(key);
				text.setText(contentStr);
				
				this.la.getRoot().getChild(actualLanguage).addContent(text);
				
				this.saveXML();
				
				this.refreshKeyList();
			}
	}
	
	/**
	 * <p>
	 * <b>Method : BouttonDeleteClicked</b></br>
	 * Delete the current key from the XML file. 
	 * </p>
	 */
	private void buttonDeleteClicked()
	{	
		String key = this.keySelected.getText();
		if(key!=null)
		{
			String actualLanguage = (((Element) langList.getSelectedItem()).getName());
			this.la.getRoot().getChild(actualLanguage).removeChild(key);
			this.saveXML();
			this.refreshKeyList();
		}
	}
	
	/**
	 * <p>
	 * <b>Method : bouttonNewLangClicked</b></br>
	 * Create a new language in the XML.
	 * </p>
	 */
	private void buttonNewLangClicked()
	{
		this.newLangDialog = new JDialog();
		newLangDialog.setSize(200,100);
		this.langField = new JTextField("                                ");
		this.buttonSaveLang = new JButton("OK");
		this.buttonSaveLang.addActionListener(this);
		this.buttonSaveLang.setSize(50, 50);
		JPanel dqsd = new JPanel();
		dqsd.add(langField);
		dqsd.add(this.buttonSaveLang);
		newLangDialog.setContentPane(dqsd);
		newLangDialog.setVisible(true);
	}
	
	/**
	 * <p>
	 * <b>Method : bouttonSaveLangClicked</b></br>
	 * 
	 * </p>
	 */
	private void buttonSaveLangClicked()
	{
		String lang = langField.getText();
		this.la.getRoot().addContent(new Element(lang));
		this.saveXML();
		this.newLangDialog.setVisible(false);
	}

	
	/**
	 * <p>
	 * <b>Method : saveXML</b></br>
	 * Save the XML language file at his path (defined in com.github.languagemanager.xml.Language.xmlPath)
	 * <p>
	 */
	private void saveXML()
	{
        this.la.saveXML();
    }
}
