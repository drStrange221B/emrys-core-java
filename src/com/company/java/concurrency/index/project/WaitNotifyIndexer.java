package com.company.java.concurrency.index.project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.Deque;

public class WaitNotifyIndexer {

    public static Deque<NaiveIndexer.Weblink> queue = new ArrayDeque<>();

    public static class Weblink{
        private long id;
        private String title;
        private String url;
        private String host;

        private  String htmlPage;

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

    public static class Downloader implements Runnable{
        private Weblink weblink;

        public Downloader(Weblink weblink)
        {
            this.weblink = weblink;
        }

        @Override
        public void run() {

            try {
                synchronized (weblink) {
                    String htmlPage = HttpConnect.downloading(weblink.url);
                    weblink.setHost(htmlPage);
                    weblink.notifyAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public static class Indexer implements Runnable{
        private Weblink weblink;

        public Indexer(Weblink weblink){
            this.weblink = weblink;
        }

        @Override
        public void run() {

            String htmlPage = weblink.getHtmlPage();

            synchronized (weblink) {

                while (htmlPage != null) {

                    System.out.println(weblink.getId() + " still downloading ......");

                    try {
                        weblink.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    htmlPage = weblink.getHtmlPage();

                    System.out.println(weblink.getId() + " has awakened!");
                }

                index(htmlPage);
            }



        }

        private void index(String htmlPage)
        {
            if(htmlPage !=null){
                System.out.println(weblink.getId() + ": Indexed");
            }
        }

    }

    public  void go(){
        while(queue.size()>0){
            NaiveIndexer.Weblink weblink = queue.remove();

            Thread downloadThread = new Thread(new NaiveIndexer.Downloader(weblink));
            Thread indexThread = new Thread(new NaiveIndexer.Indexer(weblink));


            downloadThread.start();
            indexThread.start();
        }
    }

    public NaiveIndexer.Weblink createWeblink(long id, String title, String url, String host) {
        NaiveIndexer.Weblink weblink = new NaiveIndexer.Weblink();
        weblink.setId(id);
        weblink.setTitle(title);
        weblink.setUrl(url);
        weblink.setHost(host);
        return weblink;
    }

    public void add(NaiveIndexer.Weblink link) {
        queue.add(link);
    }

}
