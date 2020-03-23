package com.company.java.concurrency.index.project;


import com.company.java.concurrency.index.project.NaiveIndexer;

public class Main {

    public static void main(String[] args) throws Exception {


        /*
        //htmlPage variable of WebIndex is volatile
        NaiveIndexer naiveIndexer = new NaiveIndexer();
        naiveIndexer.add(naiveIndexer.createWeblink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com"));
        naiveIndexer.add(naiveIndexer.createWeblink(2001, "How do I import a pre-existing Java project into Eclipse and get up and running?", "http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running", "http://www.stackoverflow.com"));
        naiveIndexer.add(naiveIndexer.createWeblink(2002, "Interface vs Abstract Class", "http://mindprod.com/jgloss/interfacevsabstract.html", "http://mindprod.com"));
        naiveIndexer.add(naiveIndexer.createWeblink(2004, "Virtual Hosting and Tomcat", "http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html", "http://tomcat.apache.org"));
        naiveIndexer.go();

         */

        //wait and notify for happens before relationship

        WaitNotifyIndexer waitNotifyIndexer = new WaitNotifyIndexer();
        waitNotifyIndexer.add(waitNotifyIndexer.createWeblink(2000, "Taming Tiger, Part 2", "https://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com"));
        waitNotifyIndexer.add(waitNotifyIndexer.createWeblink(2001, "How do I import a pre-existing Java project into Eclipse and get up and running?", "https://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running", "http://www.stackoverflow.com"));
        waitNotifyIndexer.add(waitNotifyIndexer.createWeblink(2002, "Interface vs Abstract Class", "http://mindprod.com/jgloss/interfacevsabstract.html", "http://mindprod.com"));
        waitNotifyIndexer.add(waitNotifyIndexer.createWeblink(2004, "Virtual Hosting and Tomcat", "http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html", "http://tomcat.apache.org"));
        waitNotifyIndexer.go();










    }
}
