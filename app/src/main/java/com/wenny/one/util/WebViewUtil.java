package com.wenny.one.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenny on 2016/11/9.
 */

public class WebViewUtil {

    public static void subWebView(WebView webView, String html) {
        if (html != null) {
            String imgStyle = "<style> p{font-size:18px;line-height:36px;} img{ max-width:100%;height:auto;} </style>";
            String reg = "style=\"([^\"]+)\"";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(html);
            String str = matcher.replaceAll("") + imgStyle;
            webView.getSettings().setBlockNetworkImage(true);
            String h = str.replaceAll("<p><img", "<p align=center><img");
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); //极速渲染...
            webView.loadDataWithBaseURL(null, h, "text/html", "UTF-8", null);
        }
    }
}
