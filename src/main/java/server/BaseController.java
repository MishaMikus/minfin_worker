package server;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    protected final Logger LOGGER = Logger.getLogger(this.getClass());
}
