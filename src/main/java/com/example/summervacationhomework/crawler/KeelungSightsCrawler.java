package com.example.summervacationhomework.crawler;
import com.example.summervacationhomework.dao.SightRepo;
import com.example.summervacationhomework.model.Sight;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.*;
import java.util.*;

public class KeelungSightsCrawler {
    Sight[] result = new Sight[50];
    String[] targetUrl;
    String[] targetZoneName;
    List<String> sightarray= new ArrayList();
    List<Sight> sightList = new ArrayList();
    int num = 0;

    public List<Sight> getSightList() {
        return sightList;
    }

    @Autowired
    private SightRepo sightRepo ;


    public void createSight(Sight request) {
        if(sightRepo==null) System.out.println("error");
        sightRepo.insert(request);
    }
    public KeelungSightsCrawler(){
        try {
            num = 0;
            //找出區域的連結 EX:中正區 七堵區等等
            Document doc = Jsoup.connect("https://www.travelking.com.tw/tourguide/taiwan/keelungcity/").get();
            Elements zoneTemp = doc.getElementsByClass("text");
            zoneTemp = zoneTemp.attr("class","box_ss");
            Elements zoneLinks = zoneTemp.select("a[href]");
            targetUrl=new String[zoneLinks.size()];
            targetZoneName=new String[zoneLinks.size()];
            int numUrl=0;
            for (Element link : zoneLinks) {
                targetZoneName[numUrl]=link.text();
                targetUrl[numUrl] = link.attr("abs:href");
                numUrl++;
            }
            for(int n=0;n<numUrl;n++){
                //進入想要的目標區域
                Document nextDoc = Jsoup.connect(targetUrl[n]).get();

                //取得景點連結
                Elements sightLinks = nextDoc.getElementsByClass("box");
                Elements body = sightLinks.select("ul");
                Elements urlBody = body.select("a[href]");
                for (Element link :  urlBody) {
                    sightarray.add(link.attr("abs:href"));
                }

                //遍布每個景點連結並取得需要的屬性
                for(int i = 0; i < sightarray.size(); i++) {

                    String sightUrl = sightarray.get(i);
                    Document sightDoc = Jsoup.connect(sightUrl).get();

                    Sight temp = new Sight();

                    Elements sightTitle = sightDoc.getElementsByClass("h1");
                    temp.setSightName(sightTitle.attr("property","dc:title").text());

                    temp.setZone(targetZoneName[n]);

                    Elements sightCategory = sightDoc.getElementsByClass("point_type");
                    temp.setCategory(sightCategory.attr("property","rdfs:label").text().substring(6));

                    //由於上面的圖片連結是相對的所以需轉換(參考網路 data-src  img src)
                    Element sightImageUrl = sightDoc.getElementsByClass("gpic").first();
                    if(sightImageUrl==null){
                        temp.setPhotoURL("null");
                    }
                    else {
                        Element element = sightImageUrl.select("img[src]").first();
                        Attributes node=element.attributes();
                        Iterator<Attribute> iterator = node.iterator();
                        while (iterator.hasNext()) {
                            Attribute attribute = iterator.next();
                            String key = attribute.getKey();
                            //属性中包含“src”字符串，但不是src的属性
                            if (!key.equals("src") && key.indexOf("src") != -1) {
                                String otherSrc = attribute.getValue();
                                temp.setPhotoURL(otherSrc);
                                break;
                            }
                        }
                    }
                    Element sightText = sightDoc.getElementsByClass("text").first();
                    temp.setDescription(sightText.attr("id","po").text());

                    Elements sightAddress = sightDoc.getElementsByClass("address");
                    temp.setAddress(sightAddress.attr("property","vcard:street-address").text().substring(4));

                    sightList.add(temp);
                }
            }
        } catch (IOException e) {
            System.out.printf(e.getMessage());
        }

    }
   /* public Sight[] getItems(String zone) {
        List<Sight> sightListForZone = new ArrayList();
        for (int k = 0; k < sightList.size(); k++) {
            if (sightList.get(k).getZone().equals(zone)) {
                sightListForZone.add(sightList.get(k));
            }
        }
        if (sightListForZone.size() == 0) return null;
        else {
            Sight[] tempArray = new Sight[sightListForZone.size()];
            for (int i = 0; i < sightListForZone.size(); i++) {
                tempArray[i] = sightListForZone.get(i);
            }

            return tempArray;
        }
    }*/

    /*public static void main(String[] args) throws Exception {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        SightService service = new SightService();
        Sight[] tmp = crawler.getItems("中正區");
        if(tmp == null){
            System.out.println("Wrong zone");
        }
        else {
            for (Sight temp : tmp) {
                System.out.println(temp);
            }
        }
    }*/

}
