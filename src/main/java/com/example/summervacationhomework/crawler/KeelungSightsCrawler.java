package com.example.summervacationhomework.crawler;
import com.example.summervacationhomework.model.Sight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;

public class KeelungSightsCrawler {
    Sight[] result = new Sight[50];
    String targetUrl;
    List<String> sightarray= new ArrayList();
    List<Sight> sightList = new ArrayList();
    int num = 0;

    public Sight[] getItems(String zone) {
        try {
            num = 0;
            //找出區域的連結 EX:中正區 七堵區等等
            Document doc = Jsoup.connect("https://www.travelking.com.tw/tourguide/taiwan/keelungcity/").get();
            Elements zoneTemp = doc.getElementsByClass("text");
            zoneTemp = zoneTemp.attr("class","box_ss");
            Elements zoneLinks = zoneTemp.select("a[href]");
            for (Element link : zoneLinks) {
                if(link.text().equals(zone)){
                    targetUrl = link.attr("abs:href");
                    break;
                }
            }

            //進入想要的目標區域
            Document nextDoc = Jsoup.connect(targetUrl).get();

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

                temp.setZone(zone);

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
            Sight[] tempArray = new Sight[sightList.size()];
            for(int i = 0; i < sightList.size();i++){
                tempArray[i] = sightList.get(i);
            }
            return tempArray;

        } catch (IOException e) {
            System.out.printf(e.getMessage());
            return null;
        }
    }

}
