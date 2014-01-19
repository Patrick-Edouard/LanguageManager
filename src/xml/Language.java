package xml;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import gui.LanguageGUI;
import xml.model.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class Language {
	static final public String xmlPath = "lang.xml";
	private String lang;
    private Element root;
	private Element language;
	
	/**
	 * <p>
	 * <b>Object : xml.Language</b></br>
	 * 
	 * xml.Language la = new xml.Language();	 * la.getText(key);
	 * 
	 * </br>
	 * You can also launch the translate tool using the argument "lang" or using and instance of gui.LanguageGUI.</br>
	 * You have to add at least one language node manually in the xml file (defined by xml.Language.xmlPath).</br>
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
	 * The language is set at the creation of the instance of xml.Language.</br>
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
	 * Open the xml File and get the element corresponding to the given language in the variable language.</br>
	 *
	 * </b>
	 */
	private void openLangFile()
	{
		SAXBuilder sxb = new SAXBuilder();
		try {
			this.root = sxb.build(new File(Language.xmlPath)).getRootElement();
			this.openLocaleLang();
		} catch (JDOMException e) {
			e.printStackTrace();
		}catch(IOException e){
            e.printStackTrace();
        }
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
	 * Just create a new gui.LanguageGUI
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
