R13_QueryCompletion
====================

Info retrieval query completion project

Install solr:
=============

I dont know if we need to do this but i installed solr locally.
in ubuntu it was as simple as:

apt-get install solr-tomcat

Then you can access it with
localhost:8080/solr

Install solr in eclipse:
===========================

If you haven't done it yet, get the git repo with clone.

Open the file IR13_IR13_QueryCompletionPlugIn as a new project with existing sources

install jetty
-------------

First thing first, you need to install this plug-in for eclipse.

Just follow this tutorial:
http://code.google.com/p/run-jetty-run/wiki/GettingStarted

Configure and run the whole thing
---------------------------------

Again, follow this tutorial: 
http://searchhub.org/2009/09/02/setting-up-apache-solr-in-eclipse/

MAKE SURE TO INSTALL THE 3.6 VERSION OF SOLR

It is quite straight forward, just remember that the project called "testsolr" in this tutorial is in fact IR13_QueryCompletionPlugIn, and the most important thing is, by the end of the tutorial you must have solr and webapp folder fully completed.

One important thing thought, you may notice that folder webapp and solr are already here in the git repository. Basically what you have to do is to fill the webapp/WEB-INF/lib folder with required library and configure jetty in the right way.

The little plug-in
------------------

From now, I'll assume that you have made solr work on your computer.

Look at the source code in your project, there is a package called "pl.solr.analysis". Create a jar of this package and put the jar in webapp/WEB-INF/lib.

now, restard the solr server

to check if the plugin is active go to http://localhost:8983/solr/admin/analysis.jsp

select the field type as "text_reversed", set a given field value: let's say "hello world". Click on analyse, if it sends back "olleh dlrow", you're good ! :)

How does this thing work
-------------------------

What we have done, except for the coding of classes and including the jar in the right place was modifying the schema.xml file. We have added a field type which is "text_reversed" using our new plugin implementation. 


Autocompletion in solr
======================
For now a functionnality has been activated in solr. Here is a description of how it works

Data structure
--------------
In order to test this functionnality, documents must be added to data set.
Those documents must contains at least a field named "name". The auto-completion (= search of words regarding a given prefix) will use the field "name".

Example of usage
-----------------
*Had some documents to solr's datas
* Once data set is complete (ie. you have added every of your documents) send the following command to solr: /suggest?spellcheck.build=true
* Send the following command to solr: /suggest?q=prefix in order to get every words starting with prefix in every fields "name" of all documents
