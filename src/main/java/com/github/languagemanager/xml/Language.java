package com.github.languagemanager.xml;

import com.github.languagemanager.gui.LanguageGUI;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.net.URL;
import java.net.URISyntaxException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Language {
	static final public String xmlPath = "lang.com.github.languagemanager.xml";
	private String lang;
    private Element root;
	private Element language;
	
	/**
	 * <p>
	 * <b>Object : com.github.languagemanager.xml.Language</b></br>
	 * 
	 * com.github.languagemanager.xml.Language la = new com.github.languagemanager.xml.Language();	 * la.getText(key);
	 * 
	 * </br>
	 * You can also launch the translate tool using the argument "lang" or using and instance of com.github.languagemanager.gui.LanguageGUI.</br>
	 * You have to add at least one language node manually in the com.github.languagemanager.xml file (defined by com.github.languagemanager.xml.Language.xmlPath).</br>
	 * 
	 * The language node needs to be formated like a <b>Locale LanguageTag</b> : "en-EN" or "fr-FR".
	 * </p>
	 */
	public Language()
	{		
		this.openLangFile();
	}


    public Element getRoot() {
        return root;
    }

    public Element getLanguage() {
        return language;
    }


    /**
	 * <p>
	 * <b>Method : getText</b></br>
	 * This method give you the text corresponding to the given key.</br>
	 * The language is set at the creation of the instance of com.github.languagemanager.xml.Language.</br>
	 * If the given key doesn't exist in the Locale language, this method will return the English key. If there are no English key, the method will return " ".
	 * @param key The key affiliate to a following text.
	 * @return the text corresponding of the given key, as a String.
	 * <p>
	 */
	public String getText(String key)
	{
		Element tmp = this.language.getChild(key);
		if(tmp != null)
		{
			return tmp.getTextTrim();
		}
		tmp = this.root.getChild("en-EN").getChild(key);
		if(tmp != null)
		{
			return tmp.getTextTrim();
		}
		return " ";
	}
	
	/**
	 * <p>
	 * <b>Method : getLang</b></br>
	 * 
	 * @return the current language as a String
	 * </p>
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 * <p>
	 * <b>Method : setLang</b></br>
	 * Set a new language. You must use a valid Locale parameter.</br>
	 * <b>Ex:</b> Locale.FRENCH; Locale.ENGLISH.</br>
	 * If the given language is not set in the XML file, English will be set.
	 * @param newLocale the new locale language.
	 * </b>
	 */
	public void setLang(Locale newLocale) {
		
		Locale.setDefault(newLocale);
				
		this.lang = newLocale.toLanguageTag();
		if(this.root.getChild(lang) != null)
		{
			this.language=this.root.getChild(lang);
		}
		else
		{
			this.language=this.root.getChild("en-EN");
		}
	}
	
	/**
	 * <p>
	 * <b>Method : openLocaleLang</b></br>
	 * Open the default Locale language.</br>
	 * If the default language isn't supported, English will be loaded.
	 * </b>
	 */
	public void openLocaleLang() {
		
		Locale newLocale=Locale.getDefault();
				
		this.lang = newLocale.toLanguageTag();
		if(this.root.getChild(lang) != null)
		{
			this.language=this.root.getChild(lang);
		}
		else
		{
			this.language=this.root.getChild("en-EN");
		}
	}
	
	/**
	 * <p>
	 * <b>Method : openLangFile</b></br>
	 * Open the com.github.languagemanager.xml File and get the element corresponding to the given language in the variable language.</br>
	 *
	 * </b>
	 */
	private void openLangFile()
	{
		SAXBuilder sxb = new SAXBuilder();
		try {
            InputStream is = getClass().getResourceAsStream("/lang.xml");
			this.root = sxb.build(is).getRootElement();
			this.openLocaleLang();
		} catch (JDOMException e) {
			e.printStackTrace();
		}catch(IOException e){
            e.printStackTrace();
        }
	}

    /**
     * <p>
     * <b>Method : saveXML</b></br>
     * Save the XML language file at his path (defined in com.github.languagemanager.xml.Language.xmlPath)
     * <p>
     */
    public void saveXML()
    {
        try
        {
            URL fileUrl = getClass().getResource("/lang.xml");

            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(this.root, new FileOutputStream(fileUrl.toURI().getPath()));
        }
        catch (java.io.IOException e){}
        catch (java.net.URISyntaxException e){}
    }
	
	/**
	 * <p>
	 * <b>Method : editLang</b></br>
	 * Open the GUI to edit the lang files.
	 * </p>
	 */
	public void editLang()
	{
		this.createEditDialog();
	}
	
	/**
	 * <p>
	 * <b>Method : createEditDialog</b></br>
	 * Just create a new com.github.languagemanager.gui.LanguageGUI
	 * </p>
	 */
	private void createEditDialog()
	{
		new LanguageGUI(this);
	}
	
	public static void main(String Args[])
	{
		Language la = new Language();
		la.editLang();
	}
}
