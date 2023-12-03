package tracker.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tracker.TrackerApplication;
import tracker.model.*;
import tracker.service.RateableService;
import tracker.service.RegisteredUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RateableDetailTLController {
    private Rateable rateable;
    private final RegisteredUserService registeredUserService;
    private static RateableService rateableService;

    public static void setRateableService(RateableService rS){
        rateableService = rS;
    }

    @GetMapping("/detail")
    public String detail(Map<String, Object> model){
        rateable = TrackerApplication.getInstance().getCurrentRateable();
        model.put("details", rateable.details());
        model.put("ratings", rateable.getRatings());
        model.put("addRating", new Rating());
        model.put("addTo", new Gym());
        model.put("loggedIn", TrackerApplication.getInstance().isLoggedIn());
        model.put("loggedInUserId", TrackerApplication.getInstance().getLoggedInUser().getId());
        model.put("deleteRating", 0);
        if(rateableService.getPossibleContainers() == null && TrackerApplication.getInstance().isLoggedIn())
        {
            List<RegisteredUser> users = new ArrayList<>();
            users.add(TrackerApplication.getInstance().getLoggedInUser());
            model.put("elements", users);
        }
        else if(TrackerApplication.getInstance().isLoggedIn())
            model.put("elements", rateableService.getPossibleContainers());
        return "rateableDetail";
    }

    @PostMapping("/addRating")
    public String addRating(Rating rating) {
        registeredUserService.rate(rateable,rating.getRating(), rating.getComment());
        return "redirect:/detail";
    }

    @PostMapping("/addTo")
    public String addTo(Gym gym) {
        rateableService.addRateable(gym.getId(), rateable.getId());
        return "redirect:/detail";
    }

    @PostMapping("/deleteRating")
    public String deleteRating(@RequestParam("ratingId") int id) {
        registeredUserService.deleteRating(rateable, id);
        return "redirect:/detail";
    }

    @Getter
    @Setter
    public static class Details{
        private String feature;
        private String data;

        public Details(String f, String d){
            feature = f;
            data = d;
        }
    }
}
