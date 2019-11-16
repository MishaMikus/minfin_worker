package server.logan_park.view.vehicle;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class VehicleController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/vehicle")
    public String initForm(HttpServletRequest request) {
        request.getSession(true).setAttribute("vehicleTable", VehicleHelper.allVehicleList());
        return "loganPark/vehicle";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logan_park/new_vehicle")
    public ModelAndView postNewVehicle(@Valid VehicleView newVehicleRequest) {
        LOGGER.info("TRY to add "+newVehicleRequest);
        new VehicleHelper().addVehicle(newVehicleRequest);
        return new ModelAndView(new RedirectView("/logan_park/vehicle", true));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logan_park/update_vehicle")
    public ModelAndView postUpdateVehicle(@Valid VehicleUpdate vehicleUpdate) {
        LOGGER.info("TRY to update "+vehicleUpdate);
        new VehicleHelper().updateVehicle(vehicleUpdate);
        return new ModelAndView(new RedirectView("/logan_park/vehicle", true));
    }
}