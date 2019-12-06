package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.exceptions.NotEnoughSeatsException;
import ru.bustourism.exceptions.TourNotFoundException;
import ru.bustourism.exceptions.UserNotFoundException;
import ru.bustourism.forms.AcceptingTourBean;
import ru.bustourism.service.TourService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TourController {

    @Autowired
    private ToursRepository toursRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(path = "/tour")
    public String tour(@RequestParam int tourId, ModelMap model) {
            Tour found = toursRepository.findById(tourId);
            model.addAttribute("tour", found);
            return "tour";

    }

    @PostMapping(path = "/tour")
    public String buyTour(Principal principal,  @Validated @ModelAttribute("acceptForm") AcceptingTourBean form, BindingResult result,
                          @RequestParam int tourId, ModelMap model) {
        User found = usersRepository.findByLogin(principal.getName());
        int userId = found.getId();
        try {
            tourService.buySeatsForTourByUser(userId, tourId, form.getQuantity());
        } catch(NotEnoughSeatsException e) {
            result.addError(new FieldError("acceptForm", "quantity", "Недостаточно свободных мест"));
        } catch (IllegalArgumentException e) {
            result.addError(new FieldError("acceptForm", "quantity", "Поле должно содержать число больше или равно 1"));
        } catch (UserNotFoundException e) {
            result.addError(new FieldError("acceptForm", "quantity", "Пользователь не найден"));
        } catch (TourNotFoundException e) {
            result.addError(new FieldError("acceptForm", "quantity", "Тур не найден"));
        }
        model.addAttribute("tour", toursRepository.findById(tourId));
        return "tour";
    }

    @PostMapping(path = "/assessTour")
    public String assessTour(Principal principal, @RequestParam int tourId
            , @RequestParam("rating") @NotNull @PositiveOrZero int assessment, ModelMap model) {

        User found = usersRepository.findByLogin(principal.getName());
        int userId = found.getId();

        List<String> errors = new ArrayList<>();

        try {
            tourService.assessTourByUser(userId, tourId, assessment);
        } catch (UserNotFoundException e) {
            errors.add("Пользователь не найден");
        } catch (TourNotFoundException e) {
            errors.add("Тур не найден");
        }

        model.addAttribute("errors", errors);
        model.addAttribute("tour", toursRepository.findById(tourId));
        return "tour";
    }

    @ModelAttribute("acceptForm")
    public AcceptingTourBean newAcceptingTourBean() {
        AcceptingTourBean acceptingTourBean =  new AcceptingTourBean();
        acceptingTourBean.setQuantity(1);
        return acceptingTourBean;
    }

}
