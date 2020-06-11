package com.java.push;

import java.util.List;

import com.java.push.apps.Application;
import com.java.push.devices.Device;
import com.java.push.devices.DeviceService;
import com.java.push.apps.AppService;
import com.java.push.utils.HttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeController {
    
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private DeviceService deviceService;
   
    @Autowired
    private AppService appService;

    // @GetMapping({"/home",""})
    // public String home(Model model){
    //     model.addAttribute("PROJECTS", projectService.getAllProjects());
    //     return "components/home";
    // }
  
    @GetMapping("/projects/{projectId}")
    public String project(@PathVariable("projectId")Integer projectId ,Model model){
        List<Application> apps = appService.getAllAppsByProjectId(projectId);
        model.addAttribute("APPLICATIONS", apps);
        logger.info("[ResponseApp Amount : "+apps.size() + " ]"  );
        return "components/app";
    }

    @GetMapping("/form/projects")
    public String project(){
        return "components/project";
    }

    @GetMapping("/form/users")
    public String users(){
        return "components/user";
    }
    @GetMapping("/devices")
    public String deivces(Model model){
        List<Device> devices=  deviceService.getActiveDeviceByAppId("1");
        model.addAttribute("DEVICES", devices);
        return "components/device";
    }

    // @GetMapping("/form/apps")
    // public String app(Model model){
    //     model.addAttribute("PROJECTS", projectService.getAllProjects());
    //     return "components/app";
    // }

    @GetMapping("/index")
    public Object index(){

        HttpClient http = new HttpClient();
        return http.get("https://jsonplaceholder.typicode.com/todos/1").toMap();
        
      
    }
}