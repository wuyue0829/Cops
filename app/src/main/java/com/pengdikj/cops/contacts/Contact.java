package com.pengdikj.cops.contacts;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String index;
    private String name;
    private String content;
    private String phoneNumber;

    public Contact(String index, String name,String phoneNumber,String content) {
        this.index = index;
        this.name = name;
        this.content = content;
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public static List<Contact> getChineseContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("B", "办公室","18516761618",""));
        contacts.add(new Contact("F", "法制科","18516761618",""));
        contacts.add(new Contact("G", "公共信息网络安全监察科","18516761618",""));
        contacts.add(new Contact("G", "国内安全保卫大队","18516761618",""));
        contacts.add(new Contact("H", "户政科","18516761618",""));
        contacts.add(new Contact("K", "看守所","18516761618",""));
        contacts.add(new Contact("J", "计划财务科","18516761618",""));
        contacts.add(new Contact("J", "警务督察队","18516761618",""));
        contacts.add(new Contact("P", "派出所","18516761618",""));
        contacts.add(new Contact("X", "刑事警察大队","18516761618",""));
        contacts.add(new Contact("X", "巡警防暴大队","18516761618",""));
        contacts.add(new Contact("Z", "治安警察大队","18516761618",""));
        contacts.add(new Contact("Z", "政治处","18516761618",""));
        contacts.add(new Contact("Z", "指挥中心","18516761618",""));
        return contacts;
    }



    public static List<Contact> getChinese1Contacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("C", "陈志奇","18516761618","刘明盗窃案目击者"));
        contacts.add(new Contact("C", "陈建斌","18516761618","陈正强奸案目击者"));
        contacts.add(new Contact("D", "董富娟","18516761618","邢翠华诈骗案目击者"));
        contacts.add(new Contact("L", "刘建民","18516761618","吴亮盗窃案目击者"));
        contacts.add(new Contact("L", "刘芳","18516761618","张飞盗窃案目击者"));
        contacts.add(new Contact("S", "隋新民","18516761618","宋小宝抢劫案目击者"));
        contacts.add(new Contact("W", "吴江","18516761618","杨鸿儒盗窃案目击者"));
        contacts.add(new Contact("W", "吴晔","18516761618","吴越窃取国家资源案目击者"));
        contacts.add(new Contact("W", "王玉成","18516761618","刘德民抢劫案目击者"));
        contacts.add(new Contact("W", "王童","18516761618","张潇盗窃案目击者"));
        contacts.add(new Contact("X", "徐向荣","18516761618","张建民嫖娼案目击者"));
        contacts.add(new Contact("Z", "张宝华","18516761618","陈宇飞盗窃案目击者"));
        return contacts;
    }
}
