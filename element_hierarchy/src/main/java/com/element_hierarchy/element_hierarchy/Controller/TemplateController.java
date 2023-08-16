package com.element_hierarchy.element_hierarchy.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Model.ResponseObject;
import com.element_hierarchy.element_hierarchy.Repository.IAdmRefBoRepo;
import com.element_hierarchy.element_hierarchy.Service.IAdmRefUiService;
import com.element_hierarchy.element_hierarchy.Service.AdmRefBo.IAdmRefBoService;
import com.element_hierarchy.element_hierarchy.Service.RefMapDetail.IRefMapDetailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping(path = "/")
@SessionAttributes("parentList")
public class TemplateController {

    @Autowired
    IAdmRefUiService admRefUiService;
    @Autowired
    IRefMapDetailService refMapDetailService;
    @Autowired
    IAdmRefBoService admRefBoService;

    @GetMapping("")
    public String showHomePage(@ModelAttribute("root") TreeSet<ADM_REF_UI> root, Model model, HttpSession session) {
        boolean isInserted = false;
        if (!model.containsAttribute("parentList")) {
            System.out.println("LẤY PARENT");
            WebClient webClient = WebClient.create();
            String url = "http://localhost:8080/api/getParentList";
            List<ADM_REF_UI> parentList = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ADM_REF_UI>>() {
                    }) // new ParameterizedTypeReference<TreeSet<ADM_REF_UI>>() {}
                    .block();
            model.addAttribute("parentList", parentList);
        }

        model.addAttribute("show", false);
        if (!root.isEmpty()) {
            isInserted = true;
            model.addAttribute("show", true);
        }

        model.addAttribute("isInserted", isInserted);
        model.addAttribute("root", root);
        session.setAttribute("root", root);

        int countColumn = admRefUiService.countLevel(root);
        int[] columns = new int[countColumn];
        for (int i = 1; i <= columns.length; i++) {
            columns[i - 1] = i;
        }
        model.addAttribute("columnNum", columns);
        return "index";
    }

    @PostMapping("insert")
    public String saveToRefMapDetail(@RequestParam("value") String[] values, HttpSession session) {
        TreeSet<ADM_REF_UI> root = (TreeSet) session.getAttribute("root");
        // List<REF_MAP_DETAIL> refMapDetails =
        // refMapDetailService.buildRefMapDetails(root, values);
        // refMapDetailService.saveToRefMapDetail(refMapDetails);
        session.removeAttribute("root");
        return "redirect:/";
    }

    @PostMapping("update")
    public String updateToRefMapDetail(@RequestParam("value") String[] values,
                                       @RequestParam("isUpdateData") boolean[] isUpdates, HttpSession session) {
        TreeSet<ADM_REF_UI> root = (TreeSet) session.getAttribute("root");
        String uiCtrlCode = root.first().getUiCtrlCode();
        List<ADM_REF_BO> admRefBos = admRefBoService.buildAdmRefBosUpdate(root, values, isUpdates);
        admRefBoService.buildBoFieldDir(admRefBos);
        List<ADM_REF_BO> noEmpty = admRefBoService.removeEmpty(admRefBos);
        admRefBoService.updateToAdmRefBo(noEmpty);
        // refMapDetailService.updateToRefMapDetail(refMapDetails);
        session.removeAttribute("root");
        return "redirect:/getRoot?uiCtrlCode=" + uiCtrlCode;
    }

    @GetMapping("getRoot")
    public String getRoot(@RequestParam String uiCtrlCode,
                          RedirectAttributes redirectAttributes) {
        ResponseObject root;
        WebClient webClient = WebClient.create();
        String url = "http://localhost:8080/api/buildRelationship/" + uiCtrlCode;
        root = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ResponseObject.class) // new ParameterizedTypeReference<TreeSet<ADM_REF_UI>>() {}
                .block();
        if (root.data == null) {
            redirectAttributes.addFlashAttribute("root", null);
        } else {
            TreeSet<ADM_REF_UI> tree = new TreeSet<>(root.data);
            redirectAttributes.addFlashAttribute("root", tree);
        }

        return "redirect:/";
    }
}
