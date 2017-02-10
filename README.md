Engaged Church Management Application Web
=========================================
Application for the management and visualization of the Engaged Church app as a whole, including insights into the mobile app as well.

Running the App
==
Run 'mvn -Pproduction-mode jetty:run' to run in a local jetty. Open in localhost:8080.

Run the Maven 'install' target and deploy the resulting WAR file to your Java application server.

You need a license for Vaadin Charts to compile the widgetset. You can get a free 30 day trial license by going to https://vaadin.com/directory#addon/vaadin-charts and clicking the orange "Free trial key" button. It gives you a trial key. [See the help section](https://vaadin.com/directory/help/installing-cval-license) which shows you how to install the key.

Basically you need to create a file name ".vaadin.charts.developer.license" in your HOME directory, and place the key there.  This dependency will soon be gone.

The application uses the [Vaadin Charts 2](https://vaadin.com/charts) add-on, which is released under the Commercial Vaadin Addon License: https://vaadin.com/license/cval-3
=======
