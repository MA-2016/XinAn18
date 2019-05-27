package com.mobileai.luncert.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

import com.mobileai.luncert.model.mysql.LocationMapper;
import com.mobileai.luncert.model.mysql.entity.Location;
import com.mobileai.luncert.model.mysql.entity.Position;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IPUtil {

    private static List<Location> foreginAddress;

    /**
     * 小端模式
     */
    public static String ipToString(int ip) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) builder.append('.').append((ip >> (8 * i) & 0xff));
        return builder.substring(1);
    }

    public static byte[] ipToByte(String ip) {
        String[] buf = ip.split("\\.");
        byte[] ret = new byte[4];
        for (int i = 0; i < 4; i++) ret[i] = (byte) (Long.valueOf(buf[i]) << (8 * i));
        return ret;
    }

    public static int ipToInt(String ip) {
        String[] buf = ip.split("\\.");
        int ret = 0;
        for (int i = 0; i < 4; i++) ret += Long.valueOf(buf[i]) << (8 * i);
        return ret;
    }

    public static Position queryPos(int ip) throws MalformedURLException, IOException { return queryPos(ipToString(ip)); }

    public static Position queryPos(String ip) throws MalformedURLException, IOException {
        /*
        // 百度 + 数据库1
        String rep = Request.get(new URL("https://api.map.baidu.com/location/ip?ip=" + ip + "&ak=LiWWApewhf8GRlu4VZGIek2MHKM7UIS6"));

        JSONObject content = JSONObject.fromObject(rep).getJSONObject("content");

        if (!content.isNullObject()) {
            JSONObject point = content.getJSONObject("point");
    
            return new Position(
                Float.valueOf(point.getString("x")),
                Float.valueOf(point.getString("y")),
                "China",
                content.getString("address"));
        }
        else {
            if (foreginAddress == null) {
                foreginAddress = MySQLUtil.open().getMapper(LocationMapper.class).fetchAll();
            }
            Location location = foreginAddress.get(new Random().nextInt(foreginAddress.size()));
            return new Position(
                Float.valueOf(location.getLatitude()),
                Float.valueOf(location.getLongitude()),
                location.getCountry(),
                location.getCapital());
        }
        */

        // gpsspg + 数据库
        Document doc = Jsoup.connect("http://www.gpsspg.com/ip/?q=" + ip).get();
        Element elems = doc.select(".mt20").get(0);
        String[] desc = elems.select(".fcg").html().split(" ");
        if (desc.length > 2) {
            String country = desc[0];
            String province = desc[1];
            String[] pos = elems.select("a").html().split(",");
            return new Position(Float.valueOf(pos[0]), Float.valueOf(pos[1]), country, province);
        }
        else {
            if (foreginAddress == null) {
                foreginAddress = MySQLUtil.open().getMapper(LocationMapper.class).fetchAll();
            }
            Location location = foreginAddress.get(new Random().nextInt(foreginAddress.size()));
            return new Position(
                Float.valueOf(location.getLatitude()),
                Float.valueOf(location.getLongitude()),
                location.getCountry(),
                location.getCapital());
        }
    }

}