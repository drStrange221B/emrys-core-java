package com.company.java.cuncurrency.executorframework;

import com.company.java.concurrency.index.project.HttpConnect;
import com.company.java.concurrency.index.project.NaiveIndexer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.*;

public class FutureIndexer {

    ExecutorService downloader = Executors.newFixedThreadPool(2);
    ExecutorService indexer = Executors.newFixedThreadPool(2);

     Deque<Weblink> queue = new ArrayDeque<>();

    public static class Weblink{
        private long id;
        private String title;
        private String url;
        private String host;

        private volatile String htmlPage;

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

    public static class Downloader<T extends Weblink>  implements Callable<T> {

        private T weblink;

        public Downloader(T weblink) {
            this.weblink = weblink;
        }

        String webPage;
        @Override
        public T call()  {



        try {
            webPage =  HttpConnect.downloading(weblink.getUrl());
            weblink.setHtmlPage(webPage);
        }catch(MalformedURLException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return weblink;
        }
    }

    public static class Indexer implements Runnable{

        private Weblink weblink;

        public Indexer(Weblink weblink) {
            this.weblink = weblink;
        }

        @Override
        public void run() {

            String htmlPage = weblink.getHtmlPage();


            System.out.println((htmlPage!=null)?"page is downloaded": "not downloaded");

            index(htmlPage);

        }



        private void index(String htmlPage) {

            if(htmlPage !=null) {

                System.out.println(weblink.getId() + " has been indexed !");
            }
        }
    }
    public void add(Weblink weblink){
        queue.add(weblink);
    }

    public Weblink createWeblink(long id, String title, String url, String host) {
        Weblink weblink = new Weblink();
        weblink.setId(id);
        weblink.setTitle(title);
        weblink.setUrl(url);
        weblink.setHost(host);
        return weblink;
    }

    public  void go(){

        List<Future<Weblink>>  futures = new ArrayList<>();

        while(queue.size()>0)
        {
            Weblink weblink = queue.remove();

            futures.add(downloader.submit(new Downloader<Weblink>(weblink)));
        }

        for(Future<Weblink> future:futures)
        {

            System.out.println("code has reached at  go method ....");
            try {
                indexer.submit(new Indexer(future.get()));
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }catch(ExecutionException e)
            {
                e.printStackTrace();
            }

        }

        indexer.shutdown();
        downloader.shutdown();



    }

    public static void main(String[] args) {
        FutureIndexer futureIndexer = new FutureIndexer();
        futureIndexer.add(futureIndexer.createWeblink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com"));
        futureIndexer.add(futureIndexer.createWeblink(2001, "How do I import a pre-existing Java project into Eclipse and get up and running?", "http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running", "http://www.stackoverflow.com"));
        futureIndexer.add(futureIndexer.createWeblink(2002, "Interface vs Abstract Class", "http://mindprod.com/jgloss/interfacevsabstract.html", "http://mindprod.com"));
        futureIndexer.add(futureIndexer.createWeblink(2004, "Virtual Hosting and Tomcat", "http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html", "http://tomcat.apache.org"));
//        futureIndexer.go();

        ExecutorService download = Executors.newFixedThreadPool(2);
        ExecutorService index = Executors.newFixedThreadPool(2);


        List<Future<Weblink>> futures = new ArrayList<>();
        while(futureIndexer.queue.size()>0){
            Weblink weblink = futureIndexer.queue.remove();



            futures.add(download.submit(new Downloader<Weblink>(weblink)));

        }

        futures.forEach(future-> {
            try {
                index.execute(new Indexer(future.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        download.shutdown();
        index.shutdown();


    }

}
