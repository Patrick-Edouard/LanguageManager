Language Manager
================

### About

This programm is a simple way to use a translation and retriew strings resources in your own software.



### Installation

You will need to import it to your project. Four ways are possible :

* Build the jar, store it to your nexus (or other jar repository) and add a maven dependencie

* Build the jar and add a local maven dependencie

* Build the jar and add it as external library in your ide

* Add directly the sources to your projet

I would take the firsts ones ;)


### Usage

You can either directly write into the lang.xml file to create your key-value and then use them in the java program or use the GUI to create those key-value.

In your Java code you need to instantiate a Language object and when you need to retriew a string use that getText(key); method.

Given this lang.xml :

    <languages>
      <en-EN>
        <your_key>Hello World</your_key>
        <!-- put other key-value here -->
      </en-EN>
    </languages>

Executing this java code :

    Language la = new Language();
    System.out.println(la.getText("your_key"));
 
Will display :

> Hellop World
