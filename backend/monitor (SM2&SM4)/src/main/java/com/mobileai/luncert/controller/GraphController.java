package com.mobileai.luncert.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.mobileai.luncert.annotation.AdminAuthCheck;
import com.mobileai.luncert.service.VirusGraph;
import com.mobileai.luncert.utils.SecurityTransport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("graph")
public class GraphController {

    @Autowired
    private VirusGraph virusGraph;

    @AdminAuthCheck
    @RequestMapping("getVirusGraph")
    public String getVirusGraph(HttpSession session) throws IOException {
        SecurityTransport st = (SecurityTransport)session.getAttribute("st");
        return st.response(200, null, virusGraph.fetchAllDerivation());
    }

}