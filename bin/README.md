# SBTabEditor

The available information about biological systems increased strongly in the last decade and will
increase further as more advanced methods are developed. To store and distribute computer models of biological systems, it
was necessary to develop consistent data formats to store this information about an organism and
the corresponding biochemical networks. One widely used format for this task is Systems Biology
Markup Language ([SBML](http://sbml.org)). However, this data format is not suitable for editing large
amounts of data. For this reason the table based data format SBtab was developed, up until now
there is no proper editor designed explicitly for SBtab, the aim of this project solves this problem
by developing an extensible editor which can read, write and save SBtab and SBML data
and display them in table form.


## How to start
In order to run JavaFX application, a valid JRE 8+ needed.

**Packaging into an executable Jar file**

To create executable jar, call `mvn jfx:jar`. Target jar-file will be placed at: target/jfx/app.

To run: `java -jar sbmltab-x.x.x-jfx.jar`


**Assembling into platform-specific bundle**

To create os-specific launcher/installer, call `mvn jfx:native`. Target folder: target/jfx/native

Running process is platform specific. Example `./sbmltab-x.x.x` (running on Unix-like system)
 

## Authors
Franziska Daumueller,
Granit Guri,
[Melina Maier](https://github.com/MelinaMaier),
[Anton Rabe](https://github.com/AntonJuliusRabe),
[Julian Wanner](https://github.com/JuliWanner),
[Mykola Zakharchuk](https://github.com/zakharc)

Supervisor:
[Dr. Andreas Draeger](https://github.com/draeger)
