package com.example.week5challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.function.Predicate;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public static long idCounter = 0;

    ArrayList<Job> jobs = new ArrayList<>();

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {return "redirect:/login?logout=true";}

    @RequestMapping("/details")
    public String details(){
        return "details";
    }

    @GetMapping("/add")
    public String getAdd(Principal principal, Model model){
        Job job = new Job();
        IdSetter(job);
        job.setAuthor(principal.getName());
        model.addAttribute("job", job);
        return "add";
    }

    @PostMapping("/add")
    public String postAdd(@Valid Job job, BindingResult result, Model model){
            if(result.hasErrors()){
                return "add";
        } else {
                for (Job j: jobs){
                    if(j.getId() == job.getId()){
                        j.setTitle(job.getTitle());
                        j.setPostedDate(job.getPostedDate());
                        j.setPhone(job.getPhone());
                        j.setAuthor(job.getAuthor());
                        j.setDescription(job.getDescription());
                        model.addAttribute("jobs", jobs);
                        return "index";
                    }
                }
                jobs.add(job);
                model.addAttribute("jobs", jobs);
                return "index";
            }
    }


    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        for (Job job: jobs){
            if (id == job.getId()){
                model.addAttribute("job", job);
                break;
            }
        }
        return "add";
    }


    @RequestMapping("/details/{id}")
    public String jobDetails(@PathVariable("id") long id, Model model){
        for (Job job: jobs){
            if (id == job.getId()){
                model.addAttribute("job", job);
                break;
            }
        }
        return "details";
    }

    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id, Model model){
        jobs.removeIf(job -> job.getId() == id);
        model.addAttribute("jobs", jobs);
        return "index";
    }

    static void IdSetter(Job job){
        idCounter = idCounter + 1;
        job.setId(idCounter);
    }




}
