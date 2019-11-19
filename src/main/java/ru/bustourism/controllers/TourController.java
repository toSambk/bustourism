package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Tour;
import ru.bustourism.forms.AcceptingTourBean;
import ru.bustourism.service.TourService;

import javax.servlet.http.HttpSession;

@Controller
public class TourController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private TourService tourService;

    @GetMapping(path = "/tour")
    public String tour(HttpSession session, @RequestParam int tourId, ModelMap model) {

            int sessionId = (int) session.getAttribute("userId");
            Tour found = tourDAO.findById(tourId);
            model.addAttribute("tour", found);
            return "tour";

    }

    @PostMapping(path = "/tour")
    public String buyTour(HttpSession session, @RequestParam int tourId, ModelMap model) {
        int userId = (int) session.getAttribute("userId");

        if(userDAO.findById(userId).getTours().stream().anyMatch(x-> x.getId() == tourId)) {
            System.out.println("!!!!!!");
        } else {
            tourService.addTourToUser(userId, tourId);
        }
        model.addAttribute("tour", tourDAO.findById(tourId));
        return "tour";
    }

//    @ModelAttribute("acceptForm")
//    public AcceptingTourBean newAcceptingTourBean() {
//        AcceptingTourBean acceptingTourBean =  new AcceptingTourBean();
//        acceptingTourBean.setHiddenField("");
//        return acceptingTourBean;
//    }

}
