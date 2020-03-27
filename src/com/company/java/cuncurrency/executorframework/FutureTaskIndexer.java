package com.company.java.cuncurrency.executorframework;


import com.company.java.concurrency.index.project.HttpConnect;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.*;

public class FutureTaskIndexer {


    ExecutorService downloader = Executors.newFixedThreadPool(2);
    ExecutorService indexer = Executors.newFixedThreadPool(2);

    Deque<Weblink> deque = new ArrayDeque<>();


    private static final long Time_Frame = 2000000000L;

    private static class Weblink {
        private long id;
        private String title;
        private String url;
        private String host;

        private String htmlPage;

        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getHost() {
            return host;
        }
        public void setHost(String host) {
            this.host = host;
        }
        public String getHtmlPage() {
            return htmlPage;
        }
        public void setHtmlPage(String htmlPage) {
            this.htmlPage = htmlPage;
        }
    }


    public static class Downloader<T extends Weblink> implements Callable<T> {

       private T weblink;

       public Downloader(T weblink)
       {
           this.weblink=weblink;
       }

        @Override
        public T call() throws Exception {

           String htmlPage = HttpConnect.downloading(weblink.getUrl());

            System.out.println("Dowloading" + ((htmlPage!=null)?" Success":"Failed"));

           weblink.setHtmlPage(htmlPage);

            return weblink;
        }


    }


    public static class Indexer1 implements Runnable{
        private Future<Weblink> weblink;

        private long endTime;

        public Indexer1(Future<Weblink> weblink, long endTime) {
            this.weblink = weblink;
            this.endTime = endTime;
        }

        @Override
        public void run() {


            try {
                if (weblink.get().getHost() != null) {

                    Weblink link = weblink.get(endTime, TimeUnit.NANOSECONDS);


                    index(link.getHtmlPage());
                }
            }catch(ExecutionException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                weblink.cancel(true);  // remember to add this cancel statement
                e.printStackTrace();
            }catch(TimeoutException e){
                e.printStackTrace();
            }

        }

        private void index(String htmlPage) {

            System.out.println("indexing is " + ((htmlPage!=null)?"Success":" not Successfule"));

            if(htmlPage !=null)
            {
                try {
                    System.out.println(weblink.get().getId() + "  Indexed !");
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void add(Weblink weblink){
        deque.add(weblink);
    }

    public void go(){

        List<Future<Weblink>> futures = new ArrayList<>();

        while(deque.size()>0)
        {
            Weblink weblink = deque.remove();

            Future<Weblink> future = downloader.submit(new Downloader<>(weblink));

            futures.add(future);


        }

        for(Future<Weblink> future: futures){

            indexer.execute(new Indexer1(future,Time_Frame));

        }

        downloader.shutdown();
        indexer.shutdown();



    }

    public Weblink createWeblink(long id, String title, String url, String host) {
        Weblink weblink = new Weblink();
        weblink.setId(id);
        weblink.setTitle(title);
        weblink.setUrl(url);
        weblink.setHost(host);
        return weblink;
    }

    public static void main(String[] args) {
        FutureTaskIndexer futureTimedGetIndexer = new FutureTaskIndexer();
        futureTimedGetIndexer.add(futureTimedGetIndexer.createWeblink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com"));
        futureTimedGetIndexer.add(futureTimedGetIndexer.createWeblink(2001, "How do I import a pre-existing Java project into Eclipse and get up and running?", "http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running", "http://www.stackoverflow.com"));
        futureTimedGetIndexer.add(futureTimedGetIndexer.createWeblink(2002, "Interface vs Abstract Class", "http://mindprod.com/jgloss/interfacevsabstract.html", "http://mindprod.com"));
        futureTimedGetIndexer.add(futureTimedGetIndexer.createWeblink(2004, "Virtual Hosting and Tomcat", "http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html", "http://tomcat.apache.org"));
        futureTimedGetIndexer.go();
    }

}
