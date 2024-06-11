package tick.banque.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tick.banque.interfaces.AgentInterface;
import tick.banque.interfaces.CreditInterface;
import tick.banque.interfaces.OperationInterface;
import tick.banque.models.Agent;
import tick.banque.models.Credit;
import tick.banque.models.Operation;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private AgentInterface agentInterface;
    @Autowired
    private OperationInterface operationInterface;
    @Autowired
    private CreditInterface creditInterface;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String adminPage(Model model) {
        List<Operation> operations = operationInterface.findAll();
        model.addAttribute("operations", operations);
        List<Agent> agents = agentInterface.findAll();
        model.addAttribute("agents", agents);
        model.addAttribute("agent", new Agent());
        List<Credit> credits = creditInterface.findAll();
        model.addAttribute("credits", credits);
        return "admin";
    }

    @PostMapping("/deleteAgent/{agentId}")
    public String deleteAgent(@PathVariable("agentId") int agentId) {
        agentInterface.deleteById(agentId);
        return "redirect:/admin";
    }

    @PostMapping("/addAgent")
    public String addAgent(@ModelAttribute Agent agent) {
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        agentInterface.save(agent);
        return "redirect:/admin";
    }

    @PostMapping("/acceptCredit/{creditId}")
    public String acceptCredit(@PathVariable("creditId") int creditId) {
        Credit credit = creditInterface.findById(creditId).orElse(null);
        if (credit != null) {
            credit.setStatus("Accepted");
            creditInterface.save(credit);
        }
        return "redirect:/admin";
    }

    @PostMapping("/refuseCredit/{creditId}")
    public String refuseCredit(@PathVariable("creditId") int creditId) {
        Credit credit = creditInterface.findById(creditId).orElse(null);
        if (credit != null) {
            credit.setStatus("Refused");
            creditInterface.save(credit);
        }
        return "redirect:/admin";
    }
}
